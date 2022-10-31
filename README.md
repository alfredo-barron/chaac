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

Chaac se complementa con son los siguientes módulos:

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

Es una piscina de contenedores de datos. Este componente contiene los mapas de recursos y los mapas de datos-recursos que son los esquemas de distribución de datos.

Para iniciar un cenote de manera independiente se hace uso del siguiente comando:

```
docker compose -f chaac-cenote.yml up -d
```

```
# Apagar todo
docker compose -f chaac-cenote.yml down
```

## Chaac Bin

Es una unidad de almacenamiento temporal, que solo realiza operaciones de escritura y lectura de datos.

Para iniciar un contenedor de datos de manera independiente se hace uso del siguiente comando:

```
docker compose -f chaac-bin.yml up -d
```

```
# Apagar todo
docker compose -f chaac-bin.yml down
```


## Chaac Client Library

Write
    Request DataPool
        Allocation
        
Read
