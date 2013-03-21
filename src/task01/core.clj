(ns task01.core
  (:require [pl.danieljanus.tagsoup :refer :all])
  (:gen-class))

(defn link-href [link] ((attributes link) :href))
(defn google-result? [link] (= ((attributes link) :class) "l"))

(defn select-links [node]
  (if (= (tag node) :a)
    [node]
    (reduce concat (map select-links (filter vector? (children node))))))

(defn links [node]
  (map link-href (filter google-result? (select-links node))))

(defn get-links []
  (let [data (parse "clojure_google.html")]
    (links data)))

(defn -main []
  (println (str "Found " (count (get-links)) " links!")))

(links (parse "clojure_google.html"))

