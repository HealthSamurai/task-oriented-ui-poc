(ns ui.routes
  (:require [clojure.string :as str]
            [route-map.core :as route-map]))

(def routes {:. :dashboard/index
             "tasks" {:. :dashboard/tasks
                      [:type] {:. :dashboard/task}}
             "notes" {:. :dashboard/notes}
             "profile" {:. :dashboard/profile}
             "patients" {:. :dashboard/patients}
             "navigation" {:. :dashboard/navigation}
             "anatomy" {:. :anatomy/index}
             })

(defn href
  [& parts]
  (let [url (str "/" (str/join "/" (map (fn [x] (if (keyword? x) (name x) (str x))) parts)))]
    (when-not  (route-map/match [:. url] routes)
      (.error js/console (str url " is not matches routes")))
    (str "#" url)))
