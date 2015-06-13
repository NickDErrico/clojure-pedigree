(ns app.models.marriageRelation
  (:require [korma.core :refer :all]
            [app.util.dbUtil :as db-util]
            [clojurewerkz.neocons.rest.nodes :as nn]
            [clojurewerkz.neocons.rest.relationships :as nrl]
            [config.neo4j :refer [conn]]
            [validateur.validation :as vl]))

(def RELATION_TYPES
  {:husband-wife :husband_wife
   :wife-husband :wife_husband})

(defn add-relation-from-node
  "Add new relation between two node in the system"
  [first-node second-node & {:keys [type]
                             :or [type (:husband-wife RELATION_TYPES)]}]
  (nrl/create conn first-node second-node type))

(defn add-marriage
  "Add marriage relation between husband and wife nodes"
  [husband-node wife-node]
  (add-relation-from-node husband-node wife-node :type (:husband-wife RELATION_TYPES))
  (add-relation-from-node wife-node husband-node :type (:wife-husband RELATION_TYPES)))
