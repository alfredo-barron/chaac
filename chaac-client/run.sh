#!/bin/sh
docker run \
    --detach=true \
    --label chaac \
    --label chaac=client \
    --name=chaac_client \
    --network=chaac \
    --restart=always \
    --publish=45000:45000 \
    --volume=$(pwd)/app:/home/:rw \
    fbalderasd/python:3.7-sb python run.py 45000