#!/bin/bash

read -s “Directory where to save the vehicle information: ” dir

docker run -p 8080:8080 -v $dir:/vehicles -t glaesec/leasing-accountant:latest
