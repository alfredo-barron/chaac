package manager.analysis.clustering;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class KMeans {

    public Model compute(List<Point> points, Integer k) {
        List<Cluster> clusters = chooseCentroids(points, k);

        while (!finished(clusters)) {
            prepareClusters(clusters);
            assingPoints(points, clusters);
            recomputeCentroids(clusters);
        }

        Double ofv = computeObjectiveFunction(clusters);
        computeIntraCluster(clusters);
        computeInterCluster(clusters);

        return new Model(k, clusters, ofv);
    }

    /**
     * For each cluster, compute the local statistics (or local model)
     *
     * @param clusters [clusters]
     */
    private void recomputeCentroids(List<Cluster> clusters) {
        for (Cluster c : clusters) {
            if (c.getPoints().isEmpty()) {
                c.setFinished(true);
                continue;
            }

            Double[] d = new Double[c.getPoints().get(0).getGrade()];
            Arrays.fill(d, 0d);
            for (Point p : c.getPoints()) {
                for (int i = 0; i < p.getGrade(); i++) {
                    d[i] += (p.get(i) / c.getPoints().size());
                }
            }

            Point newCentroid = new Point(d);

            if (newCentroid.equals(c.getCentroid())) {
                c.setFinished(true);
            } else {
                c.setCentroid(newCentroid);
            }
        }
    }

    private void computeIntraCluster(List<Cluster> clusters) {
        for (Cluster c : clusters) {
            if (!c.getPoints().isEmpty()) {
                Double[] d = new Double[c.getPoints().get(0).getGrade()];
                Arrays.fill(d, 0d);
                for (Point p : c.getPoints()) {
                    for (int i = 0; i < p.getGrade(); i++) {
                        d[i] += p.get(i);
                    }
                }

                Point intraCluster = new Point(d);

                c.setIntraCluster(intraCluster);
            }
        }
    }

    private void computeInterCluster(List<Cluster> clusters) {
        for (Cluster c : clusters) {

            if (!c.getPoints().isEmpty()) {
                Double[] d = new Double[c.getPoints().get(0).getGrade()];
                Arrays.fill(d, 0d);
                for (Point p : c.getPoints()) {
                    for (int i = 0; i < p.getGrade(); i++) {
                        double distance = 0d;
                        distance += Math.pow(p.get(i) - c.getCentroid().get(i), 2);
                        d[i] += Math.pow(Math.sqrt(distance), 2);
                    }
                }

                Point interCluster = new Point(d);

                c.setInterCluster(interCluster);
            }
        }
    }

    /**
     * For each data object, assigns it to the closest centroid the euclidian distance
     *
     * @param points   Data
     * @param clusters Centroids
     */
    private void assingPoints(List<Point> points, List<Cluster> clusters) {
        for (Point point : points) {
            Cluster closest = clusters.get(0);
            double minimalDistance = Double.MAX_VALUE;

            for (Cluster cluster : clusters) {
                Double distance = point.distanceEuclidian(cluster
                        .getCentroid());
                if (minimalDistance > distance) {
                    minimalDistance = distance;
                    closest = cluster;
                }
            }
            closest.getPoints().add(point);
        }
    }

    /**
     * Clear the clusters
     *
     * @param clusters [clusters]
     */
    private void prepareClusters(List<Cluster> clusters) {
        for (Cluster c : clusters) {
            c.clearPoints();
        }
    }

    /**
     * Calcule function objective
     *
     * @param clusters [clusters]
     * @return [double]
     */
    private Double computeObjectiveFunction(List<Cluster> clusters) {
        Double ofv = 0d;

        for (Cluster cluster : clusters) {
            for (Point point : cluster.getPoints()) {
                ofv += point.distanceEuclidian(cluster.getCentroid());
            }
        }

        return ofv;
    }

    private boolean finished(List<Cluster> clusters) {
        for (Cluster cluster : clusters) {
            if (!cluster.isFinished()) {
                return false;
            }
        }
        return true;
    }

    private List<Cluster> chooseCentroids(List<Point> points, Integer k) {
        List<Cluster> centroids = new ArrayList<>();

        List<Double> maximum = new ArrayList<>();
        List<Double> minimum = new ArrayList<>();
        // me fijo máximo y mínimo de cada dimensión

        for (int i = 0; i < points.get(0).getGrade(); i++) {
            double min = Double.POSITIVE_INFINITY, max = Double.NEGATIVE_INFINITY;

            for (Point point : points) {
                min = min > point.get(i) ? point.get(0) : min;
                max = Math.max(max, point.get(i));
            }

            maximum.add(max);
            minimum.add(min);
        }

        Random random = new Random();

        for (int i = 0; i < k; i++) {
            Double[] data = new Double[points.get(0).getGrade()];
            Arrays.fill(data, 0d);
            for (int d = 0; d < points.get(0).getGrade(); d++) {
                data[d] = random.nextFloat()
                        * (maximum.get(d) - minimum.get(d)) + minimum.get(d);
            }

            Cluster c = new Cluster();
            Point centroid = new Point(data);
            c.setCentroid(centroid);
            centroids.add(c);
        }

        return centroids;
    }

}
