package config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import access.Auth;
import manager.creation.tree.Graph;
import manager.model.components.Bin;
import manager.model.components.Ball;
import manager.model.components.Pool;
import manager.model.plataform.Host;
import manager.model.plataform.HostMicroservices;
import manager.model.plataform.Microservice;

public class Database {

    private static final List<Host> DB_HOSTS = Collections.synchronizedList(new ArrayList<>());
    private static final List<Microservice> DB_MICROSERVICES = Collections.synchronizedList(new ArrayList<>());
    private static final List<Pool> DB_POOLS = Collections.synchronizedList(new ArrayList<>());
    private static final List<Bin> DB_BINS = Collections.synchronizedList(new ArrayList<>());
    private static final ConcurrentHashMap<String, Ball> DB_BALLS = new ConcurrentHashMap<>();
    public static final Graph GRAPH = new Graph();
    public static final Auth AUTH = new Auth();

    // CRUD hosts
    public static boolean existsHost(String id) {
        return !DB_HOSTS.contains(new Host(id));
    }

    public static void createHost(HostMicroservices hostMicroservices) {
        DB_HOSTS.add(hostMicroservices.getHost());
        DB_MICROSERVICES.addAll(hostMicroservices.getMicroservices());
    }

    public static Collection<Host> getAllHost() {
        return DB_HOSTS;
    }

    public static Host getHost(String id) {
        return DB_HOSTS.stream()
                .filter(h -> h.getId().equals(id))
                .findAny()
                .orElse(null);
    }

    // CRUD Microservices
    public static boolean existsHostMicroservice(String microserviceId) {
        return !DB_MICROSERVICES.contains(new Microservice(microserviceId));
    }

    public static Microservice getMicroservice(String id) {
        return DB_MICROSERVICES.stream()
                .filter(m -> m.getId().equals(id))
                .findAny()
                .orElse(null);
    }

    public static Collection<Microservice> getAllMicroservices() {
        return DB_MICROSERVICES;
    }

    public static Collection<Microservice> getAllMicroservicesHost(String hostId) {
        Predicate<Microservice> byHost = microservice -> microservice.getHostId().equals(hostId);
        return DB_MICROSERVICES.stream().filter(byHost).collect(Collectors.toList());
    }

    public static Microservice getMicroserviceHost(String hostId, String microserviceId) {
        Predicate<Microservice> byHost = microservice -> microservice.getHostId().equals(hostId);
        Predicate<Microservice> byId = microservice -> microservice.getId().equals(microserviceId);
        Predicate<Microservice> where = byHost.and(byId);

        return DB_MICROSERVICES.stream()
                .filter(where)
                .findAny()
                .orElse(null);
    }

    // CRUD DataPool
    public static boolean existsPool(String id) {
        return !DB_POOLS.contains(new Pool(id));
    }

    public static void createPool(Pool pool) {
        if (existsPool(pool.getId())) {
            pool.createContainer();
            DB_POOLS.add(pool);
            GRAPH.addNode(pool.getId(), pool.getUrl());
        }
    }

    public static Collection<Pool> getAllPools() {
        return DB_POOLS;
    }

    public static Pool getPool(String id) {
        return DB_POOLS.stream()
                .filter(d -> d.getId().equals(id))
                .findAny()
                .orElse(null);
    }

    public static void updateStatusPool(String id, boolean status) {
        DB_POOLS.stream()
                .filter(d -> d.getId().equals(id))
                .findAny().ifPresent(pool -> pool.setStatus(status));
    }

    public static void deletePool(String id) {
        DB_POOLS.remove(new Pool(id));
    }

    // CRUD Bins
    public static boolean existsBin(String id) {
        return !DB_BINS.contains(new Bin(id));
    }

    public static void createBin(Bin bin) {
            if (existsBin(bin.getId())) {
                Pool pool = getPool(bin.getPoolId());

                //String urlSync = "sync-0";
                String urlCloud = "148.247.201.222:56200";

                bin.createContainer(pool.getUrl(), pool.getDataPort(), urlCloud);
                DB_BINS.add(bin);
                GRAPH.addNode(bin.getId(), bin.getUrl());
                String label = bin.getPoolId() + "-" + bin.getId();
                GRAPH.addEdge(label, bin.getPoolId(), bin.getId(), 1);
            }
    }

    public static Collection<Bin> getAllBins() {
        return DB_BINS;
    }

    public static List<Bin> getListBins() {
        return DB_BINS;
    }

    public static Collection<Bin> getAllBinPool(String poolId) {
        Predicate<Bin> byPool = Bin -> Bin.getPoolId().equals(poolId);
        return DB_BINS.stream().filter(byPool).collect(Collectors.toList());
    }

    public static Bin getBin(String id) {
        return DB_BINS.stream()
                .filter(d -> d.getId().equals(id))
                .findAny()
                .orElse(null);
    }


    public static void deleteBin(String id) {
        DB_BINS.remove(new Bin(id));
    }

    // CRUD Balls
    public static boolean existsBall(String id) {
        return !DB_BALLS.containsKey(id);
    }

    public static void createBall(Ball ball) {
        if (!DB_BALLS.containsKey(ball.getId())) {
            DB_BALLS.put(ball.getId(), ball);
        }
    }

    public static Collection<Ball> getAllBalls() {
        return DB_BALLS.values();
    }

    public static Ball getBall(String id) {
        return DB_BALLS.get(id);
    }

    public static Ball getBallByName(String name) {
        return DB_BALLS.values().stream()
                .filter(d -> d.getFileName().equals(name))
                .findAny()
                .orElse(null);
    }

    public static Ball getBallByHash(String hash) {
        return DB_BALLS.values().stream()
                .filter(d -> d.getHash().equals(hash))
                .findAny()
                .orElse(null);
    }

}