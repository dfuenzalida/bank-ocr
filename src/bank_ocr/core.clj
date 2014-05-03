(ns bank-ocr.core)

(def base-digits
  [" _     _  _     _  _  _  _  _ "
   "| |  | _| _||_||_ |_   ||_||_|"
   "|_|  ||_  _|  | _||_|  ||_| _|"])

(defn in-triplets
  "Splits the 3 lines of numbers seqs of strings of 3 chars each"
  [lines]
  (map #(re-seq #".{3}" %) lines))

(defn digit-keys
  "Group the digit groups (first items from each line, 2nd from each line...)"
  [triplets]
  (apply map str triplets))

;; Print a digit from above with:
;; (println (interpose "\n" (re-seq #".{3}" (nth digit-keys 4))))

(def string-to-digit
  "Map from string representation of digits => digit"
  (zipmap (digit-keys (in-triplets base-digits)) ;; keys
          (map str (range (inc 9)))))            ;; values

(defn string->digit [key]
  "Looks for a representation of a digit or returns '?' for unknown"
  (get string-to-digit key "?"))

(defn parse-lines
  "Parses a seq of 3 lines into a string of numbers or question marks"
  [lines]
  (->> lines
       (in-triplets)
       (digit-keys)
       (map string->digit)
       (apply str)))

;; Story 2
(defn valid-account?
  "Check if an account number is valid by computing the checksum"
  [num]
  (let [reversed-digits (map #(Integer. (str %)) (reverse (str num)))
        product         (map * reversed-digits (range 1 (inc 9)))
        total           (reduce + product)]
    (zero? (mod total 11))))

;; Story 3
(defn status
  "Checks if a number is (ILL)egible or has an (ERR)oneous checksum or is OK (empty string)"
  [num]
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

