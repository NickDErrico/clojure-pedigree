(ns app.controllers.minor-content
  (:require [compojure.core :refer :all]
            [app.util.main :as util]
            [app.logger.log-trace :as log-trace]
            [app.helper.minor-content :as minor-content]))

(defn- get-preface [request]
  (-> (minor-content/get-preface)
      (util/response-success)))

(defn- get-tree-desc [request]
  (-> (minor-content/get-tree-description)
      (util/response-success)))

(defroutes minor-content-api-routes
  (context "/api/minorContent" []
           (GET "/preface" [] get-preface)
           (GET "/treeDesc" [] get-tree-desc)))
