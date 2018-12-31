#!/usr/bin/env bash

export WORKSPACE=/Users/benoit/projects

#--- Postgres ----
export POSTGRES_IMAGE_NAME=pact-postgres
export POSTGRES_CONTAINER_NAME=pact-postgres

export POSTGRES_PASSWORD=postgres
export POSTGRES_USER=postgres
export PGDATA=$WORKSPACE/githubber/pact-docker/postgresql/data/pgdata
export POSTGRES_PORT=5454

export PACT_BROKER_DATABASE_ADAPTER=postgres
export PACT_BROKER_DATABASE_USERNAME=pact_db_user
export PACT_BROKER_DATABASE_PASSWORD=pact_password
export PACT_BROKER_DATABASE_HOST=postgres
export PACT_BROKER_DATABASE_NAME=pactbroker_db

#--- PACT ---
export PACT_BROKER_CONTAINER_NAME=dius/pact-broker
export PACT_BROKER_NAME=pact-broker
export PACT_BROKER_LOG_LEVEL=DEBUG