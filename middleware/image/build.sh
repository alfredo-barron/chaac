#!/bin/sh
readonly TAG=2
readonly DOCKER_IMAGE=alfredobarron/middleware
docker build -t "$DOCKER_IMAGE:$TAG" .