#!/usr/bin/env bash

# assemble a xxx.asm file into a xxx.hack file 
# argument: the path to xxx.asm file
java -jar lib/hack-assembler.jar "$@"