(ns thread-test-clj.core
  (:require [thread-test-clj.consumers :refer [run-consumers]]
            [thread-test-clj.poll :refer [run-poll]])
  (:gen-class))




(defn -main
  "Entry point from 'lein run'"
  [& args]
  (if (zero? (count args))
    (println "Options: consumers or poll")
    (let [choice (first args)]
      (cond
        (= choice "consumers") (run-consumers)
        (= choice "poll") (run-poll)
        :default (println "Options: consumers or poll")
        )
      )
    )



  )
