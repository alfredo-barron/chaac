package manager.analysis.clustering;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Point {

    private final Double[] data;
    private String label;
    private List<Double> maximum;
    private List<Double> minimum;

    public Point(ArrayList<String> strings, String label) {
        super();
        List<Double> points = new ArrayList<>();
        for (String string : strings) {
            points.add(Double.parseDouble(string));
        }
        this.data = points.toArray(new Double[strings.size()]);
        this.label = label;
    }

    public Point(Double[] data) {
        this.data = data;
    }

    public double get(int dimension) {
        return data[dimension];
    }

    public String getLabel() {
        return label;
    }

    public int getGrade() {
        return data.length;
    }

    public int getSize() {
        return maximum.size();
    }

    public List<Double> getMaximum() {
        return maximum;
    }

    public void setMaximum(List<Double> maximum) {
        this.maximum = maximum;
    }

    public List<Double> getMinimum() {
        return minimum;
    }

    public void setMinimum(List<Double> minimum) {
        this.minimum = minimum;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (label != null) {
            sb.append(label);
            sb.append(", ");
        }

        sb.append(data[0]);
        for (int i = 1; i < data.length; i++) {
            sb.append(", ");
            sb.append(data[i]);
        }
        return sb.toString();
    }

    public Double distanceEuclidian(Point destination) {
        double d = 0d;
        for (int i = 0; i < data.length; i++) {
            d += Math.pow(data[i] - destination.get(i), 2);
        }
        return Math.sqrt(d);
    }

    @Override
    public boolean equals(Object obj) {
        Point other = (Point) obj;
        return IntStream.range(0, data.length).noneMatch(i -> data[i] != other.get(i));
    }

}
