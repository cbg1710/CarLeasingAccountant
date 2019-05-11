#!/bin/bash

echo -n "Enter directory where to save vehicle data and press [ENTER]: "
read dir

docker run -p 8080:8080 -v $dir:/vehicles -t glaesec/leasing-accountant:latest
