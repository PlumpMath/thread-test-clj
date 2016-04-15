(defproject thread-test-clj "1.0"
  :description "Toy program for learning Clojure futures and core.async"
  :url "https://github.com/RGrun/thread-test-clj"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/core.async "0.2.374"]]
  :main ^:skip-aot thread-test-clj.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})