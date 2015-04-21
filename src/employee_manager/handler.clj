(ns employee-manager.handler
  (:require [compojure.api.sweet :refer :all]
            [employee-manager.routes.employees :refer :all]
            [ring.util.http-response :refer :all]
            [schema.core :as s]))

(s/defschema Message {:message String})

(defapi app
  #_(swagger-ui)
  #_(swagger-docs
     :title "Employee-manager")

  (swaggered "employees"
             :description "Employee managed API"
             employee-routes)

  (swaggered "api"
             :description "hello world"
             (GET* "/hello" []
                   :return Message
                   :query-params [name :- String]
                   :summary "say hello"
                   (ok {:message (str "Hello, " name)}))))
