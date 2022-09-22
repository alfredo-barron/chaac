package manager.analysis.ranking;

import java.util.List;

import manager.acquisition.storage.DataContextStorage;
import manager.model.plataform.ContextAttribute;
import manager.model.plataform.Microservice;

public class Quantification {

    DataContextStorage dataContextStorage = new DataContextStorage();

    public Microservice getVolume(Microservice microservice) {
        List<ContextAttribute> contextAttributeList = dataContextStorage.getData(microservice.getName() + "-" + microservice.getId());

        long totalProductionNet = contextAttributeList.stream()
                .mapToLong(ContextAttribute::getProductionNet)
                .sum();

        long totalConsumptionNet = contextAttributeList.stream()
                .mapToLong(ContextAttribute::getConsumptionNet)
                .sum();

        long totalProductionDisk = contextAttributeList.stream()
                .mapToLong(ContextAttribute::getProductionDisk)
                .sum();

        long totalConsumptionDisk = contextAttributeList.stream()
                .mapToLong(ContextAttribute::getConsumptionDisk)
                .sum();

        microservice.setVolumeProductionNet(totalProductionNet);
        microservice.setVolumeConsumptionNet(totalConsumptionNet);
        microservice.setVolumeProductionDisk(totalProductionDisk);
        microservice.setVolumeConsumptionDisk(totalConsumptionDisk);

        return microservice;
    }

    public Microservice getDensity(Microservice microservice) {
        List<ContextAttribute> contextAttributeList = dataContextStorage.getData(microservice.getName() + "-" + microservice.getId());

        long totalProductionNet = 0;
        long totalConsumptionNet = 0;
        long totalProductionDisk = 0;
        long totalConsumptionDisk = 0;

        int i = 1;
        while (i < contextAttributeList.size()) {
            String cur = contextAttributeList.get(i).getTimestamp();
            String prev = contextAttributeList.get(i - 1).getTimestamp();
            long interval = Interval.getInterval(cur, prev);

            totalProductionNet += (contextAttributeList.get(i).getProductionNet() / interval);
            totalConsumptionNet += (contextAttributeList.get(i).getConsumptionNet() / interval);
            totalProductionDisk += (contextAttributeList.get(i).getProductionDisk() / interval);
            totalConsumptionDisk += (contextAttributeList.get(i).getConsumptionDisk() / interval);

            i++;
        }

        microservice.setDensityProductionNet(totalProductionNet);
        microservice.setDensityConsumptionNet(totalConsumptionNet);
        microservice.setDensityProductionDisk(totalProductionDisk);
        microservice.setDensityConsumptionDisk(totalConsumptionDisk);

        return microservice;
    }

}
