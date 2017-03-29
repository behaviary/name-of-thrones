(ns name-of-thrones.core
  (:require [reagent.core :as reagent :refer [atom]]
            [name-of-thrones.thronesify :as throne]))

(enable-console-print!)

;; define your app data so that it doesn't get over-written on reload

(defonce app-state (atom {:name "Peter DePaulo!"
                          :throne-name ""
                          :showing false}))

(defn update-throne! [args]
  (swap! app-state assoc :throne-name args))

(defn thronesify! [nm]
  (update-throne! (throne/convert-name nm)))

(defn show []
  (swap! app-state assoc :showing true))

(defn name-input []
  (let [name (atom "")]
    (fn []
      [:div
        [:input {:type "text"
                 :placeholder "Name"
                 :value @name
                 :on-change #(reset! name (-> % .-target .-value))}]
        [:button {:on-click #(when-let [n @name]
                               (show)
                               (thronesify! n)
                               (reset! name ""))} "Thronesify"]])))
(defn shared-state []
  (let [val app-state]
    (fn []
      [:div
        (when (:showing @val)
          [:div "Your name is now: " (:throne-name @val)])
       [:div [name-input]]])))

(reagent/render-component [shared-state]
                          (. js/document (getElementById "app")))

(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
)
