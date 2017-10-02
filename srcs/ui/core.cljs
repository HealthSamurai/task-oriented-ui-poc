(ns ui.core
  (:require-macros [reagent.ratom :refer [reaction]])
  (:require
   [clojure.string :as str]
   [cljsjs.react]
   [reagent.core :as reagent]
   [re-frame.core :as rf]
   [frames.routing]
   [frames.redirect :as redirect]
   [frames.window-location :as location]
   [ui.pages :as pages]
   [ui.dashboard.core]
   [ui.anatomy.core]
   [ui.utils]
   [ui.routes :as routes]
   [ui.layout :as layout]))

(defn current-page []
  (let [{page :match params :params} @(rf/subscribe [:route-map/current-route])]
    (if page
      (if-let [cmp (get @pages/pages page)]
        [:div [cmp params]]
        [:div.not-found (str "Page not found [" (str page) "]" )])
      [:div.not-found (str "Route not found ")])))

(rf/reg-event-fx
 ::initialize
 [] (fn [cofx]
      (assoc cofx :dispatch-n [[:route-map/init routes/routes]])))

(defn- mount-root []
  (reagent/render
   [layout/layout [current-page]]
   (.getElementById js/document "app")))

(defn init! []
  (rf/dispatch [::initialize])
  (mount-root))
