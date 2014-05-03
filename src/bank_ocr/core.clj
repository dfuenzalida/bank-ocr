(ns bank-ocr.core)

(def base-digits
  ["    _  _     _  _  _  _  _ "
   "  | _| _||_||_ |_   ||_||_|"
   "  ||_  _|  | _||_|  ||_| _|"])

;; Split the 4 lines above in strings of 3 chars each
(defn in-triplets [lines]
  (map #(re-seq #".{3}" %) lines))

;; Group the collections above (first items from each line, 2nd from each line...)
(defn digit-keys [triplets]
  (apply map str triplets))

;; Print a digit from above with:
;; (println (interpose "\n" (re-seq #".{3}" (nth digit-keys 4))))

(def string->digit
  (zipmap (digit-keys (in-triplets base-digits)) ;; keys
          (map str (range 1 (inc 9)))))          ;; values

(def reversed-nums
  [" _  _  _  _  _     _  _       "
   "|_||_|  ||_ |_ |_| _| _|  ||_|"
   " _||_|  ||_| _|  | _||_   |  |"])

(defn -main [& args]
  (let [parsed-keys (digit-keys (in-triplets reversed-nums))]
    (println (apply str (map string->digit parsed-keys)))))
