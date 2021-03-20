#!/bin/sh

docker run --rm --name leasing-ui -d -p 8082:80 glaesec/leasing-accountant-ui:latest
