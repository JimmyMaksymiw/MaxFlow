import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * @author Jimmy Maksymiw & Kalle Bornemark
 */
public class Graph {
    private HashMap<Node, LinkedList<Edge>> nodesWithEdges;
    private Node source;
    private Node sink;

    /**
     *
     */
    public Graph() {
        this.nodesWithEdges = new HashMap<>();
    }

    /**
     *
     * @param nodesWithEdges
     */
    public Graph(HashMap<Node, LinkedList<Edge>> nodesWithEdges) {
        this.nodesWithEdges = nodesWithEdges;

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
            // Not bipartite
        } else {
            System.out.println("The graph is not bipartite!\n");
        }
    }

    /**
     *
     */
    public void printGraph() {
        System.out.println("-- Printing graph --");
        for (Map.Entry<Node, LinkedList<Edge>> entry : nodesWithEdges.entrySet()) {
            for (Edge edge : entry.getValue()) {
                if (edge.getFromNode().getName() != "source" && edge.getToNode().getName() != "sink") {
                    System.out.println(edge);
                }
            }
        }
        System.out.println("---------------------\n");
    }

    /**
     *
     */
    public void printMaximumMatching(){
        LinkedList<Edge> list = getEdgesWithFlow();
        System.out.println("-- Maximum matching --");
        int totalEdges=0;
        for (Edge edge : list) {
            if (edge.getFromNode().getName() != "source" && edge.getToNode().getName() != "sink") {
                System.out.println(edge);
                totalEdges++;
            }
        }
        System.out.println("Total edges: " + totalEdges);
        System.out.println("---------------------");
    }

    /**
     *
     * @return
     */
    private boolean isBipartite() {
        LinkedList<Node> allNodes = new LinkedList<>();

        // Loopa igenom hashmap
        for (Map.Entry<Node, LinkedList<Edge>> entry : nodesWithEdges.entrySet()) {
            allNodes.add(entry.getKey());
        }

        // J�mf�r nod-values
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
     *
     * @param path
     * @param flow
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
     *
     * @param edge
     */
    private void addEdge(Edge edge) {
        if (nodesWithEdges.containsKey(edge.getFromNode())) {
            nodesWithEdges.get(edge.getFromNode()).add(edge);
        } else {
            LinkedList<Edge> l = new LinkedList<Edge>();
            l.add(edge);
            nodesWithEdges.put(edge.getFromNode(), l);
        }
    }

    /**
     *
     * @return
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
     *
     * @return
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
     *
     * @param source
     * @param sink
     * @return
     */
    public LinkedList<Edge> findAugmentingPath(Node source, Node sink) {
        LinkedList<Node> visitedNodes = new LinkedList<>();
        LinkedList<Edge> edgesInPath = new LinkedList<>();
        findAugmentingPath(source, sink, visitedNodes, edgesInPath);
        return edgesInPath;
    }

    /**
     *
     * @param current
     * @param sink
     * @param visitedNodes
     * @param edgesInPath
     * @return
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
     *
     */
    public void setAllEdgesToZero() {
        for (Map.Entry<Node, LinkedList<Edge>> entry : nodesWithEdges.entrySet()) {
            for (Edge e : entry.getValue()) {
                e.setFlow(0);
            }
        }
    }


    /**
     *
     */
    public void maxFlowFordFulkersson(){
        setAllEdgesToZero();
        Node source = getSource();
        Node sink = getSink();

        Graph residualGraph = getResidualGraph();
        LinkedList<Edge> edgesInAugmentingPath = residualGraph.findAugmentingPath(source, sink);

        int minflow = Integer.MAX_VALUE;
        while (edgesInAugmentingPath.size() > 0) {
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
     *
     * @return
     */
    public HashMap<Node, LinkedList<Edge>> getNodesWithEdges() {
        return nodesWithEdges;
    }

    /**
     *
     * @return
     */
    public Node getSink() {
        return sink;
    }

    /**
     *
     * @return
     */
    public Node getSource() {
        return source;
    }

    /**
     *
     * @param source
     */
    public void setSource(Node source) {
        this.source = source;
    }

    /**
     *
     * @param sink
     */
    public void setSink(Node sink) {
        this.sink = sink;
    }
}
