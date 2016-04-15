(ns thread-test-clj.consumers
  (:require [clojure.core.async :refer [chan <!! >!! go-loop]])
  (:import [java.time LocalTime]))

(def ADDER-FILENAME "adder-report.txt")
(def MULTIPLIER-FILENAME "multiplier-report.txt")

(def CONSTANT 5)

(defn init
  "Truncate output files."
  []
  (spit ADDER-FILENAME "")
  (spit MULTIPLIER-FILENAME ""))

(defn run-adder
  "Run the adder thread."
  [c]
  (go-loop [in (<!! c)]                                     ;; blocks here
      (spit ADDER-FILENAME
            (str "Adder got message at: " (LocalTime/now) ". Result: " (+ in CONSTANT) "\n")
            :append true)
      (recur (<!! c))))   ;; recur with next value from input channel

(defn run-multiplier
  "Run the multiplier thread."
  [c]
  (go-loop [in (<!! c)]                                     ;; blocks here
           (spit MULTIPLIER-FILENAME
                 (str "Multiplier got message at: " (LocalTime/now) ". Result: " (* in CONSTANT) "\n")
                 :append true)
           (recur (<!! c))))   ;; recur with next value from input channel

(defn report
  "Print the results of the threads."
  []
  (let [adder-report (slurp ADDER-FILENAME)
        multiplier-report (slurp MULTIPLIER-FILENAME)]
    (println (str "\n" adder-report "\n" multiplier-report))))

(defn run-consumers
  "Run the consumer tests."
  []
  (init)
  (let [a-channel (chan 5)
        m-channel (chan 5)]
    (run-adder a-channel)
    (run-multiplier m-channel)
    (loop []
      (.print System/out "Enter a new value (zero quits): ")
      (let [val (read-line)                                 ;; blocks here for user input
            val-num (read-string val)]
        (if (zero? val-num)
          (report)
          (do
            (>!! a-channel val-num)
            (>!! m-channel val-num)
            (recur)))))))