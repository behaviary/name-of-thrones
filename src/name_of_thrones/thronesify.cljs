(ns name-of-thrones.thronesify
  (:require [clojure.string :as str]))

;; Name of Thrones generator

;; Goal: generate a Game of thrones name for any input that someone

;; Input: "Peter DePaulo"
;; Output: "Peterys DePagaryn" "Petobb DePabatheon"        

;; Constants
(def vowels (set (str/split "aeiouy" #"")))

;; TODO: Extend and simplify how the list of names works
(def got-names ["Petyr Baelish"
                "Tyrion	Lannister"
                "Jaime	Lannister"
                "Cersei	Lannister"
                "Robob	Stark"
                "Sansa	Stark"
                "Arya	Stark"
                "Bran	Stark"
                "Ricikon	Stark"
                "Rhaegar	Targaryen"
                "Viserys	Targaryen"
                "Daenerys	Targaryen"])

;; Chance that bastard name will be chosen
(def chance-of-snow 0.1)

(defn check-for-snow []
  (<= (rand) chance-of-snow))

(defn constonant? 
  "Checks if char or single string is constonant"
  [ch]
  (let [str-ch (str ch)]
    (if (> (count str-ch) 1)
      false
      (not (contains? vowels str-ch)))))

(defn find-idx-constonant [nm]
  (loop [str-vec (map-indexed vector (subs nm 1))]
    (if (constonant? (->> str-vec first second))
      (+ 2 (->> str-vec first first)) ;; refactor: Magic number breaks the string *after* the constonant
      (recur (rest str-vec)))))

(defn split-by-constonant [nm]
  (let [idx (find-idx-constonant nm)]
    (vec (map str/join (split-at idx nm)))))

(defn convert-name-vec [nm-vec]
  (vec (map #(split-by-constonant %) nm-vec)))

(defn split-name 
  "Splits name by spaces and strips extra spaces"
  [nm]
  (str/split nm #"\s+"))

(defn break-name [nm]
  (convert-name-vec (split-name nm)))

(defn chop-names [names]
  (for [nm names]
    (break-name nm)))

(defn grab-part [thing thing2]
  (str (first thing) (second thing2)))

(defn thronesify
  "Combines the permutation of the names formatted [['n1' 'n2'] ['L1' 'L2']]"
  [got-name nm]
  (let [fname (grab-part (first nm) (first got-name))
        lname (grab-part (second nm) (second got-name))]
    (str fname " " lname)))

(defn grab-da-throne [got-names]
  (-> got-names
      rand-nth
      break-name))

(defn convert-name [og-name]
  (let  [got-name (grab-da-throne got-names)]
    (if (check-for-snow)
      (let [fname (first (str/split og-name #" "))
            snow ["Snow" "Sand"]]
        (str fname " " (rand-nth snow)))
      (->> og-name
           break-name
           (thronesify got-name)))))