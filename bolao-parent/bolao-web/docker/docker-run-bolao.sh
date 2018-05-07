#!/bin/bash

docker rm ${docker.bolao.name}

docker run -it -p 4848:4848 -p 8080:8080 --link ${docker.mysql.name} --name ${docker.bolao.name} ${docker.repository}:${docker.bolao.name}