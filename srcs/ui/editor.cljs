(ns ui.editor
  (:require [reagent.core :as r]
            [re-frame.core :as rf]))

;; FIXME: remove on re-form usage

(rf/reg-event-db
  :cm/set-val
  (fn [db [_ path new-val]]
    (assoc-in db [:cm path] new-val)))

(rf/reg-sub
  :cm/get-val
  (fn [db [_ path]]
    (get-in db [:cm path])))

;; endFIXME

(defn- search-backwards [line pos regex]
  (loop [p pos]
    (let [cur-pos (dec p)
          char (.charAt line cur-pos)]
      (if (or (neg? cur-pos) (.test #"\s" char))
        nil
        (if (.test regex (.charAt line cur-pos))
          cur-pos
          (recur cur-pos))))))

(defn- options [dict regex word]
  (dict (clojure.string/replace word regex "")))

(defn complete-startswith [regex dict cm option]
  (let [cursor (.getCursor cm)
        end-char (.-ch cursor)
        line (.getLine cm (.-line cursor))
        start-char (search-backwards line end-char regex)
        to-pos #(.Pos js/CodeMirror line %)
        from {:line (.-line cursor) :ch start-char}
        to {:line (.-line cursor) :ch end-char}
        word (.getRange cm (clj->js from) (clj->js to))]
    (when start-char
      (when-let [lst (options dict regex word)]
        (clj->js
         {:list lst
          :from from
          :to to})))))

(defn superior-cm [{:keys [value on-change complete-fn]}]
  (let [cm-atom (r/atom nil)]
    (r/create-class
     {:component-did-mount
      (fn [this]
        (let [editor
              (.fromTextArea
               js/CodeMirror (r/dom-node this)
               (clj->js {:lineNumbers true
                         :hintOptions {:hint complete-fn}}))]
          (.on editor "change" (fn [cm _] (on-change (.getValue cm))))
          (.on editor "keyup"
               (fn [cm _] (when-not (.. cm -state -completionActive)
                            (.. js/CodeMirror -commands
                                (autocomplete cm nil (clj->js {:completeSingle false}))))))
          (reset! cm-atom editor)))

      :component-will-receive-props
      (fn [this next-props]
        (when-let [cm @cm-atom]
          (let [nvalue (-> next-props second :value)]
            (when (not= (.getValue cm) nvalue)
              (.setValue cm (or nvalue ""))))))

      :reagent-render
      (fn [{:keys [value on-change complete-fn]}]
        [:textarea.text])})))
