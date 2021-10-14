#!/usr/bin/env bash

filenames=(MainT.xml)

for element in "${filenames[@]}"
do
    ./compare.sh "./ArrayTest/test/$element" "./ArrayTest/$element"
done