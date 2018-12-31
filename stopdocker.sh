#!/usr/bin/env bash

#(cd docker; docker-compose up)

export DOCKER_DIR=pact-docker

echo "STOPPING DOCKER from" `pwd`

(cd $DOCKER_DIR
. ./stop-postgres.sh
. ./stop-pactbroker.sh
)

docker ps -a