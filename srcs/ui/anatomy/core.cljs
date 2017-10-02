(ns ui.anatomy.core
  (:require-macros [reagent.ratom :refer [reaction]])
  (:require
   [reagent.core :as reagent]
   [ui.pages :as pages]
   [ui.routes :refer [href]]
   [re-frame.core :as rf]
   [ui.widgets :as wgt]
   [ui.styles :as styles]
   [ui.editor :as editor]
   [clojure.string :as str]))

;; event handlers

(rf/reg-event-db
 ::initialize
 (fn [db _]
   (-> db
       (assoc ::projections {1 "http://www.anatomymapper.com/images/full-body.gif"
                             2 "http://s.sears.com/is/image/Sears/PD_0022_364_WB44K10005"
                             3 "http://s.sears.com/is/image/Sears/PD_0022_364_WB44T10010"})
       (assoc ::points {})
       (assoc ::photos [])
       (assoc ::active-projection-id 0))))

;; TODO Find proper place
(rf/dispatch-sync [::initialize])

(rf/reg-event-db
 ::activate-projection
 (fn [db [_ id]]
   (assoc db ::active-projection-id id)))

(rf/reg-event-db
 ::add-point
 (fn [db [_ projection-id coords]]
   (assoc-in db [::points (count (::points db))] [projection-id coords])))

(rf/reg-event-db
 ::remove-point
 (fn [db [_ id]]
   (update-in db [::points] dissoc id)))

;; queries

(rf/reg-sub
 ::projections
 (fn [db _]
   (::projections db)))

(rf/reg-sub
 ::active-projection-id
 (fn [db _]
   (::active-projection-id db)))

(rf/reg-sub
 ::points
 (fn [db _]
   (::points db)))

;; views

(defn projection-picker
  "choose from several projections"
  []
  (into
   [:div.projections.row]
   (for [[id url] @(rf/subscribe [::projections])]
     ^{:key id}
     [:img.col-sm-4 {:src url
                     :on-click #(rf/dispatch [::activate-projection id])}])))

(defn point-picker
  "open projection in bigger view to choose points"
  []
  (let [id  @(rf/subscribe [::active-projection-id])]
    (when (pos-int? id)
      [:div.picker
       (styles/style
        [:div.picker
         {:clear "both"
          :width "50%"
          :margin "0 auto"}])
       (into [:svg
              {:x 0 :y 0 :width 400 :height 400
               ;; TODO move logic and deal with event.persist
               :on-click
               (fn [e] (rf/dispatch
                        [::add-point id
                         (let [rect (.getBoundingClientRect (.-target e))]
                           [(- (.-clientX e) (.-left rect))
                            (- (.-clientY e) (.-top rect))])]))}
              [:image {:xlink-href (get @(rf/subscribe [::projections]) id)
                       :height "400" :width "400"}]]
             (for [[_ [projection-id [x y]]] @(rf/subscribe [::points])
                   :when (= projection-id id)]
               [:circle {:cx x :cy y :r 5}]))])))

(defn points-history
  "list of picked points with ability to remove"
  []
  (into
   [:div.points]
   (for [[key [projection-id coords]] @(rf/subscribe [::points])]
     ^{:key key}
     [:div.point
      {:on-click #(rf/dispatch [::remove-point key])}
      [:span (str "Projection " projection-id " : " coords)]])))

(defn index [params]
  [:div.container-fluid
   [:h1 "Anatomy picker"]
   [:div.row
    [:div.col
     [projection-picker]
     [point-picker]]
    [:div.col-sm-4
     [:span "Points: (click to delete)"]
     [points-history]]]])

(pages/reg-page :anatomy/index index)
