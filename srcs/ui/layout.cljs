(ns ui.layout
  (:require-macros [reagent.ratom :refer [reaction]])
  (:require
   [reagent.core :as reagent]
   [ui.styles :as styles]
   [re-frame.core :as rf]
   [ui.routes :refer [href]]
   [ui.widgets :as wgt]
   [clojure.string :as str]))


(defn current-page? [route key]
  (= (:match route) key))

(def common-style
  [:body
   [:h1 {:border-bottom "1px solid rgba(0,0,0,.10)!important"
         :font-size "32px"
         :padding-bottom "14px"
         :font-weight "300"
         :letter-spacing "0.7px"
         :margin-bottom "10px"
         :line-height "40px"}]
   [:h3 {:border-bottom "1px solid rgba(0,0,0,.15)!important"
         :font-size "20px"
         :letter-spacing "0.7px"
         :font-weight 300
         :line-height "40px"}]
   [:a {:color "#444"}]
   [:.btn-primary {:color "#0275d8"
                   :background-color "white"
                   :border-color "#0275d8"}
    [:&:hover {:background-color "#0275d8"
               :color "white"}]]
   [:.block
    {:margin-top "20px" :margin-bottom "20px"
     :border-left "2px solid #8bc34a"
     :padding-left "30px"}
    [:&.form {:border-left-color "#ddd" :background-color "#f6f6f6"}]
    [:.search-icon {:position "relative" :right "-20px" :z-index 1000 :color "gray"}]
    [:.search {:border-top "none"
               :border-right "none"
               :padding-left "24px"
               :position "relative"
               :bottom "-1px"
               :width "20em"
               :border-bottom "1px solid #888"
               :border-left "none"}
     [:&:focus
      {:border-top "none"
       :border-right "none"
       :border-bottom "1px solid blue"
       :border-left "none"}]
     ]

    [:b {:color "gray"
         :font-weight 300
         :letter-spacing "0.4px"}]]

   [:.assignee {:margin-right "10px"
                 :font-size "14px"
                 :color "#444"
                 :border "1px solid #f1f1f1"
                 :display "inline-block;"
                 :margin-left "10px"
                 :height "26px"
                 :border-radius "12px"
                 :padding  "1px 10px 1px 1px"}
    [:.fa {:margin "0 5px" :border-radius "50%" :border "1px solid #ddd"}]
    [:img.avatar {:width "19px"
                  :height "19px"
                  :margin "0 5px 0 2px"
                  :border-radius "50%"}]
    ]
   [:.action {:border "1px solid rgba(27, 149, 224, 0.11)"
              :color "#1b95e0!important"
              :padding "2px 10px"
              :font-size "14px"
              :font-weight 300
              :border-radius "10px" :margin-right "10px"}]
   ])

(defn layout [content]
  (fn []
    [:div.wrap
     (styles/style common-style)
     (styles/style [:div.wrap
                    {:padding-left "130px"
                     :padding-top "30px"
                     :padding-right "60px"}
                    [:div.nav-cnt
                     {:position "fixed"
                      :top 0
                      :left 0
                      :background "#f6f6f6"
                      :padding "20px"
                      :bottom 0}
                     [:.nav {:color "#3f51b5"
                             :border "1px solid #3f51b5"
                             :border-radius "50%"
                             :padding "4px 0"
                             :display "block"
                             :opacity "0.8"
                             :text-align "center"
                             :font-size "20px"
                             :margin "10px 5px"
                             :width "40px"
                             :height "40px"}
                      [:&.active {:color "#444" :opacity 1 :border-color "#aaa"}]
                      [:&.blue {:color "#03A9F4" :opacity 1 :border-color "#03A9F4"}]
                      [:&.pink {:color "#E91E63" :border-color "#E91E63"}]
                      [:&.green {:color "#4CAF50" :border-color "#4CAF50"}]
                      [:&.indigo {:color "#3F51B5" :border-color "#3F51B5"}]
                      [:&:hover {:opacity 1}]]]])
     [:div.nav-cnt
      [:a.nav.blue {:href (href :navigation)} [wgt/icon :bars]]
      [:a.nav.green {:href (href :tasks)} [wgt/icon :check-square-o]]
      [:a.nav.active {:href (href :profile)} [wgt/icon :user-o]]]
     [:div.content content]]))
