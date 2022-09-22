package manager.analysis.ranking;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.OptionalDouble;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import config.Database;
import manager.analysis.clustering.Cluster;
import manager.analysis.clustering.KMeans;
import manager.analysis.clustering.Model;
import manager.analysis.clustering.Point;
import manager.model.plataform.Microservice;
import manager.model.plataform.Zone;

public class Ranking {

    private static final Logger logger = LoggerFactory.getLogger(Ranking.class);
    Quantification quantification = new Quantification();

    public void compute() {
        for (Microservice microservice : Database.getAllMicroservices()) {
            microservice = quantification.getVolume(microservice);
            microservice = quantification.getDensity(microservice);
            logger.debug(microservice.toString());
        }
    }

    public void training() {
        OptionalDouble meanVolumeProductionNet = Database.getAllMicroservices().stream()
                .mapToLong(Microservice::getVolumeProductionNet)
                .average();

        OptionalDouble meanVolumeProductionDisk = Database.getAllMicroservices().stream()
                .mapToLong(Microservice::getVolumeProductionDisk)
                .average();

        OptionalDouble meanVolumeConsumptionNet = Database.getAllMicroservices().stream()
                .mapToLong(Microservice::getVolumeConsumptionNet)
                .average();

        OptionalDouble meanVolumeConsumptionDisk = Database.getAllMicroservices().stream()
                .mapToLong(Microservice::getVolumeConsumptionDisk)
                .average();

        OptionalDouble meanDensityProductionNet = Database.getAllMicroservices().stream()
                .mapToLong(Microservice::getDensityProductionNet)
                .average();

        OptionalDouble meanDensityProductionDisk = Database.getAllMicroservices().stream()
                .mapToLong(Microservice::getDensityProductionDisk)
                .average();

        OptionalDouble meanDensityConsumptionNet = Database.getAllMicroservices().stream()
                .mapToLong(Microservice::getDensityConsumptionDisk)
                .average();

        OptionalDouble meanDensityConsumptionDisk = Database.getAllMicroservices().stream()
                .mapToLong(Microservice::getDensityConsumptionDisk)
                .average();

        System.out.println("Training: " + meanVolumeProductionNet + " | " + meanVolumeProductionDisk + " | " +
                meanVolumeConsumptionNet + " | " + meanVolumeConsumptionDisk + " | " + meanDensityProductionNet +
                " | " + meanDensityProductionDisk + " | " + meanDensityConsumptionNet + " | " + meanDensityConsumptionDisk);
    }

    public HashMap<String, Zone> clustering(int k, Collection<Microservice> microservices) {
        HashMap<String, Zone> zonesHashMap = new HashMap<>();
        List<Point> points = new ArrayList<>();

        microservices.forEach(microservice -> {
            ArrayList<String> data = new ArrayList<>();
            String label = microservice.getId();
            data.add(String.valueOf(microservice.getVolumeProductionNet()));
            data.add(String.valueOf(microservice.getVolumeConsumptionNet()));
            data.add(String.valueOf(microservice.getVolumeProductionDisk()));
            data.add(String.valueOf(microservice.getVolumeConsumptionDisk()));
            data.add(String.valueOf(microservice.getDensityProductionNet()));
            data.add(String.valueOf(microservice.getDensityConsumptionNet()));
            data.add(String.valueOf(microservice.getDensityProductionDisk()));
            data.add(String.valueOf(microservice.getDensityConsumptionDisk()));

            points.add(new Point(data, label));
        });

        KMeans kMeans = new KMeans();
        Model model = kMeans.compute(points, k);
        logger.info("------- Con k=" + k + " ofv=" + model.getOfv() + "-------\n");
        int i = 0;
        for (Cluster cluster : model.getClusters()) {
            i++;
            logger.info("-- Cluster " + i + " --\n");

            List<Microservice> microservicesList = new ArrayList<>();

            for (Point point : cluster.getPoints()) {
                logger.info(point.toString());
                Microservice microservice = Database.getMicroservice(point.getLabel());
                microservicesList.add(microservice);
            }
            String name = "zone" + i;
            Zone zone = new Zone(name, microservicesList);
            zonesHashMap.put(name, zone);
        }
        return zonesHashMap;
    }

}
