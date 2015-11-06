

import java.util.ArrayList;

/**
 * @author Jimmy Maksymiw
 */
public class Test {
    public static void main(String[] args) {
        Node a = new Node("a");
        Node b = new Node("b");
        Node c = new Node("c");

        Node d = new Node("d");
        Node e = new Node("e");
        Node f = new Node("f");

        ArrayList<Node> nodes = new ArrayList<>();
        nodes.add(a);
        nodes.add(b);
        nodes.add(c);
        nodes.add(d);
        nodes.add(e);
        nodes.add(f);


        Edge a1 = new Edge(a, d, 3);
        Edge a2 = new Edge(a, f, 4);

        Edge b1 = new Edge(b, d, 6);
        Edge b2 = new Edge(b, e, 4);

        Edge c1 = new Edge(c, f, 1);
        Edge c2 = new Edge(c, e, 5);

        ArrayList<Edge> edges = new ArrayList<>();
        edges.add(a1);
        edges.add(a2);
        edges.add(b1);
        edges.add(b2);
        edges.add(c1);
        edges.add(c2);

        Graph graph = new Graph(nodes, edges);

//        MaxFlowFordFulkerson maxFlowFordFulkerson = new MaxFlowFordFulkerson (graph, source , sink);



    }
}
