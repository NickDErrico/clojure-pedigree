(ns app.controllers.home
  (:require [compojure.core :refer :all]
            [app.views.layout :as layout]
            [selmer.parser :refer [render-file render]]
            [app.util.main :as util]
            [app.util.security :as security]
            [ring.util.response :refer [response redirect content-type]]
            [buddy.auth :refer [authenticated?]]
            [config.main :refer [config]]))

(defn home [request]
  (layout/render "home/index.html" {:name (config :site-name)}))

(defn login-render [request]
  (layout/render "home/login.html"))

(defn login-authenticate [request]
  (let [username (util/param request "username")
        password (util/param request "password")
        user-info (security/authen-user username password)]
    (if user-info
      (let [session (:session request)
            updated-session (assoc session
                                   :identity (user-info :id)
                                   :user-info user-info)]
        (-> (redirect "/welcome") (assoc :session updated-session)))
      (layout/render "home/login.html" {:message "error"}))))

(defn welcome [request]
  (println (:session request))
  (layout/render "home/welcome.html"))

(defroutes home-routes
  (GET "/" [] home)
  (GET "/login" [] login-render)
  (GET "/welcome" [] welcome)
  (POST "/login" [] login-authenticate))

(def home-rules [{:uri "/login"
                  :handler security/anonymous-access
                  :redirect "/welcome"}])









