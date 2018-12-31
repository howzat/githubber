#!/usr/bin/env bash

. docker-common.sh

pd=`docker ps -a -f name=$PACT_BROKER_NAME -q`
echo Stopping $PACT_BROKER_NAME
docker stop $PACT_BROKER_NAME  >/dev/null 2>&1 && docker rm -vf $PACT_BROKER_NAME  >/dev/null 2>&1

