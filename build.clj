(ns build
  (:refer-clojure :exclude [test])
  (:require [clojure.tools.build.api :as b]))

(def lib 'compile-test/core)
(def version "0.1.0-SNAPSHOT")
(def main 'compile-test.core)
(def class-dir "target/classes")

(defn build [opts]
  (b/delete {:path "target"})

  (println "Copying source...")
  (b/copy-dir {:src-dirs ["src"]
               :target-dir class-dir})

  (println (str "Compiling " main "..."))
  (b/compile-clj {:lib lib
                  :main main
                  :uber-file (format "target/%s-%s.jar" lib version)
                  :basis (b/create-basis {})
                  :class-dir class-dir
                  :src-dirs ["src"]
                  :ns-compile [main]}))

