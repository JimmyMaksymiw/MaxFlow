/**
 * @author Jimmy Maksymiw & Kalle Bornemark
 */
public class Node {
    private String name;

    /**
     *
     * @param name
     */
    public Node(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return name;
    }

    /**
     *
     * @param obj
     * @return
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
