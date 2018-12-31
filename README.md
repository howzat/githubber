# GitHubber

GitHubber is a noddy project that allows one to easily perform operations against groups of microservices (GitHub projects), such as update and checkout. The real reason for it's existence is to provide a semi-realistic set of problems to allow me to learn something.

* Tagless Final
* Blaze HTTP
* Pact Contract Testing
* Docker

Running
=

 Starting Postgres and Pact (running in Docker)
 -
 The directory currently named pact-docker holds a Docker compose file that starts the requisite Postgres and Pact Broker containers.
```
cd pact-docker
docker-compose up
docker ps
```

*Postgres Queries using Pygmy CLI*

Docker Compose starts a new network when bringing the containers online. To link a new container you need to know the name of that network.

Running `docker network ls` will show all the networks known to Docker. You can identify which applies to this project since the folder `docker-compose` is run from will form part of the network name.

For example: 
```
NETWORK ID          NAME                          DRIVER              SCOPE
b7f1da411060        pact-docker-compose_default   bridge              local
171673d3c506        pact-docker_default           bridge              local



docker run -it --rm --link pactbroker-db:postgres pygmy/pgcli
```