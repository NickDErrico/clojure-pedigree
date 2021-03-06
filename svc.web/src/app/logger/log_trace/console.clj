(ns app.logger.log-trace.console
  (:require [app.logger.logger :as logger]
            [io.aviso.exception :as aviso-ex]))

(defn- process-datum [datum]
  (cond
    (nil? datum)                  ""
    (instance? Exception datum)   (aviso-ex/format-exception datum)
    (some #(% datum) [seq? map?]) (with-out-str (clojure.pprint/pprint datum))
    :else                        (.toString datum)
    ))

(defn- process-data "Pretty format the data" [data]
  (let [data (map #(process-datum %) data)]
    (clojure.string/join " " data)))

(defn write "Write the log-data to console" [log-data]
  (let [{correlation-id :correlationId
         level          :level
         request        :request
         response       :response
         process-time   :processTime
         messages       :message}       log-data
        {:keys [params route-params form-params query-params query-string server-name uri]} request
        header-request (:header request)
        {:keys [message body]} response
        header-response (:header response)]

    ;; a separator
    (logger/write-console
     level
     "--------------------------------------------------------------------------------"
     "")

    ;; request id
    (logger/write-console level "CORRELATION ID:" correlation-id)

    ;; write request information
    (logger/write-console
     level "REQUEST:"
     (str "\n"
          "Header: " header-request "\n"
          "Params: " params "\n"
          "Route params: " route-params "\n"
          "Form params: " form-params "\n"
          "Query params: " query-params "\n"
          "Server name: " server-name "\n"
          "Uri: " uri "\n"))

    ;; write steps
    (let [
          ;; map the messages to list of step strings
          steps (for [[idx message] (map-indexed vector messages)]
                  (str "[" (+ 1 idx) "]" " "
                       (get message :title) " - "
                       (process-data (get message :data))))

          ;; join them by new line
          steps (clojure.string/join "\n" steps)
          ]
      (logger/write-console
       level
       "STEPS:"
       (str "\n" steps "\n")))

    ;; write response
    (logger/write-console
     level "RESPONSE:"
     (str "\n"
          "Header: " header-response "\n"
          "Message: " message "\n"
          "Body: " body "\n"))

    ;; other information
    (logger/write-console level "PROCESS TIME:" process-time)))
