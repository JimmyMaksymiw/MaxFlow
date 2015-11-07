

/**
 * @author Jimmy Maksymiw & Kalle Bornemark
 */
public class Edge {
    private Node fromNode;
    private Node toNode;
    private int capacity;
    private int flow;

    /**
     *
     * @param startNode
     * @param stopNode
     */
    public Edge(Node startNode, Node stopNode) {
        this.fromNode = startNode;
        this.toNode = stopNode;
        this.capacity = 1;
        this.flow = 0;
    }

    /**
     *
     * @param startNode
     * @param stopNode
     * @param capacity
     */
    public Edge(Node startNode, Node stopNode, int capacity) {
        this.fromNode = startNode;
        this.toNode = stopNode;
        this.capacity = capacity;
        this.flow = 0;
    }

    /**
     *
     * @return
     */
    public Node getFromNode() {
        return fromNode;
    }

    /**
     *
     * @return
     */
    public Node getToNode() {
        return toNode;
    }

    /**
     *
     * @return
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     *
     * @param capacity
     */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    /**
     *
     * @return
     */
    public int getFlow() {
        return flow;
    }

    /**
     *
     * @param flow
     */
    public void setFlow(int flow) {
        this.flow = flow;
    }

    /**
     *
     * @param flow
     */
    public void addFlow(int flow) {
        this.flow += flow;
    }

    /**
     *
     * @return
     */
    public Edge getResidualEdge() {
        return (new Edge(fromNode, toNode, (capacity - flow)));
    }

    /**
     *
     * @return
     */
    public Edge getAntiParallelEdge() {
        return (new Edge(toNode, fromNode, flow));
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "[" + fromNode.getName() + "] > [" + toNode.getName() + "], flow/capacity = " + flow + "/" + capacity;
    }


}
