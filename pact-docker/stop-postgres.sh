#!/usr/bin/env bash

. docker-common.sh

pd=`docker ps -a -f name=$POSTGRES_CONTAINER_NAME -q`
echo Stopping $POSTGRES_CONTAINER_NAME
docker stop $POSTGRES_CONTAINER_NAME  >/dev/null 2>&1 && docker rm -vf $POSTGRES_CONTAINER_NAME  >/dev/null 2>&1
