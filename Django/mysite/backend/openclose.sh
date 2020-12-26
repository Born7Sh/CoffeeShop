#!/bin/bash

echo "\n"
date >> openclose.txt
echo "\n"
curl http://13.125.237.247:8000/openclose/ >> openclose.txt

echo "\n"

