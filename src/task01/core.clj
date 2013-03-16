" 1) Find all elements containing {:class \"r\"}.

Example:
[:h3 {:class \"r\"} [:a {:shape \"rect\", :class \"l\",
                         :href \"https://github.com/clojure/clojure\",
                         :onmousedown \"return rwt(this,'','','','4','AFQjCNFlSngH8Q4cB8TMqb710dD6ZkDSJg','','0CFYQFjAD','','',event)\"}
                     [:em {} \"clojure\"] \"/\" [:em {} \"clojure\"] \" · GitHub\"]]

   2) Extract href from the element :a.

The link from the example above is 'https://github.com/clojure/clojure'.

  3) Return vector of all 10 links.

Example: ['https://github.com/clojure/clojure', 'http://clojure.com/', . . .]"

(ns task01.core
  (:require [pl.danieljanus.tagsoup :refer :all])
  (:gen-class))

(defn node-name [node] (get node 0))
(defn node-attrs [node] (get node 1))
(defn node-children [node] (subvec node 2))
(defn node-nontext-children [node] (filter vector? (node-children node)))
(defn link-href [link] (get (node-attrs link) :href))

(defn tags [node]
  (concat [node] (reduce concat (map tags (node-nontext-children node)))))

(defn link? [n] (= (node-name n) :a))

(defn google-result? [link] (= (get (node-attrs link) :class) "l"))

(defn links [node]
  (map link-href (filter google-result? (filter link? (tags node)))))

(defn get-links []
  (let [data (parse "clojure_google.html")]
    (links data)))

(defn -main []
  (println (str "Found " (count (get-links)) " links!")))