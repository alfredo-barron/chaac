# Chaac
***
_Chaac es una herramienta para construir esquemas de distribución de datos sensibles al contexto computacional para el manejo de los recursos de almacenamiento para aplicaciones que son construidas y desplegadas en el entorno de microservicios._

## Tabla de contenido
1. [Componentes](#componentes)
2. [Data Context](#data-context)
3. [Chaac Client](#chaac-client)
4. [Middleware](#middleware)
5. [Data Pool](#data-pool)
6. [Data Container](#data-container)
7. [Data Client](#data-client)

## Componentes

Chaac esta compuesto de 6 módulos que son los siguientes:

- Data Context
- Chaac Client
- Middleware
- Data Pool
- Data Container
- Data Client

![Arquitectura middleware](/images/Chaac.png)

## Data Context

Para obtener el contexto computacional se despliegan los siguientes módulos, en los hosts donde van a construir los esquemas de distribución:

### Lanzador 

```
docker run \
  --label distribution_scheme \
  --label distribution_scheme=launcher \
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

### Monitor de datos contextuales

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


## Chaac Client

Chaac client

## Middleware

Es un software intermediario entre los microservicios y el almacenamiento en la nube. 

Para desplegar este componente se hace uso de los siguientes comandos:

```
docker run \
    --detach=true \
    --env NODE_ID=middle1 \
    --env NODE_URL=middleware \
    --env NODE_PORT=80 \
    --env URL_DB=influxdb \
    --env URL_LAUNCHER=launcher:5000 \
    --env LOG_PATH=/app/logs \
    --label distribution_scheme \
    --label distribution_scheme=middleware \
    --name=middleware \
    --network=my-net \
    --publish=8081:80 \
    --restart=always \
    --volume=$(pwd)/logs:/app/logs:rw \
    alfredobarron/middleware:1.8

docker run \
  --label distribution_scheme \
  --label distribution_scheme=launcher \
  --name=launcher \
  --network=my-net \
  --publish=5000:5000 \
  --detach=true \
  --restart=always \
  --volume=/var/run/docker.sock:/var/run/docker.sock:ro \
  --privileged \
  --device=/dev/kmsg \
  alfredobarron/launcher:1
```

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
