(ns bank-ocr.core)

(def base-digits
  [" _     _  _     _  _  _  _  _ "
   "| |  | _| _||_||_ |_   ||_||_|"
   "|_|  ||_  _|  | _||_|  ||_| _|"])

;; Split the 3 lines above in strings of 3 chars each
(defn in-triplets [lines]
  (map #(re-seq #".{3}" %) lines))

;; Group the collections above (first items from each line, 2nd from each line...)
(defn digit-keys [triplets]
  (apply map str triplets))

;; Print a digit from above with:
;; (println (interpose "\n" (re-seq #".{3}" (nth digit-keys 4))))

(def string-to-digit
  (zipmap (digit-keys (in-triplets base-digits)) ;; keys
          (map str (range (inc 9)))))            ;; values

(defn string->digit [key]
  (get string-to-digit key "?"))

(defn parse-lines [lines]
  (->> lines
       (in-triplets)
       (digit-keys)
       (map string->digit)
       (apply str)))

;; Story 2: check if an account number is valid by computing the checksum
(defn valid-account? [num]
  (let [reversed-digits (map #(Integer. (str %)) (reverse (str num)))
        product         (map * reversed-digits (range 1 (inc 9)))
        total           (reduce + product)]
    (zero? (mod total 11))))

;; Story 3: compute status
(defn status [num]
  (cond
   (.contains num "?") "ILL"
   (valid-account? num) ""
   :else "ERR"))

(def reversed-nums
  [" _  _  _  _  _  _     _  _       "
   "|_||_|| |  ||_ |_ |_| _| _|  ||_|"
   " _||_||_|  ||_| _|  | _||_   |  |"])

(defn -main [& args]
  (println "Expected: 98076543214")
  (println "Parsed  :"(parse-lines reversed-nums)))

