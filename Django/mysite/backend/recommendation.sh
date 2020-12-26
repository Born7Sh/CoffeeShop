#!/bin/bash

echo "\n"
date >> recommendation.txt
echo "\n"
curl http://13.125.237.247:8000/recommendation/ >> recommendation.txt

echo "\n"

