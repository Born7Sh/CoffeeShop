#!/bin/bash

echo "\n"
date >> tag.txt
echo "\n"
curl http://13.125.237.247:8000/tag/ >> tag.txt

echo "\n"

