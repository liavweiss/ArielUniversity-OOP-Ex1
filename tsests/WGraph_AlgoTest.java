
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WGraph_AlgoTest {

    @Test
    void copy(){
        weighted_graph g0 = Wgraph();
        weighted_graph_algorithms ag0 = new WGraph_Algo();
        ag0.init(g0);
        weighted_graph g1 = ag0.copy();
        g1.removeNode(0);
        assertFalse(g0.equals(g1));
    }
    @Test
    void isConnected() {
        weighted_graph g0 = Wgraph();
        weighted_graph_algorithms ag0 = new WGraph_Algo();
        ag0.init(g0);
        assertTrue(ag0.isConnected());
        g0.removeEdge(10,9);
        ag0.init(g0);
        assertFalse(ag0.isConnected());
        g0.connect(10,9,3);
        g0.removeNode(7);
        ag0.init(g0);
        assertTrue(ag0.isConnected());
    }

    @Test
    void shortestPathDist() {
        weighted_graph g0 = Wgraph();
        weighted_graph_algorithms ag0 = new WGraph_Algo();
        ag0.init(g0);
        assertTrue(ag0.isConnected());
        double sPhatNumber = ag0.shortestPathDist(0,7);
        Assertions.assertEquals(sPhatNumber, 10.5);
        g0.connect(7,1,7.4);
        double sPhatNumber2 = ag0.shortestPathDist(0,7);
        Assertions.assertEquals(sPhatNumber2,10.4);
    }

    @Test
    void shortestPath() {
        weighted_graph g0 = Wgraph();
        weighted_graph_algorithms ag0 = new WGraph_Algo();
        ag0.init(g0);
        List<node_info> sPath = ag0.shortestPath(0,7);
        List<node_info> sPath1 = new LinkedList<>();
        sPath1.add(g0.getNode(0));
        sPath1.add(g0.getNode(1));
        sPath1.add(g0.getNode(2));
        sPath1.add(g0.getNode(4));
        sPath1.add(g0.getNode(8));
        sPath1.add(g0.getNode(7));
        Iterator<node_info> sPath_It = sPath.iterator();
        Iterator<node_info> sPath1_It = sPath1.iterator();
        while(sPath1_It.hasNext()&&sPath_It.hasNext()) {
              node_info node = sPath_It.next();
              node_info node1 = sPath1_It.next();
                assertEquals(node,node1);
            }
        }


    @Test
    void save_load() {
        weighted_graph g0 = Wgraph();
        weighted_graph_algorithms ag0 = new WGraph_Algo();
        ag0.init(g0);
        String str = "g0.obj";
        ag0.save(str);
        weighted_graph g1 = Wgraph();
        ag0.load(str);
        assertEquals(g0,g1);
        g0.removeNode(0);
        assertNotEquals(g0,g1);
    }


    private weighted_graph Wgraph() {
        weighted_graph g0 = WGraph_DSTest.graphCreator(11,0);
        g0.connect(0,1,3);
        g0.connect(1,2,1.1);
        g0.connect(7,1,7.6);
        g0.connect(2,4,2.2);
        g0.connect(4,5,1);
        g0.connect(4,8,3.3);
        g0.connect(8,7,0.9);
        g0.connect(5,6,4.2);
        g0.connect(6,10,6);
        g0.connect(9,10,3.1);
        g0.connect(9,3,4);

        return g0;
    }
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
