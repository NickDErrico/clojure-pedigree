(ns app.data.init
  (:require [app.models.user :as user-model]
            [app.models.person :as person-model]
            [app.util.pg :as db-util]
            [app.neo4j.main :as neo4j]
            [korma.db :as kd]
            [app.models.marriage-relation :as mrl]
            [app.models.pedigree-relation :as prl]
            [app.data.sample :as sample]
            [config.neo4j :refer [conn]]))

(defn create-init-user []
  (when (db-util/table-empty? user-model/user)
    (user-model/add-user {:username "admin"
                          :full_name "Admin"
                          :email "admin@example.com"
                          :password "admin"}
                         :admin)))

(defn create-init-person
  "Create new persons when the app starts if there is no person presented yet"
  []
  (when (db-util/table-empty? person-model/person)
    (neo4j/with-transaction
      (kd/transaction
       (let [root-husband (-> sample/root-husband
                              (person-model/add-person :is-root true) (:node))
             root-wife    (-> sample/root-wife
                              (person-model/add-person) (:node))

             f2-1-husband (-> sample/f21
                              (person-model/add-person) (:node))
             f2-1-wife-1  (-> sample/f22
                              (person-model/add-person) (:node))
             f2-1-wife-2  (-> sample/f23
                              (person-model/add-person) (:node))
             f2-2-husband (-> sample/f24
                              (person-model/add-person) (:node))
             f2-3-husband (-> sample/f25
                              (person-model/add-person) (:node))
             f2-3-wife    (-> sample/f26
                              (person-model/add-person) (:node))

             f3-1-wife    (-> sample/f31
                              (person-model/add-person) (:node))
             f3-1-husband (-> sample/f32
                              (person-model/add-person) (:node))
             f3-2-husband (-> sample/f33
                              (person-model/add-person) (:node))
             f3-3-husband (-> sample/f34
                              (person-model/add-person) (:node))
             f3-3-wife    (-> sample/f35
                              (person-model/add-person) (:node))

             f4-1-husband (-> sample/f41
                              (person-model/add-person) (:node))
             ]

         ;; marriages
         (mrl/add-marriage root-husband root-wife)
         (mrl/add-marriage f2-1-husband f2-1-wife-1 :husband-order 0)
         (mrl/add-marriage f2-1-husband f2-1-wife-2 :husband-order 1)
         (mrl/add-marriage f2-2-husband f2-3-wife)
         (mrl/add-marriage f3-1-husband f3-1-wife)
         (mrl/add-marriage f3-3-husband f3-3-wife)

         ;; pedigree
         (prl/add-child root-husband root-wife f2-2-husband 1)
         (prl/add-child root-husband root-wife f2-1-husband 0)
         (prl/add-child root-husband root-wife f2-3-husband 2)
         (prl/add-child f2-1-husband f2-1-wife-1 f3-1-wife 0)
         (prl/add-child f2-1-husband f2-1-wife-2 f3-2-husband 0)
         (prl/add-child f2-2-husband f2-3-wife f3-3-husband 0)
         (prl/add-child f3-1-husband f3-1-wife f4-1-husband 0)

         (println "Sample data inserted"))))))

(defn create-init-data []
  (create-init-user)
  (create-init-person))