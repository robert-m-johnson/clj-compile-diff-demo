(ns compile-test.core
  (:require [java-time.api :as jt]
            [clojure.core.async :as a
             :refer [<! <!! >! close! go-loop chan mult tap]]
            [integrant.core :as ig]

            [clojure.tools.logging :as log]
            [medley.core :as medley]
            [clojure.data.priority-map :as priority-map]
            [chime.core :as ch]
            [chime.core-async :as cha])
  (:gen-class))

(defn -main
  [& _args]
  (println "Hello, world!"))

