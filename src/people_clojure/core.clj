(ns people-clojure.core
  (:require [clojure.string :as str]
            [clojure.walk :as walk]
            [compojure.core :as c]
            [ring.adapter.jetty :as j])
  (:gen-class))

(defn read-people []
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
    people))

(c/defroutes app
  (c/GET "/" request
    "Hello World!"))

(defn -main []
  (j/run-jetty app {:port 3000}))
