#!/bin/sh
readonly TAG=2
readonly DOCKER_IMAGE=alfredobarron/launcher
docker build -t "$DOCKER_IMAGE:$TAG" .