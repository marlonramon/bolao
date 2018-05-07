#!/bin/bash

docker rm ${docker.mysql.name}

docker run -it --name ${docker.mysql.name} -p 3306:3306 -v $(pwd)/mysql-data:/var/lib/mysql -e MYSQL_DATABASE=bolao -e MYSQL_USER=bolao -e MYSQL_PASSWORD=bolao -e MYSQL_ROOT_PASSWORD=root -d mysql:5.7 --lower_case_table_names=1