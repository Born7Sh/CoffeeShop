#!/bin/bash

echo "\n"
date >> age_stats.txt
echo "\n"
curl http://13.125.237.247:8000/age_stats/ >> age_stats.txt

echo "\n"

