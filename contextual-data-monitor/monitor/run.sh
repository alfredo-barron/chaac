#!/bin/sh
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
  alfredobarron/launcher:2

docker run \
  --label chaac \
  --label chaac=monitor \
  --volume=/:/rootfs:ro \
  --volume=/var/run:/var/run:ro \
  --volume=/sys:/sys:ro \
  --volume=/var/lib/docker/:/var/lib/docker:ro \
  --volume=/dev/disk/:/dev/disk:ro \
  --publish=8081:8080 \
  --detach=true \
  --name=cadvisor \
  --privileged \
  --device=/dev/kmsg \
  gcr.io/cadvisor/cadvisor:latest