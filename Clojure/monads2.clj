(ns monads)

(defn half-double [n]
  [(/ n 2) (* n 2)])

(defn inc-int [n]
  [(+ 5 n) (+ 10 n)])

(defn m-bind [ns int-fn]
  (apply concat
         (map int-fn ns)))

(defn do-both [n]
  (m-bind 
    (half-double n) 
    inc-int))

(half-double 10)
(inc-int 3)
(do-both 8)