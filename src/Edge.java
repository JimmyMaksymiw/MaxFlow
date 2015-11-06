

/**
 * @author Jimmy Maksymiw
 */
public class Edge {
    private Node fromNode;
    private Node toNode;
    private int capacity;
    private int flow;

    public Edge(Node startNode, Node stopNode, int capacity) {
        this.fromNode = startNode;
        this.toNode = stopNode;
        this.capacity = capacity;
        this.flow = 0;
    }

    public Node getFromNode() {
        return fromNode;
    }


    public Node getToNode() {
        return toNode;
    }


    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getFlow() {
        return flow;
    }

    public void setFlow(int flow) {
        this.flow = flow;
    }

    /**
     * Returns the difference between the capacity and the flow.
     * @return The difference between the capacity and the flow.
     */
    public int getCapacityFlowDifference(){
        return capacity - flow;
    }

    /**
     * Returns the residual edge of the current edge.
     * @return The residual edge of the current edge.
     */
    public Edge getResidualEdge(){
        return (new Edge(fromNode, toNode, getCapacityFlowDifference()));
    }

    @Override
    public String toString() {
        return "Edge  >  from = [" + fromNode.getName() + "], to = [" + toNode.getName() + "], capacity = " + capacity + ", flow=" + flow;
    }


}
