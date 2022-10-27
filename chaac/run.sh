#!/bin/sh
docker run \
    --detach=true \
    --env NODE_ID=chaac \
    --env CHAAC=chaac \
    --env PUBLIC_PORT=80 \
    --env DB_CHAAC=chaac_db \
    --env LAUNCHER_CHAAC=launcher:5000 \
    --env LOG_PATH=/app/logs \
    --label chaac \
    --label chaac=chaac \
    --name=chaac \
    --network=chaac \
    --publish=8080:80 \
    --restart=always \
    --volume=$(pwd)/logs:/app/logs:rw \
    alfredobarron/chaac:1

docker run \
    --detach=true \
    --name chaac_db \
    --network=chaac \
    --env DOCKER_INFLUXDB_INIT_USERNAME=root \
    --env DOCKER_INFLUXDB_INIT_PASSWORD=root \
    --env DOCKER_INFLUXDB_INIT_ORG=my-org \
    --env DOCKER_INFLUXDB_INIT_BUCKET=my-bucket \
    --label chaac \
    --label chaac=chaac \
    --publish 8086:8086 \
    --restart=always \
    --volume=$(pwd)/db:/var/lib/influxdb \
    influxdb:1.8.4-alpine
