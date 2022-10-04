#!/bin/sh
docker run \
    --detach=true \
    --env MIDDLEWARE_ID=chaac \
    --env MIDDLEWARE_URL=middleware \
    --env PUBLIC_PORT=80 \
    --env DB_URL=influxdb \
    --env LAUNCHER_URL=launcher:5000 \
    --env LOG_PATH=/app/logs \
    --label chaac \
    --label chaac=middleware \
    --name=middleware \
    --network=chaac \
    --publish=8080:80 \
    --restart=always \
    --volume=$(pwd)/logs:/app/logs:rw \
    alfredobarron/middleware:2

docker run \
    --detach=true \
    --name influxdb \
    --network=chaac \
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
