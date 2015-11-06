import java.util.LinkedList;

/**
 * @author Jimmy Maksymiw
 */
public class MaxFlowFordFulkerson {

    private Graph graph;

    public MaxFlowFordFulkerson (Graph graph){
        this.graph = graph;
        Node source = graph.getSource();
        Node sink = graph.getSink();
        LinkedList<Edge> edgesInAugmentingPath = findAugmentingPath(source, sink);

        while (edgesInAugmentingPath.size() > 0) {
            for (Edge edge : edgesInAugmentingPath) {
                edge.setFlow(1);
            }

        }
    }

    public LinkedList<Edge> findAugmentingPath(Node source, Node sink) {
        LinkedList<Node> visitedNodes = new LinkedList<>();
        LinkedList<Edge> edgesInPath = new LinkedList<>();
        findAugmentingPath(source, sink, visitedNodes, edgesInPath);
        return edgesInPath;
    }

    private boolean findAugmentingPath(Node current, Node sink, LinkedList<Node> visitedNodes, LinkedList<Edge> edgesInPath) {
        visitedNodes.add(current);
        if (current.equals(sink)) {
            return true;
        } else {
            LinkedList<Edge> neighbourEdges = graph.getNodesWithEdges().get(current);
            if (neighbourEdges != null) {
                for (Edge e : neighbourEdges) {
                    if (!visitedNodes.contains(e.getToNode())) {
                        if (findAugmentingPath(e.getToNode(), sink, visitedNodes, edgesInPath)) {
                            edgesInPath.add(e);
                            System.out.println("Adding " + e + " to path");
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
