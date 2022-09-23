package manager.analysis.clustering;

import java.util.List;

public class Model {

    private String status;
    private final int k;
    private Point point;
    private List<Cluster> clusters;
    private final Double ofv;

    public Model(int k, List<Cluster> clusters, Double ofv) {
        super();
        this.k = k;
        this.ofv = ofv;
        this.clusters = clusters;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public List<Cluster> getClusters() {
        return clusters;
    }

    public void setClusters(List<Cluster> clusters) {
        this.clusters = clusters;
    }

    public Double getOfv() {
        return ofv;
    }

    public int getK() {
        return k;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Model{" +
                "status='" + status + '\'' +
                ", k=" + k +
                ", point=" + point +
                ", clusters=" + clusters +
                ", ofv=" + ofv +
                '}';
    }

}
