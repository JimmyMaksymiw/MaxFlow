import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * @author Jimmy Maksymiw
 */
public class Graph {
    private HashMap<Node, LinkedList<Edge>> nodesWithEdges;
    private Node source;
    private Node sink;

    public Graph() {
        this.nodesWithEdges = new HashMap<>();
    }

    public Graph(HashMap<Node, LinkedList<Edge>> nodesWithEdges) {
        this.nodesWithEdges = nodesWithEdges;

        // Check if graph is bipartit
        if (isBipartite()) {
            System.out.println("The graph is bipartite");
            source = new Node("source");
            sink = new Node("sink");

            // Connect source node with nodes
            LinkedList<Edge> sourceEdges = new LinkedList<>();
            for (Map.Entry<Node, LinkedList<Edge>> entry : nodesWithEdges.entrySet()) {
                sourceEdges.add(new Edge(source, entry.getKey(), 1));
                System.out.println("Adding edge from " + source + " to " + entry.getKey());
            }
            nodesWithEdges.put(source, sourceEdges);

            // Connect sink node with nodes
            HashMap<Node, LinkedList<Edge>> temp = new HashMap<>(nodesWithEdges);
            LinkedList<Edge> edges;
            for (Map.Entry<Node, LinkedList<Edge>> entry : temp.entrySet()) {
                LinkedList<Edge> nodeEdges = entry.getValue();
                if (entry.getKey() != source) {
                    for (Edge edge : nodeEdges) {
                        edges = new LinkedList<>();
                        if (!nodesWithEdges.containsKey(edge.getToNode())) {
                            edges.add(new Edge(edge.getToNode(), sink, 1));
                            nodesWithEdges.put(edge.getToNode(), edges);;
                            System.out.println("Adding edge from " + edge.getToNode() + " to " + sink);
                        }
                    }
                }
            }

        // Not bipartite
        } else {
            System.out.println("The graph is not bipartite");
        }
    }

    public void printGraph() {
        System.out.println("-- Printing graph --");
        for (Map.Entry<Node, LinkedList<Edge>> entry : nodesWithEdges.entrySet()) {
            for (Edge edges : entry.getValue()) {
                System.out.println(edges);
            }
        }
        System.out.println("---------------------");
    }

    private boolean isBipartite() {
        LinkedList<Node> allNodes = new LinkedList<>();

        // Loopa igenom hashmap
        for (Map.Entry<Node, LinkedList<Edge>> entry : nodesWithEdges.entrySet()) {
            allNodes.add(entry.getKey());
        }

        // Jämför nod-values
        for (Map.Entry<Node, LinkedList<Edge>> entry : nodesWithEdges.entrySet()) {
            for (Edge e : entry.getValue()) {
                if (allNodes.contains(e.getToNode())) {
                    return false;
                }
            }
        }

        return true;
    }

/*    public Graph getResidualGraph() {
        Graph g = new Graph();

        for (Map.Entry<Node, LinkedList<Edge>> entry : adjacencySet) {
            LinkedList<Edge> adjacencyEdges = new LinkedList<Edge>();

            for (Edge e : entry.getValue()) {
                if (e.getCapacityFlowDifference() != 0)
                    g.addEdge(e.getResidualEdge());
                if (e.getFlow() != 0)
                    g.addEdge(e.getAntiParallelEdge());
            }
        }
        g.generateAdjacencySet();

        return g;
    }*/

    public HashMap<Node, LinkedList<Edge>> getNodesWithEdges() {
        return nodesWithEdges;
    }

    public Node getSink() {
        return sink;
    }

    public Node getSource() {
        return source;
    }
}
