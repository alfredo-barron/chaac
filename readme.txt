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