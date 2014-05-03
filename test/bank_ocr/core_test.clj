(ns bank-ocr.core-test
  (:require [clojure.test :refer :all]
            [bank-ocr.core :refer :all]))

;; Test: load file "resources/story1.txt" and parse the numbers correctly

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

