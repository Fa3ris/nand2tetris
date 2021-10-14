#!/usr/bin/env bash

paths=(./ArrayTest/Main.jack)


for element in "${paths[@]}"
do
    echo tokenize "$element"
    ./token-tester.sh "$element"
done

./compareArrayTest.sh