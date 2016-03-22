(ns people-clojure.core
  (:require [clojure.string :as str]
            [clojure.walk :as walk])
  (:gen-class))

(defn -main []
  (let [people (slurp "people.csv")
        people (str/split-lines people)
        people (map (fn [line]
                      (str/split line #","))
                    people)
        header (first people)
        people (rest people)
        people (map (fn [line]
                      (apply hash-map (interleave header line)))
                    people)
        people (walk/keywordize-keys people)
        people (filter (fn [line]
                         (= (:country line) "Brazil"))
                       people)]
    (spit "filtered_people.edn" (pr-str people))
    people))
