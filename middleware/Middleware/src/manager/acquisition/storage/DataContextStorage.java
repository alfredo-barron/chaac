package manager.acquisition.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;
import org.influxdb.dto.Pong;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import config.DefaultConfig;
import manager.acquisition.HostService;
import manager.model.plataform.ContextAttribute;

public class DataContextStorage {

    private static final Logger logger = LoggerFactory.getLogger(HostService.class);
    private final InfluxDB influxDB;
    private final String databaseName;
    private final String policy;

    public DataContextStorage() {
        String serverURL = "http://" + DefaultConfig.DB_URL + ":8086";
        String username = "root";
        String password = "root";
        influxDB = InfluxDBFactory.connect(serverURL, username, password);
        databaseName = "context_database";
        policy = "defaultPolicy";
        this.verifyConnection();
        this.createDB();
    }

    public void verifyConnection() {
        Pong response = influxDB.ping();
        if (response.getVersion().equalsIgnoreCase("unknown")) {
            logger.debug("Error pinging server.");
        } else {
            logger.debug("Connection correct");
        }
    }

    public void createDB() {
        if (!influxDB.databaseExists(databaseName)) {
            influxDB.createDatabase(databaseName);
            influxDB.createRetentionPolicy(policy, databaseName, "7d", 1, true);
            logger.debug("Database created");
        }
    }

    public void putData(List<ContextAttribute> attributeList) {
        BatchPoints batchPoints = BatchPoints.database(databaseName).retentionPolicy(policy).build();
        influxDB.enableBatch(10, 100, TimeUnit.MILLISECONDS);
        attributeList.forEach(contextAttribute -> {
            Point point = Point.measurement(contextAttribute.getName() + "-" + contextAttribute.getContainerId())
                    .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                    .addField("id", contextAttribute.getId())
                    .addField("containerId", contextAttribute.getContainerId())
                    .addField("name", contextAttribute.getName())
                    .addField("productionDisk", contextAttribute.getProductionDisk())
                    .addField("consumptionDisk", contextAttribute.getConsumptionDisk())
                    .addField("productionNet", contextAttribute.getProductionNet())
                    .addField("consumptionNet", contextAttribute.getConsumptionNet())
                    .addField("ufCPU", contextAttribute.getUfCPU())
                    .addField("ufMemory", contextAttribute.getUfMemory())
                    .addField("ufFileSystem", contextAttribute.getUfFileSystem())
                    .addField("ufNet", contextAttribute.getUfNet())
                    .build();
            batchPoints.point(point);
        });
        influxDB.write(batchPoints);
        influxDB.disableBatch();
        influxDB.close();
    }

    public List<ContextAttribute> getData(String measurement) {
        List<ContextAttribute> contextAttributeList = new ArrayList<>();
        Query query = new Query("select * from \"" + measurement + "\"", databaseName);
        QueryResult queryResult = influxDB.query(query);

        for (QueryResult.Result result : queryResult.getResults()) {
            result.getSeries().forEach(series -> series.getValues().forEach(objects -> {
                String id = objects.get(4).toString();
                String containerId = objects.get(3).toString();
                String name = objects.get(5).toString();
                double productionDisk = Double.parseDouble(objects.get(6).toString());
                double consumptionDisk = Double.parseDouble(objects.get(1).toString());
                double productionNet = Double.parseDouble(objects.get(7).toString());
                double consumptionNet = Double.parseDouble(objects.get(2).toString());
                double ufCPU = Double.parseDouble(objects.get(8).toString());
                double ufMemory = Double.parseDouble(objects.get(9).toString());
                double ufFileSystem = Double.parseDouble(objects.get(10).toString());
                double ufNet = Double.parseDouble(objects.get(11).toString());
                String time = objects.get(0).toString();

                contextAttributeList.add(new ContextAttribute(id, containerId, name, (long) productionDisk,
                        (long) consumptionDisk, (long) productionNet, (long) consumptionNet, ufCPU, ufMemory,
                        ufFileSystem, ufNet, time));

            }));
        }
        return contextAttributeList;
    }

}
