;;; GENERATED BY ANSIBLE. DO NOT EDIT
;;; EDIT TEMPLATE IN ansible/templates/db_config.clj
(ns config.db
  (:use [korma.db]
        [korma.core]))

(def db (postgres {:db "pedigree"
                   :user "vagrant"
                   :password "ebe5386eebe396080ab7243cdd965cea"
                   :host "localhost"
                   :port "5432"
                   :delimiters ""}))

(defdb app-db db)
