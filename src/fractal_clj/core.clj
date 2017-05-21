(ns fractal-clj.core
  (:require [clojure.math.numeric-tower :as math]
            [clojure.java.shell :refer [sh]])
  (:use clojure.java.io)
  (:import [java.io FileWriter BufferedWriter]
           [org.apache.commons.math3.complex Complex])
  (:gen-class))

(def step 0.015)
(def k 3)
(def loopmax 20)

(def x_vals (range -2.0 0.6 step))
(def y_vals (range -1.2 1.2 step))
(def c_zero (new Complex 0 0))

(defn fmt [vec]
  (if (= vec "NA")
    "\n"
    (format "%s\t%s\t%s\n" (nth vec 0) (nth vec 1) (nth vec 2))))

(defn compute-mandel [c k loopmax]
  (loop [z c_zero
         n 0]
    (if (and (< n loopmax) (< (.abs z) k))
      (let [z_1 (if (= z c_zero)
                 c
                 (.add (.pow z (new Complex 2 0)) c))]
        (recur z_1 (+ n 1)))
      [(.getReal c) (.getImaginary c) n])))

(defn apply-mandel []
  (let [data (for [x x_vals y y_vals]
               (compute-mandel (new Complex x y) k loopmax))
        ;;data-with-na (mapcat identity (map #(concat % '("NA")) (partition-by #(nth % 0) data)))
        data-with-na (reduce concat (map #(concat % '("NA")) (partition-by #(nth % 0) data)))
        ]
    (apply str (map fmt data-with-na))))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (let [data (time (apply-mandel))
        bw (new BufferedWriter (new FileWriter "mandel.data"))]
    (time
     (do
       (.write bw data)
       (.close bw))))
  (sh "./draw.sh")
  (sh "open" "mandel.png"))
