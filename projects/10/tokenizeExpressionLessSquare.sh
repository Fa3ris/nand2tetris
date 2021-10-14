#!/usr/bin/env bash

paths=(./ExpressionLessSquare/Main.jack ./ExpressionLessSquare/Square.jack ./ExpressionLessSquare/SquareGame.jack)


for element in "${paths[@]}"
do
    echo tokenize "$element"
    ./token-tester.sh "$element"
done

./compareExpressionLessSquare.sh