

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;


/**
 * @author Jimmy Maksymiw
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

        LinkedList<Edge> a1 = new LinkedList<>();
        a1.add(new Edge(a, d, 3));
        a1.add(new Edge(a, f, 4));

        LinkedList<Edge> b1 = new LinkedList<>();
        b1.add(new Edge(b, d, 6));
        b1.add(new Edge(b, e, 4));

        LinkedList<Edge> c1 = new LinkedList<>();
        c1.add(new Edge(c, f, 1));
        c1.add(new Edge(c, e, 5));

        fromNodesWithEdges.put(a, a1);
        fromNodesWithEdges.put(b, b1);
        fromNodesWithEdges.put(c, c1);

        Graph graph = new Graph(fromNodesWithEdges);
        new MaxFlowFordFulkerson(graph);


//        graph.printGraph();

//        MaxFlowFordFulkerson maxFlowFordFulkerson = new MaxFlowFordFulkerson (graph, source , sink);



    }
}
