#!/usr/bin/env bash

filenames=(MainT.xml SquareT.xml SquareGameT.xml)

./compare.sh "./ExpressionLessSquare/test/Main.xml" "./ExpressionLessSquare/Main-actual.xml"
# for element in "${filenames[@]}"
# do
#     ./compare.sh "./ExpressionLessSquare/test/$element" "./ExpressionLessSquare/$element"
# done