import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Represents a graph with nodes and edges.
 * @author Jimmy Maksymiw & Kalle Bornemark
 */
public class Graph {
    private HashMap<Node, LinkedList<Edge>> nodesWithEdges;
    private Node source;
    private Node sink;

    /**
     * Creates a new empty graph.
     */
    public Graph() {
        this.nodesWithEdges = new HashMap<>();
    }

    /**
     * Creates a new graph based on the provided nodes and edges.
     * Prints the graph, then check whether or not the graph is bipartite.
     * If it is, creates the source and sink nodes and connects them with the nodes in the graph.
     * Then runs the Ford Fulkerson algorithm on the graph, then prints its maximum matching edges.
     * @param nodesWithEdges The graphs nodes that has outgoing edges, sink and source excluded.
     */
    public Graph(HashMap<Node, LinkedList<Edge>> nodesWithEdges) {
        this.nodesWithEdges = nodesWithEdges;
        printGraph();

        // Check if graph is bipartit
        if (isBipartite()) {
            System.out.println("The graph is bipartite!\n");

            source = new Node("source");
            sink = new Node("sink");

            // Connect source node with nodes
            LinkedList<Edge> sourceEdges = new LinkedList<>();
            for (Map.Entry<Node, LinkedList<Edge>> entry : nodesWithEdges.entrySet()) {
                sourceEdges.add(new Edge(source, entry.getKey()));
//                System.out.println("Adding edge from " + source + " to " + entry.getKey());
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
                            edges.add(new Edge(edge.getToNode(), sink));
                            nodesWithEdges.put(edge.getToNode(), edges);
//                            System.out.println("Adding edge from " + edge.getToNode() + " to " + sink);
                        }
                    }
                }
            }

            maxFlowFordFulkersson();
            printMaximumMatching();
            // Not bipartite
        } else {
            System.out.println("The graph is not bipartite!\n");
        }
    }

    /**
     * Prints the content of the graph to the console, source and sink nodes excluded.
     */
    private void printGraph() {
        System.out.println("-- Printing graph --");
        for (Map.Entry<Node, LinkedList<Edge>> entry : nodesWithEdges.entrySet()) {
            for (Edge edge : entry.getValue()) {
                if (!edge.getFromNode().equals(source) && !edge.getToNode().equals(sink)) {
                    System.out.println(edge);
                }
            }
        }
        System.out.println("---------------------\n");
    }

    /**
     * Prints the maximum matching of the graph, source and sink nodes excluded.
     */
    private void printMaximumMatching(){
        LinkedList<Edge> list = getEdgesWithFlow();
        System.out.println("-- Maximum matching --");
        int totalEdges=0;
        for (Edge edge : list) {
            if (!edge.getFromNode().equals(source) && !edge.getToNode().equals(sink)) {
                System.out.println(edge);
                totalEdges++;
            }
        }
        System.out.println("Total edges: " + totalEdges);
        System.out.println("---------------------");
    }

    /**
     * Checks whether or not the graph is bipartite
     * by making sure no toNodes are connected to toNodes and vice versa.
     * @return True if the graph is bipartite, otherwise false.
     */
    private boolean isBipartite() {
        LinkedList<Node> allNodes = new LinkedList<>();

        for (Map.Entry<Node, LinkedList<Edge>> entry : nodesWithEdges.entrySet()) {
            allNodes.add(entry.getKey());
        }

        for (Map.Entry<Node, LinkedList<Edge>> entry : nodesWithEdges.entrySet()) {
            for (Edge e : entry.getValue()) {
                if (allNodes.contains(e.getToNode())) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Adds the provided flow to the path.
     * @param path The path to add flow to.
     * @param flow The flow to be added to the path.
     */
    public void addFlowToPath(LinkedList<Edge> path, int flow) {
        for (Edge edge : path) {
            for (Edge neighbourEdge : nodesWithEdges.get(edge.getFromNode())) {
                if (neighbourEdge.getToNode().equals(edge.getToNode())) {
                    neighbourEdge.addFlow(flow);
                }
            }

            if (nodesWithEdges.get(edge.getToNode()) != null) {
                for (Edge neighbourEdge : nodesWithEdges.get(edge.getToNode())) {
                    if (neighbourEdge.getToNode().equals(edge.getFromNode())) {
                        neighbourEdge.addFlow(-flow);
                    }
                }
            }
        }
    }

    /**
     * Adds the provided edge to its origin node, provided that the node exists.
     * If the node doesn't exists, creates a new entry in nodesWithEdges with the edge's origin node.
     * @param edge The edge to be added.
     */
    private void addEdge(Edge edge) {
        if (nodesWithEdges.containsKey(edge.getFromNode())) {
            nodesWithEdges.get(edge.getFromNode()).add(edge);
        } else {
            LinkedList<Edge> l = new LinkedList<>();
            l.add(edge);
            nodesWithEdges.put(edge.getFromNode(), l);
        }
    }

    /**
     * Creates a new graph, links it with the original graphs' source and sink,
     * and adds the original graphs residual edges to it.
     * @return The graph's residual graph.
     */
    public Graph getResidualGraph() {
        Graph g = new Graph();
        g.setSource(getSource());
        g.setSink(getSink());
        for (Map.Entry<Node, LinkedList<Edge>> entry : nodesWithEdges.entrySet()) {
            for (Edge e : entry.getValue()) {

                if (e.getCapacity() - e.getFlow() != 0)
                    g.addEdge(e.getResidualEdge());
                if (e.getFlow() != 0)
                    g.addEdge(e.getAntiParallelEdge());

            }
        }

        return g;
    }

    /**
     * Returns all edges with a flow greater than 0.
     * @return All edges with a flow greater than 0.
     */
    public LinkedList<Edge> getEdgesWithFlow() {
        LinkedList<Edge> edgeList = new LinkedList<Edge>();
        for (Map.Entry<Node, LinkedList<Edge>> entry : nodesWithEdges.entrySet()) {
            for (Edge e : entry.getValue()) {
                if (e.getFlow() > 0)
                    edgeList.add(e);
            }
        }
        return edgeList;
    }

    /**
     * Find the augmenting path between the provided source and sink nodes.
     * @param source The source node.
     * @param sink The sink node.
     * @return The augmenteing path between tha provided start and sink nodes.
     */
    public LinkedList<Edge> findAugmentingPath(Node source, Node sink) {
        LinkedList<Node> visitedNodes = new LinkedList<>();
        LinkedList<Edge> edgesInPath = new LinkedList<>();
        findAugmentingPath(source, sink, visitedNodes, edgesInPath);
        return edgesInPath;
    }

    /**
     * Find the augmenting path between two nodes by recursively calling itself until it has reached the sink node.
     * @param current The current node in the loop.
     * @param sink The target node.
     * @param visitedNodes A list of visited nodes.
     * @param edgesInPath A list of edges in the augmenting path.
     * @return True if sink is reached, otherwise false.
     */
    private boolean findAugmentingPath(Node current, Node sink, LinkedList<Node> visitedNodes, LinkedList<Edge> edgesInPath) {
        visitedNodes.add(current);
        if (current.equals(sink)) {
            return true;
        } else {
            LinkedList<Edge> neighbourEdges = getNodesWithEdges().get(current);
            if (neighbourEdges != null) {
                for (Edge e : neighbourEdges) {
                    if (!visitedNodes.contains(e.getToNode())) {
                        if (findAugmentingPath(e.getToNode(), sink, visitedNodes, edgesInPath)) {
                            edgesInPath.add(e);
//                            System.out.println("Adding " + e + " to path");
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Sets all the graphs' edges to 0.
     */
    public void setAllEdgesToZero() {
        for (Map.Entry<Node, LinkedList<Edge>> entry : nodesWithEdges.entrySet()) {
            for (Edge e : entry.getValue()) {
                e.setFlow(0);
            }
        }
    }


    /**
     * Runs the Ford Fulkerson algorithm on the graph.
     */
    public void maxFlowFordFulkersson(){
        setAllEdgesToZero();
        Node source = getSource();
        Node sink = getSink();

        Graph residualGraph = getResidualGraph();
        LinkedList<Edge> edgesInAugmentingPath = residualGraph.findAugmentingPath(source, sink);

        int minflow = Integer.MAX_VALUE;

        // Runs while there's an augmenting path
        while (edgesInAugmentingPath.size() > 0) {

            // Find the augmenting path's minimal flow
            for (Edge e : edgesInAugmentingPath) {
                if (e.getCapacity() < minflow) {
                    minflow = e.getCapacity();
                }
            }

            addFlowToPath(edgesInAugmentingPath, minflow);
            residualGraph = getResidualGraph();
            edgesInAugmentingPath = residualGraph.findAugmentingPath(source, sink);
        }
    }

    /**
     * Returns a hashmap with all nodes that has outgoing edges.
     * @return A hashmap with all nodes that has outgoing edges.
     */
    public HashMap<Node, LinkedList<Edge>> getNodesWithEdges() {
        return nodesWithEdges;
    }

    /**
     * Returns the graph's sink node.
     * @return The graph's sink node.
     */
    public Node getSink() {
        return sink;
    }

    /**
     * Returns the graph's source node.
     * @return The graph's source node.
     */
    public Node getSource() {
        return source;
    }

    /**
     * Sets the graph's source node to the provided node.
     * @param source The node to be set as the graph's source node.
     */
    public void setSource(Node source) {
        this.source = source;
    }

    /**
     * Sets the graph's sink node to the provided node.
     * @param sink The node to be set as the graph's sink node.
     */
    public void setSink(Node sink) {
        this.sink = sink;
    }
}
