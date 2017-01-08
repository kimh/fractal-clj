(ns fractal-clj.core
  (:require [clojure.math.numeric-tower :as math])
  (:use clojure.java.io)
  (:import [java.io FileWriter BufferedWriter]
           [org.apache.commons.math3.complex Complex])
  (:gen-class))

(def step 0.0015)
(def k 3)

(def x_vals (range -2.0 0.6 step))
(def y_vals (range -1.2 1.2 step))
(def c_zero (new Complex 0 0))

(defn compute-mandel [c k loopmax]
  (loop [z c_zero
         n 0]
    (if (and (< n loopmax) (< (.abs z) k))
      (let [z_1 (if (= z c_zero)
                 c
                 (.add (.pow z (new Complex 2 0)) c))]
        (recur z_1 (+ n 1)))
      [(.getReal c) (.getImaginary c) n])))

(defn fmt [vec]
  (if (= vec "NA")
    "\n"
    (format "%s\t%s\t%s\n" (nth vec 0) (nth vec 1) (nth vec 2))))

(defn apply-mandel []
  (let [data (for [x x_vals y y_vals]
               (compute-mandel (new Complex x y) 3 20))
        data-with-na (mapcat identity (map #(concat % (cons "NA" '())) (partition-by #(nth % 0) data)))]
    (apply str (map fmt data-with-na))))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (let [data (time (apply-mandel))
        bw (new BufferedWriter (new FileWriter "mandel.data"))]
    (time
     (do
       (.write bw data)
       (.close bw)))))

;;echo "set term png;
;;set output \"mandel.png\";
;;set grid;
;;set pm3d map;
;;set size square;
;;splot \"mandel.data\"" | gnuplot
