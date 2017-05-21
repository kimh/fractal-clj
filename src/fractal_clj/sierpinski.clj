(ns fractal-clj.sierpinski)

(defn create-canvas []
  (vec (repeat 3 (vec (repeat  5 " ")))))

(defn draw [canvas]
  (doseq [row canvas]
    (println row)))

;; 1 is row
;; (assoc [[1 2 3 ][1 2 3]] 1 [1 9 3])

(defn mark-point [canvas x y]
  (let [_y (- y 1)
        _x (- x 1)
        row (nth canvas _y)
        new-row (assoc row _x  "*")]
    (assoc canvas _y new-row)))

(defn _draw-line [canvas y start end]
  (if (> start end)
    canvas
    (let [_y (- y 1)
          new-canvas (mark-point canvas start y)]
      (_draw-line new-canvas y (+ 1 start) end))))

(defn draw-line [canvas y start length]
  (let [end (+ start (- length 1))]
       (_draw-line canvas y start end)))

(defn triangle [canvas]
  (loop [cvs canvas
         y 1
         start 1
         length (count (first canvas))]
    (if (> 1 length)
      cvs
      (recur (draw-line cvs y start length) (+ y 1) (+ start 1) (- length 2)))))
