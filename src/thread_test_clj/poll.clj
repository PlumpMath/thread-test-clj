(ns thread-test-clj.poll)

(defn sleeping-thread
  "This thread sleeps inside a future."
  [time]
  (Thread/sleep time)
  time)

(defn run-poll
  "Runs the poll test."
  []
  (.print System/out "Enter a time for the thead to sleep (in ms): ")
  (let [in-time (read-string (read-line))                   ;; blocks here for user input
        thread (future (sleeping-thread in-time))]
    (loop [counter 0]
      (if (realized? thread)
        (do
          (println (str "Thread finished in " counter " iterations of the loop!"))
          (System/exit 0))
        (recur (inc counter))))))