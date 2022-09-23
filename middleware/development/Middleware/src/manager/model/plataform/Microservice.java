package manager.model.plataform;

import java.util.Objects;

public class Microservice {

    private final String id;
    private String name;

    private String hostId;
    private String location;

    private long volumeProductionNet;
    private long densityProductionNet;
    private long volumeConsumptionNet;
    private long densityConsumptionNet;

    private long volumeProductionDisk;
    private long densityProductionDisk;
    private long volumeConsumptionDisk;
    private long densityConsumptionDisk;

    public Microservice(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getHostId() {
        return hostId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public long getVolumeProductionNet() {
        return volumeProductionNet;
    }

    public void setVolumeProductionNet(long volumeProductionNet) {
        this.volumeProductionNet = volumeProductionNet;
    }

    public long getDensityProductionNet() {
        return densityProductionNet;
    }

    public void setDensityProductionNet(long densityProductionNet) {
        this.densityProductionNet = densityProductionNet;
    }

    public long getVolumeConsumptionNet() {
        return volumeConsumptionNet;
    }

    public void setVolumeConsumptionNet(long volumeConsumptionNet) {
        this.volumeConsumptionNet = volumeConsumptionNet;
    }

    public long getDensityConsumptionNet() {
        return densityConsumptionNet;
    }

    public void setDensityConsumptionNet(long densityConsumptionNet) {
        this.densityConsumptionNet = densityConsumptionNet;
    }

    public long getVolumeProductionDisk() {
        return volumeProductionDisk;
    }

    public void setVolumeProductionDisk(long volumeProductionDisk) {
        this.volumeProductionDisk = volumeProductionDisk;
    }

    public long getDensityProductionDisk() {
        return densityProductionDisk;
    }

    public void setDensityProductionDisk(long densityProductionDisk) {
        this.densityProductionDisk = densityProductionDisk;
    }

    public long getVolumeConsumptionDisk() {
        return volumeConsumptionDisk;
    }

    public void setVolumeConsumptionDisk(long volumeConsumptionDisk) {
        this.volumeConsumptionDisk = volumeConsumptionDisk;
    }

    public long getDensityConsumptionDisk() {
        return densityConsumptionDisk;
    }

    public void setDensityConsumptionDisk(long densityConsumptionDisk) {
        this.densityConsumptionDisk = densityConsumptionDisk;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Microservice that = (Microservice) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Microservice{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", hostId='" + hostId + '\'' +
                ", location='" + location + '\'' +
                ", volumeProductionNet=" + volumeProductionNet +
                ", densityProductionNet=" + densityProductionNet +
                ", volumeConsumptionNet=" + volumeConsumptionNet +
                ", densityConsumptionNet=" + densityConsumptionNet +
                ", volumeProductionDisk=" + volumeProductionDisk +
                ", densityProductionDisk=" + densityProductionDisk +
                ", volumeConsumptionDisk=" + volumeConsumptionDisk +
                ", densityConsumptionDisk=" + densityConsumptionDisk +
                '}';
    }

}
