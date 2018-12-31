#!/usr/bin/env bash

#(cd docker; docker-compose up)

export DOCKER_DIR=pact-docker

echo "STARTING DOCKER"
(cd $DOCKER_DIR
echo Running Docker from root directory of:
pwd
. ./build-pact-postgres-image.sh
. ./start-postgres.sh
. ./start-pactbroker.sh
)

docker ps -a