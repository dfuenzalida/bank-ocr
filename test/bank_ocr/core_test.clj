(ns bank-ocr.core-test
  (:require [clojure.test :refer :all]
            [bank-ocr.core :refer :all]))

;; Test for story #1:
;; Load file "resources/story1.txt" and parse the numbers correctly

(deftest story-1
  (testing "Parsing the resources/story1.txt file"
    (let [filename "resources/story1.txt"
          lines    (with-open [reader (clojure.java.io/reader filename)]
                     (into [] (line-seq reader)))
          lines    (map butlast (partition 4 lines))
          parsed   (map parse-lines lines)]
      (is (= ["000000000" "111111111" "222222222" "333333333"
              "444444444" "555555555" "666666666" "777777777"
              "888888888" "999999999" "123456789"]
             parsed)))))

;; Test for story #2:
;; Validate if the following numbers are valid accounts or not

(deftest story-2
  (testing "Validate account numbers"
    (is (valid-account? 345882865))
    (is (valid-account? 457508000))
    (is (not (valid-account? 664371495)))))

;; Test for story #3:
;; Parse a file with valid-invalid accounts, find good/illegible numbers

(deftest story-3
  (testing "Parsing the resources/story3.txt file"
    (let [filename "resources/story3.txt"
          lines    (with-open [reader (clojure.java.io/reader filename)]
                     (into [] (line-seq reader)))
          lines    (map butlast (partition 4 lines))
          parsed   (map parse-lines lines)]
      (is (= ["000000051" "49006771?" "1234?678?"] parsed))
      (is (= ["" "ILL" "ILL"] (map status parsed))))))

