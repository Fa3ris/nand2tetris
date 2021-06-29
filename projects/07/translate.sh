#!/usr/bin/env bash

# translate a xxx.vm file into a xxx.asm file 
# argument: the path to xxx.vm file
java -jar lib/vm-translator-1.0-SNAPSHOT.jar "$@"