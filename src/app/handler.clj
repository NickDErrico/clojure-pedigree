(ns app.handler
  (:require [compojure.core :refer [defroutes routes]]
            [ring.middleware.resource :refer [wrap-resource]]
            [ring.middleware.file-info :refer [wrap-file-info]]
            [ring.middleware.session.memory :refer [memory-store]]
            [hiccup.middleware :refer [wrap-base-url]]
            [compojure.handler :as handler]
            [ring.middleware.params :refer [wrap-params]]
            [compojure.route :as route]

            [app.controllers.home :refer [home-routes home-rules]]
            [app.controllers.person :refer [person-routes]]
            [app.controllers.user :refer [user-routes user-rules]]
            [app.controllers.admin :refer [admin-routes admin-rules]]

            [ring.middleware.session :refer [wrap-session]]
            [noir.session :as session]
            [buddy.auth.backends.session :refer [session-backend]]
            [buddy.auth.middleware :refer [wrap-authentication]]
            [buddy.auth.accessrules :refer [wrap-access-rules]]
            [noir.validation :as validation]
            [app.util.security :as security]))

(defn init []
  (println "app is starting"))

(defn destroy []
  (println "app is shutting down"))

(defroutes app-routes
  (route/resources "/")
  (route/not-found "Not Found"))

(def authentication-backend (session-backend))

(def authorization-rules (concat
                          user-rules
                          admin-rules
                          home-rules))

(def app
  (-> (routes home-routes
              person-routes
              user-routes
              admin-routes
              app-routes)
      (wrap-access-rules {:rules authorization-rules :on-error security/unauthorized-handler})
      (wrap-authentication authentication-backend)
      (wrap-params)
      (wrap-session)))
