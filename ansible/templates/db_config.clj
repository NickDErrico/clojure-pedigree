;;; GENERATED BY ANSIBLE. DO NOT EDIT
;;; EDIT TEMPLATE IN ansible/templates/db_config.clj
(ns config.db
  (:use [korma.db]))

(def db (postgres {:db "{{db_name}}"
                   :user "{{db_user}}"
                   :password "{{db_password}}"
                   :host "{{db_host}}"
                   :port "5432"
                   :delimiters ""}))
