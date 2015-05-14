(ns monads)

; Monads

;--------------------------------------------------
; Identity decider
;--------------------------------------------------

;(def identity-patcher identity)
(defn identity-decider
  [step-value rest-fn]
  (rest-fn step-value))

;(let [a (+ 1 2)
;      b (inc a)
;      c (* a b)]
;  (* a b c))

; (domonad identity-decider
;   [a (+ 1 2)
;    b (inc a)
;    c (* a b)]
;   (* a b c))

; examples

(identity-decider 
  (+ 1 2) 
  (fn [a] (identity-decider
            (inc a)
            (fn [b] (identity-decider
                      (* a b)
                      (fn [c] 
                        (* a b c)))))))

;--------------------------------------------------
; Maybe decider
;--------------------------------------------------

;(def maybe-patcher identity)
(defn maybe-decider
  [step-value rest-fn]
  (if (nil? step-value) 
    nil
    (rest-fn step-value)))

;(let [a (+ 1 2)
;      b nil
;      c (* a b)]
;  (* a b c))

; (domonad maybe-decider
;   [a (+ 1 2)
;    b nil
;    c (* a b)]
;   (* a b c))

; examples

(maybe-decider 
  (+ 1 2) 
  (fn [a] (maybe-decider
            nil
            (fn [b] (maybe-decider
                      (* a b)
                      (fn [c] 
                        (* a b c)))))))

;--------------------------------------------------
; Sequence decider
;--------------------------------------------------

(def sequence-patcher list)

(defn sequence-decider
  [step-value rest-fn]  
  (apply concat
         (map rest-fn
              step-value)))

; examples

;(domonac sequence-m
;  [a [7 5 3]
;   b [100 10 1]]
;  (* a b))

(sequence-decider
  [7 5 3]
  (fn [a] (sequence-decider
            [100 10 1]
            (fn [b] 
              (* a b)))))

;--------------------------------------------------
; Logging decider
;--------------------------------------------------

(defn new-pair [value log] {:value value :log log})

(defn starting-pair [value] (new-pair value nil))

(defn logging-patcher [body-value]
  (starting-pair body-value))

(defn value-with-bigger-log [pair value-to-log]
  (new-pair (:value pair)
            (cons value-to-log (:log pair))))

(defn log [value]
  (^:logger (fn [] value)))  ;same as ^{:logger: true} (fn [] value), which is the same as (with-metadata (fn [] value) {:logger: true})

(defn loggable-value-container? [thing]
  (:logger (meta thing)))

(defn logging-decider [step-value rest-fn]
  (if (loggable-value-container? step-value)
    (value-with-bigger-log 
      (rest-fn (step-value))
      (step-value))
    (rest-fn step-value)))

; Logging-decider to log everything
;(defn logging-decider [step-value rest-fn]
;  (value-with-bigger-log 
;    (rest-fn step-value)
;    step-value))

; examples

(logging-decider
  5
  (fn [a]
    (logging-decider
      (log (inc a))
      (fn [b]
        (logging-decider
          (log (+ a b))
          (fn [c]
            (* a b c)))))))
