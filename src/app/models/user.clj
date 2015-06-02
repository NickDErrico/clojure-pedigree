(ns app.models.user
  (:use [korma.core])
  (:require [app.models.userRole :refer [create-user-role]]
            [crypto.password.bcrypt :as crypto]
            [app.util.dbUtil :as db-util]
            [validateur.validation :as vl]))

(defentity user
  (table :tbl_user)

  (pk :id)
  )

(def validation
  (vl/validation-set
   (vl/presence-of :username)
   (vl/presence-of :full_name)
   (vl/presence-of :email)
   (vl/presence-of :password)))

(defn create-init-users []
  (when (db-util/table-empty? user)
    (let [admin (insert user
                        (values {:full_name "Admin"
                                 :email "admin@example.com"
                                 :password (crypto/encrypt "admin")}))]
      (create-user-role admin :admin))))

(defn add-user [user-map]
  (when (vl/valid? validation user-map)
    (let [password-hash (crypto/encrypt (:password user-map))
          new-user-map (assoc user-map :password password-hash)]
      (insert user (values new-user-map))
      )))


















