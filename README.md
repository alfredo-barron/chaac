# Chaac

<img src="images/logo_chaac.png" width="200">

***
_Chaac es una herramienta para construir esquemas de distribución de datos sensibles al contexto computacional para el manejo de los recursos de almacenamiento para aplicaciones que son construidas y desplegadas en el entorno de microservicios._

## Tabla de contenido
1. [Componentes](#componentes)
2. [Chaac](#middleware)
3. [Chaac Context Awaraness](#data-context)
4. [Chaac Client](#chaac-client)
5. [Chaac Cenote](#data-pool)
6. [Chaac Bin](#data-container)
7. [Chaac Client Libray](#data-client)

## Componentes

Chaac tiene 5 módulos que lo complementan y son los siguientes:

- Chaac Context Awaraness
- Chaac Client
- Chaac Cenote
- Chaac Bin
- Chaac Client Library

![Arquitectura middleware](/images/Chaac.png)

## Chaac

Es un software intermediario entre los microservicios y el almacenamiento en la nube. 

Para iniciar Chaac se hace uso del siguiente comando:

```
docker compose -f chaac.yml up -d
```

Para bajar Chaac es el siguiente comando:

```
# Apagar todo
docker compose -f chaac.yml down
```


## Chaac Context Awaraness

Para obtener el contexto computacional se despliega el siguiente comando en cada anfitrión:

```
docker compose -f chaac-context.yml up -d
```

Para bajar Chaac es el siguiente comando:

```
# Apagar todo
docker compose -f chaac-context.yml down
```

## Chaac Client

Chaac client

## Chaac Cenote

DataPool

DataSchemeDistribution

Map of Balls

Map of Bins
http://127.0.0.1:8080/api/v1/containers/

Allocation
http://127.0.0.1:8080/allocation

Location

## Chaac Bin

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


## Chaac Client Library

Write
    Request DataPool
        Allocation
        
Read
