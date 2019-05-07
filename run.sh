#!/bin/sh

docker run -p 8080:8080 -v /home/pi/vehicles:/vehicles -t glaesec/leasing-accountant:latest
