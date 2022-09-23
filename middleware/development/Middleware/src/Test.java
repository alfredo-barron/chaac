import access.Auth;

public class Test {

    public static void main(String[] args) {
        /*
        Host host = new Host("id-01", "http://localhost/", 4, 12);
        List<Microservice> microserviceList = new ArrayList<>();
        Microservice ms1 = new Microservice("id-01", "cadvisor");
        Microservice ms2 = new Microservice("id-02", "cache-0");
        Microservice ms3 = new Microservice("id-03", "cache-1");
        Microservice ms4 = new Microservice("id-04", "origin");
        Microservice ms5 = new Microservice("id-05", "middleware");
        Microservice ms6 = new Microservice("id-06", "sync-0");
        Microservice ms7 = new Microservice("id-07", "proyecto_final_peer2_1");
        microserviceList.add(ms1);
        microserviceList.add(ms2);
        microserviceList.add(ms3);
        microserviceList.add(ms4);
        microserviceList.add(ms5);
        microserviceList.add(ms6);
        microserviceList.add(ms7);

        Database.DB_HOST_MICROSERVICES.put(host, microserviceList);

        Ranking ranking = new Ranking();
        ranking.compute();

        ranking.clustering(4, Database.DB_MICROSERVICES.values());

         */

        
        Auth auth = new Auth();

        String msg = "pool-1";
        //String msg2 = "cache-2";
        String token = auth.createAccessToken(msg);
        System.out.println(token);
        //String token1 = "4ce05bc8869411357992c3118680902a";
        String tokenPool = "27f27e2f7ecbb83128bb0c3e2a7f0967";
        if (auth.authToken(tokenPool, msg)) {
            System.out.println("Es autorizado");
        } else {
            System.out.println("No pasa");
        }

         

        /*
        List<Port> ports = new ArrayList<>();
        ports.add(new Port("80",""));

        DataPool dataPool = new DataPool("pool-0","alfredobarron/datapool:1.5","my-net","pool-0",ports,"TC");
        Database.createDataPool(dataPool);

        Database.getAllDataPool().forEach(System.out::println);
        System.out.println("-----------------------------------");
        System.out.println(Database.getDataPool("pool-0"));
        System.out.println("-----------------------------------");

        DataContainer dataContainer = new DataContainer("cache-0","pool-0","alfredobarron/datacontainer:1.7","my-net","cache-0",80,"CACHE","LOCAL","CLOUD",20,"LFU",40,2);
        Database.createDataContainer(dataContainer);

        Database.getAllDataContainers().forEach(System.out::println);
        System.out.println("-----------------------------------");
        if (Database.existsDataContainer("cache-0")) {
            System.out.println(Database.getDataContainer("cache-0"));
        } else {
            System.out.println("no existe");
        }
        */
    }
}
