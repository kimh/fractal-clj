(ns fractal-clj.core
  (:require [clojure.math.numeric-tower :as math])
  (:gen-class))

(import org.apache.commons.math3.complex.Complex)

(def step 0.0015)
(def k 3)

(def x_vals (range -2.0 0.6 step))
(def y_vals (range -1.2 1.2 step))
(def c_zero (new Complex 0 0))

(defn to_mandel [c k loopmax]
  (loop [z c_zero
         n 0]
    (if (and (< n loopmax) (< (.abs z) k))
      (let [z_1 (if (= z c_zero)
                 c
                 (.add (.pow z (new Complex 2 0)) c))]
        (recur z_1 (+ n 1)))
      [(.getReal c) (.getImaginary c) n])))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (let [c (new Complex 1 1)]
    (println (. c getReal))
    (println (. c getImaginary))))

