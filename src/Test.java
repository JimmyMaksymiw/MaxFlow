
import java.util.HashMap;
import java.util.LinkedList;


/**
 * @author Jimmy Maksymiw & Kalle Bornemark
 */
public class Test {
    public static void main(String[] args) {
        HashMap<Node, LinkedList<Edge>> fromNodesWithEdges = new HashMap<>();

        Node a = new Node("a");
        Node b = new Node("b");
        Node c = new Node("c");
        Node d = new Node("d");
        Node e = new Node("e");

        Node f = new Node("f");
        Node g = new Node("g");
        Node h = new Node("h");
        Node i = new Node("i");
        Node j = new Node("j");

        LinkedList<Edge> a1 = new LinkedList<>();
        a1.add(new Edge(a, f));
        a1.add(new Edge(a, g));
        a1.add(new Edge(a, i));

        LinkedList<Edge> b1 = new LinkedList<>();
        b1.add(new Edge(b, f));
        b1.add(new Edge(b, g));
        b1.add(new Edge(b, i));
        b1.add(new Edge(b, j));

        LinkedList<Edge> c1 = new LinkedList<>();
        c1.add(new Edge(c, h));

        LinkedList<Edge> d1 = new LinkedList<>();
        d1.add(new Edge(d, h));

        LinkedList<Edge> e1 = new LinkedList<>();
        e1.add(new Edge(e, g));
        e1.add(new Edge(e, h));
        e1.add(new Edge(e, i));
        e1.add(new Edge(e, j));


        fromNodesWithEdges.put(a, a1);
        fromNodesWithEdges.put(b, b1);
        fromNodesWithEdges.put(c, c1);
        fromNodesWithEdges.put(d, d1);
        fromNodesWithEdges.put(e, e1);

        Graph graph = new Graph(fromNodesWithEdges);
        graph.printGraph();
        graph.maxFlowFordFulkersson();
        graph.printMaximumMatching();
    }
}
