
;; copy/paste these tests seperately into the REPL.
;; run them with a command like this:

;; (dotimes [x 5]
;;   (>!! c x))

;; SPLIT TEST

(use 'clojure.core.async)

(def c (chan 100))

(let [[o e] (split odd? c)]
    (go-loop []
        (println "odd-c: " (<!! o))
        (recur))
    (go-loop []
        (println "even-c: " (<!! e))
        (recur)))
        

;; MULT / TAP TEST
        
(use 'clojure.core.async)
        
(def c (chan 100))

(let [m (mult c)
      out (chan 100) 
      log (chan 100)]
      (tap m out)
      (tap m log)
      
      ;; test console printing
      (go-loop []
        (println "from out: " (<!! out))
        (recur))
      
      (spit "log-output.txt" "")
      ;; test logging
      (go-loop []
        (spit "log-output.txt" (str (<!! log) "\n") :append true)
        (recur)))
        
