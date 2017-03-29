(ns name-of-thrones.thronesify
  (:require [clojure.string :as str]))

; Name of Thrones generator

; Goal: generate a Game of thrones name for any input that someone

; Input: "Maclen Zilber"
; Output "Macerys Zilgaryn" "Macobb Zilbatheon"
;        "Peterys DePagaryn" "Petobb DePabatheon"

; First names:
; Cer sei

;; (defn uniques? [s]
;;   (let [acc (into #{} s)]
;;     (= (count acc) (count s))))

; Last Names:

; Lann ister
; Bar atheon
; Snow
; Dro go
; Tar garyn

;; Peter DePaulo
;; [Peter DePaulo]
;; ;; 10% chance: Peter Snow
;; [Pet er DeP aulo]
;; [Pet aulo] or [er DeP]
;; [Petnister DePatheon] or [Cerer Lanaulo] etc...

;; Constants
(def vowels (set (str/split "aeiouy" #"")))

;; TODO: Extend and simplify how the list of names works
(def got-names ["Petyr Baelish"
                "Tyrion	Lannister"
                "Jaime	Lannister"
                "Cersei	Lannister"
                "Robb	Stark"
                "Sansa	Stark"
                "Arya	Stark"
                "Bran	Stark"
                "Rickon	Stark"
                "Rhaegar	Targaryen"
                "Viserys	Targaryen"
                "Daenerys	Targaryen"])

;; Chance that Snow will be the chosen name
(def chance-of-snow 0.1)

(defn check-for-snow []
  (<= (rand) chance-of-snow))

;; Test for check-for-snow
;; (frequencies (repeatedly 100 check-for-snow))
;; Using the ->> macro for readability
;; (->> check-for-snow
;;     (repeatedly 100)
;;     frequencies)

(defn constonant? [ch]
  "Checks if char or single string is constonant"
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

;; (split-by-constonant "aaaTa")

(defn convert-name-vec [nm-vec]
  (vec (map #(split-by-constonant %) nm-vec)))

(defn split-name [nm]
  "Splits name by spaces and strips extra spaces"
  (str/split nm #"\s+"))

(defn break-name [nm]
  (convert-name-vec (split-name nm)))

;; (split-name "Peter DePaulo")
;; (break-name "Peter DePaulo   ")

(defn chop-names [names]
  (for [nm names]
    (break-name nm)))

;; Test chop-names
;; (chop-names got-names)

(defn grab-part [thing thing2]
  (str (first thing) (second thing2)))

;; (defn grab-second-part [[_ thing] [_ thing2]]
;;   (str (first thing) (second thing2)))

;; (grab-second-part [["Pet" "er"] ["DeP" "aulo"]] [["Fart" "y"] ["Barb" "arian"]])

(defn thronesify [got-name nm]
  "Combines the permutation of the names formatted [['n1' 'n2'] ['L1' 'L2']]"
  (let [fname (grab-part (first nm) (first got-name))
        lname (grab-part (second nm) (second got-name))]
    (str fname " " lname)))

;; TEST thronesify
;; (thronesify (break-name "Peter DePaulo") (break-name "Farty Barbarian"))

(defn grab-da-throne [got-names]
  (-> got-names
      rand-nth
      break-name))

(defn convert-name [og-name]
  (let  [got-name (grab-da-throne got-names)]
    (if (check-for-snow)
      (let [fname (first (str/split og-name #" "))
            snow "Snow"]
        (str fname " " snow))
      (->> og-name
           break-name
           (thronesify got-name)))))


