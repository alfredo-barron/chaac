package manager.creation.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * Write data to Analysis and Results
 *
 * @author abarron
 * @version 1
 */
public class Graph {

    private List<Node> nodes;
    private List<Edge> edges;

    public Graph() {
        this.nodes = new ArrayList<>();
        this.edges = new ArrayList<>();
    }

    /**
     * @param id
     * @param label
     */
    public void addNode(String id, String label) {
        nodes.add(new Node(id, label));
    }

    /**
     * @param label
     * @param source
     * @param target
     * @param weight
     */
    public void addEdge(String label, String source, String target, double weight) {
        edges.add(new Edge(label, source, target, weight));
    }

    /**
     *
     */
    public void viewNodes() {
        for (Node node : nodes) {
            System.out.println(node.getId() + " " + node.getLabel());
        }
    }

    /**
     *
     */
    public void viewEgdes() {
        for (Edge edge : edges) {
            System.out.println(edge.getLabel() + " " + edge.getSource() + " " +
                    edge.getTarget() + " " + edge.getWeight());
        }
    }

}
