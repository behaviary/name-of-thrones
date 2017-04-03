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

(defn handle-error []
  (js/alert "You must enter a name"))

(defn handle-form [name]
  (when-let [n @name]
   (if (empty? n)
     (handle-error)
     (do
       (show)
       (thronesify! n)
       (reset! name "")))))

(defn name-input []
  (let [name (atom "")]
    (fn []
      [:div {:class "input-wrapper"}
        [:input {:type "text"
                 :placeholder "First and Last Name"
                 :value @name
                 :on-key-press (fn [e]
                                (if (= 13  (.-charCode e))
                                  (handle-form name)))
                 :on-change #(reset! name (-> % .-target .-value))}]
        [:button {:type "submit"
                  :on-click #(handle-form name)} "Thronesify"]])))

(defn shared-state []
  (let [val app-state]
    (fn []
      [:div {:class "mountains"}
        [:div {:class "valyrian-wrapper"}
         [:h1 {:class "title"} "NAME OF THRONES"]
          (when (:showing @val)
            [:div {:class "throne-name"} "Your name is now:  " 
             [:p (:throne-name @val)]])
         [:div {:class "name-input"} [name-input]]]])))

(reagent/render-component [shared-state]
                          (. js/document (getElementById "app")))

(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
)
