#!/usr/bin/env bash

#!/bin/bash

. docker-common.sh

running=`docker ps -a -f name="${PACT_BROKER_CONTAINER_NAME}" -q`

if [ -z $running ]; then

  echo "Starting docker container [$PACT_BROKER_CONTAINER_NAME] ..."

  docker pull dius/pact-broker

  docker run --name pact-broker --link $POSTGRES_CONTAINER_NAME:postgres \
   -e PACT_BROKER_DATABASE_USERNAME=$PACT_BROKER_DATABASE_USERNAME \
   -e PACT_BROKER_DATABASE_PASSWORD=$PACT_BROKER_DATABASE_PASSWORD \
   -e PACT_BROKER_DATABASE_HOST=$PACT_BROKER_DATABASE_HOST \
   -e PACT_BROKER_DATABASE_NAME=$PACT_BROKER_DATABASE_NAME -d -p 80:80 $PACT_BROKER_CONTAINER_NAME

  echo " done!"

else

  echo "Docker container [$PACT_BROKER_CONTAINER_NAME] is running"
fi
