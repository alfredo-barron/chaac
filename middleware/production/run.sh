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
  --label chaac \
  --label chaac=launcher \
  --name=launcher \
  --network=my-net \
  --publish=5000:5000 \
  --detach=true \
  --restart=always \
  --volume=/var/run/docker.sock:/var/run/docker.sock:ro \
  --privileged \
  --device=/dev/kmsg \
  alfredobarron/launcher:1