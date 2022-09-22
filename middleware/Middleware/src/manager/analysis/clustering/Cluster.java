package manager.analysis.clustering;

import java.util.ArrayList;
import java.util.List;

public class Cluster {

    private final List<Point> points = new ArrayList<>();
    private Point centroid;
    private Point interCluster;
    private Point intraCluster;
    private boolean finished = false;

    public Point getCentroid() {
        return centroid;
    }

    public void setCentroid(Point centroid) {
        this.centroid = centroid;
    }

    public Point getInterCluster() {
        return interCluster;
    }

    public void setInterCluster(Point interCluster) {
        this.interCluster = interCluster;
    }

    public Point getIntraCluster() {
        return intraCluster;
    }

    public void setIntraCluster(Point intraCluster) {
        this.intraCluster = intraCluster;
    }

    public List<Point> getPoints() {
        return points;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public void clearPoints() {
        points.clear();
    }

    @Override
    public String toString() {
        return "Cluster{" +
                "points=" + points +
                ", centroid=" + centroid +
                ", interCluster=" + interCluster +
                ", intraCluster=" + intraCluster +
                ", finished=" + finished +
                '}';
    }

}
