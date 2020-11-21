import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Random;

class WGraph_DSTest {
    private static Random _rnd = null;

    @Test
    void getNode() {
        weighted_graph ga = new WGraph_DS();
        ga.addNode(0);
        Assertions.assertNull(ga.getNode(1));
        Assertions.assertNotNull(0);
    }

    @Test
    void addNode() {
        weighted_graph ga = new WGraph_DS();
        ga.addNode(0);
        ga.addNode(0);
        int node_size = ga.nodeSize();
        Assertions.assertTrue(node_size == 1);
    }

    @Test
    void getEdge() {
        weighted_graph ga = new WGraph_DS();
        ga.addNode(0);
        ga.addNode(1);
        ga.addNode(2);
        ga.connect(0, 1, 3);
        ga.connect(0, 2, 3);
        ga.connect(0, 0, 4);
        double o1 = ga.getEdge(0, 1);
        double o2 = ga.getEdge(1, 2);
        double o3 = ga.getEdge(0, 0);
        Assertions.assertEquals(3, o1);
        Assertions.assertEquals(-1, o2);
        Assertions.assertNotEquals(o3, 4);
    }

    @Test
    void nodeSize() {
        weighted_graph ga = new WGraph_DS();
        ga.addNode(0);
        ga.addNode(1);
        ga.addNode(1);
        ga.removeNode(0);
        ga.removeNode(0);
        ga.removeNode(4);
        int node_size = ga.nodeSize();
        Assertions.assertTrue(node_size == 1);

    }

    @Test
    void edgeSize() {
        weighted_graph ga = new WGraph_DS();
        ga.addNode(0);
        ga.addNode(1);
        ga.addNode(2);
        ga.connect(0, 1, 3);
        ga.connect(0, 2, 3);
        ga.connect(1, 0, 3);
        ga.connect(1, 2, 3);
        ga.removeNode(0);
        int edge_size = ga.edgeSize();
        Assertions.assertEquals(1, edge_size);
        ga.connect(2, 1, 4);
        Assertions.assertEquals(edge_size, 1);

    }

    @Test
    void getV() {
        weighted_graph ga = new WGraph_DS();
        ga.addNode(0);
        ga.addNode(1);
        ga.addNode(2);
        ga.addNode(3);
        ga.addNode(4);
        ga.connect(0, 1, 3);
        ga.connect(0, 2, 3);
        ga.connect(0, 3, 3);
        ga.connect(0, 1, 3);
        ga.connect(4, 1, 3);
        Collection<node_info> vertex = ga.getV();
        for (node_info node : vertex) {
            Collection<node_info> vertexNi = ga.getV(node.getKey());
            for (node_info ni : vertexNi) {
                Assertions.assertNotNull(ni);
            }
        }
    }


    @Test
    public void runTime() {
        int node_size = 1000000;
        int edge_size = node_size ;
        weighted_graph g = graphCreator(node_size, edge_size);
        int key = 1;
        Collection<node_info> ni1 = g.getV(1);
        double weight = g.getEdge(key, ni1.iterator().next().getKey());
        double ex = 0.1;
        Assertions.assertEquals(weight, ex, 0.9);
    }

    @Test
    void hasEdge() {
        weighted_graph ga = new WGraph_DS();
        ga.addNode(0);
        ga.addNode(1);
        ga.addNode(2);
        ga.addNode(4);
        ga.connect(0, 1, 3);
        ga.connect(0, 2, 3);
        ga.connect(0, 3, 3);
        ga.connect(0, 4, 3);
        ga.connect(0,0,3);
        Assertions.assertFalse(ga.hasEdge(1,4));
        Assertions.assertFalse(ga.hasEdge(0,0));
        Assertions.assertTrue(ga.hasEdge(0,4));
    }

    @Test
    void connect() {
        weighted_graph ga = new WGraph_DS();
        ga.addNode(0);
        ga.addNode(1);
        ga.addNode(2);
        ga.addNode(3);
        ga.connect(0, 1, 0);
        ga.connect(0, 2, 3);
        ga.connect(0, 3, 3);
        ga.connect(1, 2, 3);
        ga.connect(1, 0, 4);
        ga.connect(0, 6, 3);
        ga.connect(0, 0, 3);
        double o1 = ga.getEdge(0, 1);
        Assertions.assertFalse(o1 == 3);
        Assertions.assertTrue(ga.hasEdge(0, 1));
    }


    @Test
    void removeNode() {
        weighted_graph ga = new WGraph_DS();
        ga.addNode(0);
        ga.addNode(1);
        ga.addNode(2);
        ga.addNode(3);
        ga.connect(0, 1, 3);
        ga.connect(0, 2, 3);
        ga.connect(0, 3, 3);
        ga.connect(1, 2, 3);
        ga.removeNode(0);
        ga.removeNode(0);
        ga.removeNode(7);
        ga.addNode(0);
        int node_size = ga.nodeSize();
        int edge_size = ga.edgeSize();
        Assertions.assertEquals(4, node_size);
        Assertions.assertEquals(1, edge_size);
    }

    @Test
    void removeEdge() {
        weighted_graph ga = new WGraph_DS();
        ga.addNode(0);
        ga.addNode(1);
        ga.addNode(2);
        ga.addNode(3);
        ga.connect(0, 1, 3);
        ga.connect(0, 2, 3);
        ga.connect(0, 3, 3);
        ga.connect(1, 2, 3);
        ga.removeEdge(0, 1);
        ga.removeEdge(0, 1);
        ga.removeEdge(0, 0);
        ga.removeEdge(0, 6);
        ga.removeEdge(1, 2);
        ga.removeEdge(1, 0);
        double o1 = ga.getEdge(0, 1);
        double o2 = ga.getEdge(1, 2);
        double o3 = ga.getEdge(0, 3);
        Assertions.assertEquals(o1, o2);
        Assertions.assertNotEquals(o1, 03);
    }


    ///////////////////////////////////
    /**
     * Generate a random graph with node_size nodes and edge_size edges
     *
     * @param node_size
     * @param edge_size
     * @return
     */
    public static weighted_graph graphCreator(int node_size, int edge_size) {
        weighted_graph ga = new WGraph_DS();
        for (int i = 0; i < node_size; i++) {
            ga.addNode(i);
        }
        for (int i = 0; i < edge_size; i++) {
            double weight = Math.random();
            int vertex1 = (int) (Math.random() * node_size);
            int vertex2 = (int) (Math.random() * node_size);
            ga.connect(vertex1,vertex2,weight);
        }
        return ga;
    }

}