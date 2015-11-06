

import java.util.ArrayList;

/**
 * @author Jimmy Maksymiw
 */
public class Graph {
    ArrayList<Node> nodes;
    ArrayList<Edge> edges;

    public Graph(ArrayList<Node> nodes, ArrayList<Edge> edges) {
        this.nodes = nodes;
        this.edges = edges;

        if (!isBipartite()) {
            System.out.println("the graph is not bipartite...");
        }

        for (Edge edge : edges) {
            System.out.println("edge : " + edge.toString());
        }

    }


    private boolean isBipartite() {
        ArrayList<Node> left = new ArrayList<>();
        ArrayList<Node> right = new ArrayList<>();

        for (Edge edge : edges) {
            left.add(edge.getFromNode());
            right.add(edge.getToNode());
        }

        for (Node node : left) {
            if (right.contains(node)) {
                return false;
            }
        }
        return true;
    }
}
