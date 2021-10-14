#!/usr/bin/env bash

paths=(./Square/Main.jack ./Square/Square.jack ./Square/SquareGame.jack)


for element in "${paths[@]}"
do
    echo tokenize "$element"
    ./token-tester.sh "$element"
done

./compareSquare.sh