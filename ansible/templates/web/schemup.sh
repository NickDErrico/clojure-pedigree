#!/usr/bin/env bash

cd {{ project_dir }}
sudo docker exec -it $(sudo docker-compose ps | grep 'schemup' | awk '{print $1}') schemup commit
