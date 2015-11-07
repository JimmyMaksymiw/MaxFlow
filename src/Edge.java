/**
 * Represents an edge in a graph.
 * @author Jimmy Maksymiw & Kalle Bornemark
 */
public class Edge {
    private Node fromNode;
    private Node toNode;
    private int capacity;
    private int flow;

    /**
     * Creates a new edge between two nodes.
     * @param fromNode The edge's start node.
     * @param toNode The edge's end node.
     */
    public Edge(Node fromNode, Node toNode) {
        this.fromNode = fromNode;
        this.toNode = toNode;
        this.capacity = 1;
        this.flow = 0;
    }

    /**
     * Creates a new weighted edge between two nodes.
     * @param fromNode The edge's start node.
     * @param toNode The edge's end node.
     * @param capacity The edge's capacity.
     */
    public Edge(Node fromNode, Node toNode, int capacity) {
        this.fromNode = fromNode;
        this.toNode = toNode;
        this.capacity = capacity;
        this.flow = 0;
    }

    /**
     * Returns the edge's start node.
     * @return The edge's start node.
     */
    public Node getFromNode() {
        return fromNode;
    }

    /**
     * Returns the edge's end node.
     * @return The edge's end node.
     */
    public Node getToNode() {
        return toNode;
    }

    /**
     * Returns the edge's capacity.
     * @return Thee edge's capacity.
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Sets the edge's capacity to the provided value.
     * @param capacity Value to set the edge's capacity to.
     */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    /**
     * Returns the edge's flow.
     * @return The edge's flow.
     */
    public int getFlow() {
        return flow;
    }

    /**
     * Sets the edge's flow to the provided value.
     * @param flow Value to set the edge's flow to.
     */
    public void setFlow(int flow) {
        this.flow = flow;
    }

    /**
     * Increases the edge's flow by the amount provided.
     * @param flow The amount to increase the edge's flow with.
     */
    public void addFlow(int flow) {
        this.flow += flow;
    }

    /**
     * Returns a new edge based on the original edge's residual flow.
     * @return A new edge based on the original edge's residual flow.
     */
    public Edge getResidualEdge() {
        return (new Edge(fromNode, toNode, (capacity - flow)));
    }

    /**
     * Returns a new edge that's antiparallel to the original edge.
     * @return A new edge that's antiparallel to the original edge.
     */
    public Edge getAntiParallelEdge() {
        return (new Edge(toNode, fromNode, flow));
    }

    /**
     * Returns a formatted presentation of the edge.
     * @return A formatted presentation of the edge.
     */
    @Override
    public String toString() {
        return "[" + fromNode.getName() + "] > [" + toNode.getName() + "], flow/capacity = " + flow + "/" + capacity;
    }


}
