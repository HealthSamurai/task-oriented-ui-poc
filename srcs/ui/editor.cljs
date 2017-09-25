(ns ui.editor
  (:require [reagent.core :as r]))

(defn- search-backwards [line pos regex]
  (loop [p pos]
    (let [cur-pos (dec p)
          char (.charAt line cur-pos)]
      (if (or (neg? cur-pos) (.test #"\s" char))
        nil
        (if (.test regex (.charAt line cur-pos))
          cur-pos
          (recur cur-pos))))))

(defn- complete [cm option]
  (let [cursor (.getCursor cm)
        end-char (.-ch cursor)
        line (.getLine cm (.-line cursor))
        start-char (search-backwards line end-char #"#")
        to-pos #(.Pos js/CodeMirror line %)]
    (if (nil? start-char)
      nil
      (clj->js
       {:list ["some" "useful" "completions"]
        :from {:line (.-line cursor) :ch start-char}
        :to {:line (.-line cursor) :ch end-char}}))))

(defn superior-cm [] ;; TODO: add props as re-form inputs have to
  (r/create-class
   {:component-did-mount
    (fn [this]
      (.fromTextArea js/CodeMirror (r/dom-node this)
                     (clj->js {:lineNumbers true
                               :extraKeys {"Ctrl-Space" "autocomplete"}
                               :hintOptions {:hint complete}})))

    :reagent-render
    (fn []
      [:textarea.text])}))
