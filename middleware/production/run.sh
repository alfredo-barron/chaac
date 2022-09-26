#!/bin/sh
docker run \
    --detach=true \
    --env NODE_ID=middle1 \
    --env NODE_URL=middleware \
    --env NODE_PORT=80 \
    --env URL_DB=influxdb \
    --env URL_LAUNCHER=launcher:5000 \
    --env LOG_PATH=/app/logs \
    --label chaac \
    --label chaac=middleware \
    --name=middleware \
    --network=my-net \
    --publish=8081:80 \
    --restart=always \
    --volume=$(pwd)/logs:/app/logs:rw \
    alfredobarron/middleware:1.8

docker run \
    --detach=true \
    --name influxdb \
    --network=my-net \
    --env DOCKER_INFLUXDB_INIT_USERNAME=root \
    --env DOCKER_INFLUXDB_INIT_PASSWORD=root \
    --env DOCKER_INFLUXDB_INIT_ORG=my-org \
    --env DOCKER_INFLUXDB_INIT_BUCKET=my-bucket \
    --label chaac \
    --label chaac=middleware \
    --publish 8086:8086 \
    --restart=always \
    --volume=$(pwd)/db:/var/lib/influxdb \
    influxdb:1.8.4-alpine
