#!/usr/bin/env bash

filenames=(MainT.xml SquareT.xml SquareGameT.xml)

for element in "${filenames[@]}"
do
    ./compare.sh "./ExpressionLessSquare/test/$element" "./ExpressionLessSquare/$element"
done