package manager.model.components;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Ball Model
 *
 * @author abarron
 * @version 1
 */
public class Ball {

    private String id;
    private String fileName;
    private String hash;
    private List<Location> locations;
    private String cloudId;
    private long size;
    private double uif;
    private long compressSize;
    private boolean compress;
    private boolean status;
    private String created_at;

    public Ball() {}

    public Ball(String hash) {
        this.hash = hash;
    }

    public Ball(String id, List<Location> locations) {
        this.id = id;
        this.locations = locations;
    }

    public Ball(String id, String fileName, String hash, long size, boolean status) {
        this.id = id;
        this.fileName = fileName;
        this.hash = hash;
        this.size = size;
        this.status = status;
    }

    public Ball(String id, String fileName, String hash, long size, double uif, boolean status) {
        this.id = id;
        this.hash = hash;
        this.fileName = fileName;
        this.size = size;
        this.uif = uif;
        this.status = status;
        this.created_at = new Timestamp(System.currentTimeMillis()).toString();
    }

    public Ball(String id, String fileName, String hash, List<Location> locations, long size, double uif,
                    boolean compress, boolean status) {
        this.id = id;
        this.hash = hash;
        this.fileName = fileName;
        this.locations = locations;
        this.size = size;
        this.uif = uif;
        this.compress = compress;
        this.status = status;
        this.created_at = new Timestamp(System.currentTimeMillis()).toString();
    }

    public Ball(String fileName, String hash, List<Location> locations, long size, double uif, boolean compress, boolean status) {
        this.id = UUID.randomUUID().toString();
        this.fileName = fileName;
        this.hash = hash;
        this.locations = locations;
        this.size = size;
        this.uif = uif;
        this.compress = compress;
        this.status = status;
        this.created_at = new Timestamp(System.currentTimeMillis()).toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

    public String getCloudId() {
        return cloudId;
    }

    public void setCloudId(String cloudId) {
        this.cloudId = cloudId;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public double getUif() {
        return uif;
    }

    public void setUif(double uif) {
        this.uif = uif;
    }

    public boolean isCompress() {
        return compress;
    }

    public void setCompress(boolean compress) {
        this.compress = compress;
    }

    public long getCompressSize() {
        return compressSize;
    }

    public void setCompressSize(long compressSize) {
        this.compressSize = compressSize;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ball that = (Ball) o;
        return hash.equals(that.hash);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hash);
    }

    @Override
    public String toString() {
        return "Ball{" +
                "id='" + id + '\'' +
                ", fileName='" + fileName + '\'' +
                ", hash='" + hash + '\'' +
                ", locations=" + locations +
                ", cloudId='" + cloudId + '\'' +
                ", size=" + size +
                ", uif=" + uif +
                ", compressSize=" + compressSize +
                ", compress=" + compress +
                ", status=" + status +
                ", created_at='" + created_at + '\'' +
                '}';
    }
}
