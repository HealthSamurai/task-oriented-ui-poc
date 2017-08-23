(ns ui.utils
  (:require-macros [reagent.ratom :refer [reaction]])
  (:require [re-frame.core :as rf]))

(rf/reg-sub-raw :db/by-path
                (fn [db [_ path]]
                  (.log js/console "by-path" path)
                  (reaction (get-in @db path))))

