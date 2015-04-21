(ns employee-manager.routes.employees
  (:require [schema.core :as s]
            [compojure.api.sweet :refer :all]
            [ring.util.http-response :refer :all]
            [ring.swagger.schema :as rs :refer [describe]]))

(s/defschema Employee {:id Long
                       :name String
                       :age Long
                       :role (s/enum :manager :developer)})

(defonce id-seq (atom 0))
(defonce employees (atom (array-map)))

(defn get-employees [] (-> employees deref vals reverse))

(defn add! [employee-data]
  (let [id (swap! id-seq inc)
        employee (rs/coerce Employee employee-data)]
    (swap! employees assoc id employee)
    employee))

(when (empty? @employees)
  (add! {:id 1 :name "Nishimura" :age 23 :role :developer})
  (add! {:id 2 :name "Ochiai" :age 29 :role :manager}))

(defroutes* employee-routes
  (context "/employees" []
           (GET* "/" []
                 :return [Employee]
                 :summary "get all employees"
                 :nickname "getAllEmployees"
                 (ok (get-employees)))))
