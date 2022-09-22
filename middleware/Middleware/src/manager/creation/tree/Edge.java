package manager.creation.tree;

/**
 * @author Alfredo Barrón Rodríguez
 * @version 1.0
 * @since 2015 - 08 - 03
 */
public class Edge {

    private String label;
    private String source;
    private String target;
    private double weight;

    /**
     * @param label
     * @param source
     * @param target
     * @param weight
     */
    public Edge(String label, String source, String target, double weight) {
        this.label = label;
        this.source = source;
        this.target = target;
        this.weight = weight;
    }

    /**
     * @return
     */
    public String getLabel() {
        return label;
    }

    /**
     * @param label
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * @return
     */
    public String getSource() {
        return source;
    }

    /**
     * @param source
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     * @return
     */
    public String getTarget() {
        return target;
    }

    /**
     * @param target
     */
    public void setTarget(String target) {
        this.target = target;
    }

    /**
     * @return
     */
    public double getWeight() {
        return weight;
    }

    /**
     * @param weight
     */
    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Edge{" +
                "label='" + label + '\'' +
                ", source='" + source + '\'' +
                ", target='" + target + '\'' +
                ", weight=" + weight +
                '}';
    }

}
