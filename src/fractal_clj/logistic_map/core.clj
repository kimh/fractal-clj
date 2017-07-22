(ns fractal-clj.logistic-map.core
  (use [clojure.string :only [join]]))

(defn logistic-map
  [initial r iterate]
  (loop [x_n initial
         loop_count 0
         plots []]
    (if (= loop_count iterate)
      plots
      (let [x_n1 (* (* r x_n)
                    (- 1 x_n))]
        (recur x_n1 (inc loop_count) (conj plots x_n))))))

;; (logistic-map 0.8 2.1 10)

(defn bif-logistic-map
  [x0 r-min r-max step iterate remove]

  (loop [x_n x0
         r r-min
         plots []]

    (if (> r r-max)
      plots
      (let [points (logistic-map x0 r iterate)
            pls [r (drop remove points)]]
        (recur x0 (+ r step) (conj plots pls))))))

(defn write-to-file
  [data]
  (let [interleaved (map (fn [data] (interleave
                                     (repeat (first data))
                                     (first (rest data)))) data)
        data (join (map
                    (fn [x]
                      (format "%s %s\n" (first x) (last x)))
                    (partition 2 (flatten interleaved))))]

    (spit "data/logistic-map.data" data)))

(defn -main []
  (let [data (bif-logistic-map 0.2 2.4 4 0.01 1000 5)]
    (write-to-file data)))
