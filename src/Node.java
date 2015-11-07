/**
 * Class that represent a node in a graph.
 *
 * @author Jimmy Maksymiw & Kalle Bornemark
 */
public class Node {
    private String name;

    /**
     * Constructor that creates a new node with the provided value.
     * @param name The name of the node.
     */
    public Node(String name) {
        this.name = name;
    }

    /**
     * Returns the name of the node.
     * @return The name of the node.
     */
    public String getName() {
        return name;
    }

    /**
     *  Returns a String with the name of the Node.
     * @return A String with the name.
     */
    @Override
    public String toString() {
        return name;
    }

    /**
     * Checks if the name of the node is equal to the provided object.
     * @param obj
     * @return true if the names are the same otherwise false.
     */
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Node){
            return name.equals(((Node)obj).name);
        }else{
            return super.equals(obj);
        }
    }
}
