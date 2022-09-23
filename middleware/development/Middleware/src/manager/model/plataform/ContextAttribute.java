package manager.model.plataform;

public class ContextAttribute {

    private final String id;
    private final String containerId;
    private final String name;
    private final long productionDisk;
    private final long consumptionDisk;
    private final long productionNet;
    private final long consumptionNet;
    private final double ufCPU;
    private final double ufMemory;
    private final double ufFileSystem;
    private final double ufNet;
    private final String timestamp;

    public ContextAttribute(String id, String containerId, String name, long productionDisk, long consumptionDisk, long productionNet,
                            long consumptionNet, double ufCPU, double ufMemory, double ufFileSystem, double ufNet,
                            String timestamp) {
        this.id = id;
        this.containerId = containerId;
        this.name = name;
        this.productionDisk = productionDisk;
        this.consumptionDisk = consumptionDisk;
        this.productionNet = productionNet;
        this.consumptionNet = consumptionNet;
        this.ufCPU = ufCPU;
        this.ufMemory = ufMemory;
        this.ufFileSystem = ufFileSystem;
        this.ufNet = ufNet;
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public String getContainerId() {
        return containerId;
    }

    public String getName() {
        return name;
    }

    public long getProductionDisk() {
        return productionDisk;
    }

    public long getConsumptionDisk() {
        return consumptionDisk;
    }

    public long getProductionNet() {
        return productionNet;
    }

    public long getConsumptionNet() {
        return consumptionNet;
    }

    public double getUfCPU() {
        return ufCPU;
    }

    public double getUfMemory() {
        return ufMemory;
    }

    public double getUfFileSystem() {
        return ufFileSystem;
    }

    public double getUfNet() {
        return ufNet;
    }

    public String getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "ContextAttribute{" +
                "id='" + id + '\'' +
                ", containerId='" + containerId + '\'' +
                ", name='" + name + '\'' +
                ", productionDisk=" + productionDisk +
                ", consumptionDisk=" + consumptionDisk +
                ", productionNet=" + productionNet +
                ", consumptionNet=" + consumptionNet +
                ", ufCPU=" + ufCPU +
                ", ufMemory=" + ufMemory +
                ", ufFileSystem=" + ufFileSystem +
                ", ufNet=" + ufNet +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }

}
