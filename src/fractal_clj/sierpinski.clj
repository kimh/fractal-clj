(ns fractal-clj.sierpinski)

(defn get-height [length]
  (loop [lgh length
         height 0]
    (if (< lgh 1)
      height
      (recur (- lgh 2) (+ height 1)))))

(defn create-canvas [length]
  (assert (odd? length))
  (let [height (get-height length)]
    (vec (repeat height (vec (repeat length " "))))))

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

(defn triangle [canvas x y length]
  (loop [cvs canvas
         row y
         start x
         lgh length]
    (if (> 1 lgh)
      cvs
      (recur (draw-line cvs row start lgh) (- row 1) (+ start 1) (- lgh 2)))))


(draw (triangle (create-canvas 11) 1 (get-height 11) 11))

(let [length 11]
  (draw (triangle (create-canvas length) 1 (get-height length) length)))



(draw (triangle (triangle (create-canvas 11) 1 1 5) 4 4 5))
