#!/bin/bash

IMAGE_ID=${docker.repository}:${docker.bolao.name}

docker build -t $IMAGE_ID -f Dockerfile-Bolao . 
