package manager.model.components;

import java.util.Objects;

public class Location {

    private String nodeId;
    private String nodeUrl;
    private int dataPort;
    private String fileId;
    private String operationId;
    private String urlSharing;

    public Location(String nodeId) {
        this.nodeId = nodeId;
    }

    public Location(String nodeId, String nodeUrl) {
        this.nodeId = nodeId;
        this.nodeUrl = nodeUrl;
    }

    public Location(String nodeId, String nodeUrl, String urlSharing, String fileId) {
        this.nodeId = nodeId;
        this.nodeUrl = nodeUrl;
        this.urlSharing = urlSharing;
        this.fileId = fileId;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getNodeUrl() {
        return nodeUrl;
    }

    public void setNodeUrl(String nodeUrl) {
        this.nodeUrl = nodeUrl;
    }

    public int getDataPort() {
        return dataPort;
    }

    public void setDataPort(int dataPort) {
        this.dataPort = dataPort;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getOperationId() {
        return operationId;
    }

    public void setOperationId(String operationId) {
        this.operationId = operationId;
    }

    public String getUrlSharing() {
        return urlSharing;
    }

    public void setUrlSharing(String urlSharing) {
        this.urlSharing = urlSharing;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return nodeId.equals(location.nodeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nodeId);
    }

    @Override
    public String toString() {
        return "Location{" +
                "nodeId='" + nodeId + '\'' +
                ", nodeUrl='" + nodeUrl + '\'' +
                ", dataPort=" + dataPort +
                ", fileId='" + fileId + '\'' +
                ", operationId='" + operationId + '\'' +
                ", urlSharing='" + urlSharing + '\'' +
                '}';
    }

}

