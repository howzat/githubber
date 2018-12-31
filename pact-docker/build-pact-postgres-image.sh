#!/usr/bin/env bash

. ././docker-common.sh

(cd postgres-docker; docker build -t $POSTGRES_CONTAINER_NAME .)