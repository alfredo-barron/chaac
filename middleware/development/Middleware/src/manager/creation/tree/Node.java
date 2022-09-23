package manager.creation.tree;

/**
 * @author Alfredo Barrón Rodríguez
 * @version 1.0
 * @since 2015 - 08 - 03
 */
public class Node {

    private String id;
    private String label;

    /**
     * @param id
     * @param label
     */
    public Node(String id, String label) {
        this.id = id;
        this.label = label;
    }

    /**
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "Node{" +
                "id='" + id + '\'' +
                ", label='" + label + '\'' +
                '}';
    }

}
