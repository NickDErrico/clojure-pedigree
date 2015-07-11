(ns app.tree.main
  (:require [app.models.person :as person]
            [app.util.neo4j.command :as ncm]))

(def ^{:private true} default-depth 5)

(defn- recur1 [tree rows]
  (let [row (first rows)]
    (if row
      (let [[path rel in-parent out-parent child] row
            short-path (vec (drop-last path))
            new-tree (assoc-in tree short-path {})]
        (recur new-tree (rest rows)))
      tree)))



(defn recur2 [path user-data]
  (let [sub-path (rest path)
        sub-id (first path)
        children (get user-data :children [])
        idx (.indexOf children sub-id)
        user-data2 (assoc user-data :children children)
        ]
    (if sub-id
      ;; (let [sub (recur2 sub-path user-data2)
      ;;       user-data2 (assoc-in user-data2 [:children (if (= idx -1) 0 idx)] sub)
      ;;       user-data3 (assoc user-data2 :user-id sub-id)]
      ;;   user-data3)
      (let [user-data3 (assoc user-data2 :user-id sub-id)
            sub (recur2 sub-path user-data3)
            user-data4 (assoc-in user-data3 [:children (if (= idx -1) 0 idx)] sub)
            ]
        user-data4)
      sub-id)
    ))


;; (defn f []
;;   (let [path [[1 2]
;;               [1 2 3]
;;               [1 2 3 5]
;;               [1 2 3 6]
;;               [1 2 4]
;;               [1 2 4 7]
;;               [1 2 8]
;;               [1 2 8 9]
;;               [1 2 8 9 10]
;;               [1 2 8 9 11]
;;               [1 2 8 12]
;;               [1 2 8 12 13]]
;;         tree
;;         (reduce (fn [link user-data] (recur2 link user-data)) {} path)
;;         ;; (recur2 path {})
;;         ]
;;     (clojure.pprint/pprint tree)
;;     ))

(defn f []
  (let [path [1 2 3 4 5 6]
        tree (recur2 path {})]
    (clojure.pprint/pprint tree)
    ))

;; (defn process [path user-data]
;;   (let [sub-path (rest path)
;;         sub-id (first sub-path)
;;         idx (index-of sub-id (:children user-data))
;;         sub (process sub-path (get-in [:children idx] {:user_id sub-id
;;                                                        :chidren []}))]
;;     (assoc-in user-data [:children (or idx 0)] sub)))

;; (reduce (fn [root row] (process root (first row))) {})

(defn- get-tree-from-node [root & [depth]]
  (let [rows (ncm/query-tree (:user_id root) depth)
        init-tree {(:user_id root) {}}]
    (recur1 init-tree rows)
    ))

(defn get-tree
  "Get tree from user id"
  ([]
   (let [root-node (person/find-root-node)]
     (get-tree-from-node root-node default-depth)))

  ([user-id]
   (let [root-node (person/find-node-by-user-id user-id)]
     (get-tree-from-node root-node default-depth)))

  ([user-id & {:keys [depth]
               :or [depth default-depth]}]
   (let [root-node (person/find-node-by-user-id user-id)]
     (get-tree-from-node root-node depth))))
