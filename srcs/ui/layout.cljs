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


(defn layout [content]
  (fn []
    [:div.wrap
     (styles/style [:div.wrap
                    {:padding-left "140px"
                     :padding-top "30px"
                     :padding-right "60px"}
                    [:div.nav-cnt
                              {:position "absolute"
                               :top 0
                               :left 0
                               :background "#f6f6f6"
                               :padding "20px 20px"
                               :bottom 0
                               :border-right "1px solid #ddd"
                               }
                              [:.nav {:color "#3f51b5"
                                      :border "1px solid #3f51b5"
                                      :border-radius "50%"
                                      :padding "14px 0"
                                      :display "block"
                                      :opacity "0.8"
                                      :text-align "center"
                                      :font-size "24px"
                                      :margin "20px 5px"
                                      :width "60px"
                                      :height "60px"}
                               [:&.active {:color "#555" :opacity 1 :border-color "#555"}]
                               [:&.pink {:color "#E91E63" :border-color "#E91E63"}]
                               [:&.indigo {:color "#3F51B5" :border-color "#3F51B5"}]
                               [:&:hover {:opacity 1}]]]])
     [:div.nav-cnt
      [:a.nav.pink {:href (href :profile)} [wgt/icon :user]]
      [:a.nav.active {:href (href :tasks)} [wgt/icon :tasks]]
      [:a.nav.indigo {:href (href :navigation)} [wgt/icon :bars]]]
     [:div.content content]]))
