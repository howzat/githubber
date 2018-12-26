#!/usr/bin/env bash

export POSTGRES_PASSWORD=shhh
export POSTGRES_USER=admin
export PGDATA=/Users/benoit/projects/dockermounts/postgresql/data/pgdata
export POSTGRES_DB=pactbroker-db

export PACT_BROKER_DATABASE_ADAPTER=postgres
export PACT_BROKER_DATABASE_USERNAME=pactbrokeruser
export PACT_BROKER_DATABASE_PASSWORD=pactuserpassword
export PACT_BROKER_DATABASE_HOST=postgres
export PACT_BROKER_DATABASE_NAME=pactbroker

export PACT_BROKER_LOG_LEVEL=DEBUG