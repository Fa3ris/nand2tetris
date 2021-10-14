#!/usr/bin/env bash

echo compare files "$1" and "$2"
toolsDir=$PWD/../../tools


${toolsDir}/TextComparer.bat $@