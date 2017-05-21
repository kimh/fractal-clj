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

(defn mark-point [canvas x y marker]
  (let [_y (- y 1)
        _x (- x 1)
        row (nth canvas _y)
        new-row (assoc row _x  marker)]
    (assoc canvas _y new-row)))

(defn _draw-line [canvas y start end marker]
  (if (> start end)
    canvas
    (let [_y (- y 1)
          new-canvas (mark-point canvas start y marker)]
      (_draw-line new-canvas y (+ 1 start) end marker))))

(defn draw-line [canvas y start length marker]
  (let [end (+ start (- length 1))]
       (_draw-line canvas y start end marker)))

(defn triangle [canvas x y length marker]
  (loop [cvs canvas
         row y
         start x
         lgh length]
    (if (> 1 lgh)
      cvs
      (recur (draw-line cvs row start lgh marker) (- row 1) (+ start 1) (- lgh 2)))))

(defn r-triangle [canvas x y length marker]
  (loop [cvs canvas
         row y
         start x
         lgh length]
    (if (> 1 lgh)
      cvs
      (recur (draw-line cvs row start lgh marker) (+ row 1) (+ start 1) (- lgh 2)))))

(defn get-midpoint [triangle-canvas]
  (let [length (/ (- (count (first triangle-canvas)) 1) 2)
        x (/ (+ 1 (count (first triangle-canvas))) 3)
        y (+ 1 (get-height length))]
    {:length length :x x :y y}))

(defn sip [triangle-canvas]
  (let [midpoint (get-midpoint triangle-canvas)
        length (:length midpoint)
        x (:x midpoint)
        y (:y midpoint)]
    (r-triangle triangle-canvas x y length ".")))

(defn start []
  (let [initial-length 11
        initial-canvas (triangle (create-canvas initial-length) 1 (get-height initial-length) initial-length "*")]
    (draw (sip initial-canvas))))

(start)
