#!/bin/bash

echo "\n"
date >> gender_stats.txt
echo "\n"
curl http://13.125.237.247:8000/gender_stats/ >> gender_stats.txt

echo "\n"

