# Chaac

<img src="images/logo_chaac.png" width="200">

***
_Chaac es una herramienta para construir esquemas de distribución de datos sensibles al contexto computacional para el manejo de los recursos de almacenamiento para aplicaciones que son construidas y desplegadas en el entorno de microservicios._

## Tabla de contenido
1. [Componentes](#componentes)
2. [Contextual Data Monitor](#data-context)
3. [Middleware](#middleware)
4. [Chaac Client](#chaac-client)
5. [Data Pool](#data-pool)
6. [Data Container](#data-container)
7. [Data Client](#data-client)

## Componentes

Chaac esta compuesto de 6 módulos que son los siguientes:

- Contextual Data Monitor
- Middleware
- Chaac Client
- Data Pool
- Data Container
- Data Client

![Arquitectura middleware](/images/Chaac.png)

## Contextual Data Monitor

Para obtener el contexto computacional se despliegan los siguientes módulos, en los hosts donde van a construir los esquemas de distribución:

### Lanzador 

```
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
```

### Monitor

cAdvisor

```
docker run \
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
```

## Middleware

Es un software intermediario entre los microservicios y el almacenamiento en la nube. 

Para desplegar este componente se hace uso de los siguientes comandos:

```
docker run \
    --detach=true \
    --env MIDDLEWARE_ID=chaac \
    --env MIDDLEWARE_URL=middleware \
    --env WEB_PORT=80 \
    --env DB_URL=influxdb \
    --env LAUNCHER_URL=launcher:5000 \
    --env LOG_PATH=/app/logs \
    --label chaac \
    --label chaac=middleware \
    --name=middleware \
    --network=my-net \
    --publish=80:80 \
    --restart=always \
    --volume=$(pwd)/logs:/app/logs:rw \
    alfredobarron/middleware:2

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

```

## Chaac Client

Chaac client

## Data Pool

DataPool

DataSchemeDistribution

Map of Balls

Map of Bins
http://127.0.0.1:8080/api/v1/containers/

Allocation
http://127.0.0.1:8080/allocation

Location

## Data Container

DataFlow Read

Local Cache Hit
Remote Cache Hit
Cache Miss
Cache Skip

DataFlow Write


            Container FileSystem     |       Host FileSystem

Image FS    /     read-write  ->  (Overlay-    -> /var/lib/docker/overlay/1/x
            /...              ->    drive)     -> /var/lib/docker/overlay/1/y

In-Memory   /tmp  read-write  ->   tmpfs

Bind-Mount  /etc/default.conf  read            -> /home/currentuser/example.conf

Volume      /var/logs          read-write      -> /var/lib/docker/volumes/_data
            /data              read-write      -> /home/currentuser/data


## Data Client

Write
    Request DataPool
        Allocation
        
Read
