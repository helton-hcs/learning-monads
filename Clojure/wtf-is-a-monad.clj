(use 'clojure.algo.monads)

;----------------------
; Monads introduction
;----------------------

(defn f []
  (let [a 1
        b (+ a 1)
        c (+ a b)]
    (* a b c)))

(println "f:" (f))

(defn ff []
  ((fn [a]
     ((fn [b]
        ((fn [c]
           (* a b c))
         (+ a b)))
     (+ a 1)))
   1))

(println "ff:" (ff))

(defn lt [v f] (f v))

(defn fff []
  (lt 1
      (fn [a]
        (lt (+ 1 a)
            (fn [b]
              (lt (+ a b)
                  (fn [c]
                    (* a b c))))))))

(println "fff:" (fff))

;----------------------
; Identity monad
;----------------------

(defmonad let-m
  [m-bind (fn [v f] (f v))
   m-result identity])

(defn mf []
  (domonad let-m [a 1
                  b (+ 1 a)
                  c (+ a b)]
           (* a b c)))

(println "mf:" (mf))

;----------------------
; Maybe monad
;----------------------

(defn fragile [a b c]
  (+ a b c))

(println "fragile-------------")
(println (fragile 1 2 3))
;(println (fragile 1 nil 2)) ; blow up!

(try
  (println (fragile 1 nil 2))
  (catch Exception e
    (println "OUCH! " (-> e .getClass .getName))))

(println "safe---------------")

(defmonad no-nil-m
  [m-result identity
   m-bind (fn [mv f] 
            (if (nil? mv) 
              nil 
              (f mv)))])

(defn safe-fragile [a b c]
  (domonad no-nil-m 
           [safe-a a
            safe-b b
            safe-c c]
           (fragile safe-a safe-b safe-c)))

(println (safe-fragile 1 2 3))
(println (safe-fragile 1 nil 3))

(def lifted-safe-fragile 
  (with-monad 
    no-nil-m 
    (m-lift 3 fragile)))

(println "lifted-safe---------------")

(println (lifted-safe-fragile 1 2 3))
(println (lifted-safe-fragile 1 nil 3))

(println "done---------------")

;----------------------
; Dot monad
;----------------------

(defmonad dot-m
  [m-bind 
     (fn [mv f] 
       (f (count mv)))
   m-result 
     (fn [n] 
       (apply str (repeat n ".")))])

(def add-dots 
  (with-monad dot-m (m-lift 2 +)))
(def mul-dots 
  (with-monad dot-m (m-lift 2 *)))
(def sub-dots 
  (with-monad dot-m (m-lift 2 -)))
(def div-dots 
  (with-monad dot-m (m-lift 2 /)))

(def dcd 
  (with-monad dot-m (m-lift 2 (fn [t u] (+ u (* t 10))))))

(println "           5    10   15   20   25   30   35")
(println "       ....:....:....:....:....:....:....:")
(println "2+4=6 " (add-dots ".." "...."))
(println "3*4=12" (mul-dots "..." "...."))
(println "5-2=3 " (sub-dots "....." ".."))
(println "35    " (dcd "..." "....."))

;----------------------
; List monad
;----------------------

(defmonad list-m
  [m-bind (fn [mv f] (apply concat (map f mv)))
   m-result (fn [v] (list v))])

(println "monad:"
         (domonad list-m
                  [x [1 2 3]
                   y [4 5 6]]
                  (+ x y)))

; list comprehensions are just the sequence monad
(println "for:  "
         (for
           [x [1 2 3]
            y [4 5 6]]
           (+ x y)))

(def mul-list
  (with-monad list-m
              (m-lift 2 *)))

(println "lift: "
         (mul-list [1 2 3] [4 5 6]))