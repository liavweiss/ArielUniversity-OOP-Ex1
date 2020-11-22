package ex1.src;

import java.io.*;
import java.util.*;


/**
 * This class implements the interface that represents an Undirected (positive) Weighted Graph Theory algorithms including:
 * 0. clone(); (copy)
 * 1. init(graph);
 * 2. isConnected();
 * 3. double shortestPathDist(int src, int dest);
 * 4. List<node_data> shortestPath(int src, int dest);
 * 5. Save(file);
 * 6. Load(file);
 *
 * @author liav.weiss
 */

public class WGraph_Algo implements weighted_graph_algorithms, Serializable {

    /**
     * W_graph - Represents the graph.
     */
    private weighted_graph W_graph;

    /**
     * default constructor.
     */
    public WGraph_Algo() {
        this.W_graph = new WGraph_DS();
    }

    /**
     * Init the graph on which this set of algorithms will operate .
     * @param g - do the algorithm on this graph.
     */
    @Override
    public void init(weighted_graph g) {
        this.W_graph = g;
    }

    /**
     * @return - Return the underlying graph of which this class works.
     */
    @Override
    public weighted_graph getGraph() {
        return this.W_graph;
    }

    /**
     * deep copy constructor.
     * Implemented by a copy function in Node and WGraph_DS classes.
     * @return - the copy graph.
     */
    @Override
    public weighted_graph copy() {
        this.W_graph = new WGraph_DS(this.W_graph);
        return this.W_graph;
    }

    /**
     * Returns true if and only if there is a valid path from every node to each other node.
     * Do it by checking if BFS function return the number of nodes in this graph.
     * (An explanation of BFS is given in the function itself).
     *
     * @return - if it is connected or not.
     */
    @Override
    public boolean isConnected() {
        if (this.W_graph == null) {
            return false;
        }
        if (this.W_graph.nodeSize() == 0 || this.W_graph.nodeSize() == 1) {
            return true;
        }
        return (BFS(this.W_graph) == this.W_graph.nodeSize());
    }

    /**
     * returns the length of the shortest path between src to dest
     * we will do it by first check that the input is correct and then send it to the
     * Dij function, if true we will return the tag of the dest.
     * and initialize the fields with resetNode function.
     * Otherwise we will return -1.
     *
     * @param src  - start node
     * @param dest - end (target) node
     * @return - the number of the path.
     */
    @Override
    public double shortestPathDist(int src, int dest) {

        if (this.W_graph.getNode(src) == null || this.W_graph.getNode(dest) == null) {
            return -1.0;
        }
        WGraph_DS.Node srcCast = (WGraph_DS.Node) this.W_graph.getNode(src);
        WGraph_DS.Node destCast = (WGraph_DS.Node) this.W_graph.getNode(dest);
        if (Dij(this.W_graph.getNode(src), this.W_graph.getNode(dest)) == true) {
            double ans = destCast.getTag();
            resetNodes(this.W_graph);
            return ans;
        }
        return -1;
    }

    /**
     * returns the the shortest path between src to dest - as an ordered List of nodes:
     * src--> n1-->n2-->...dest
     * we will do it by First check that the input is correct and then we will send it to
     * the Dij function, if true we will use the while loop and enter the list according to the field PreviousKey,
     * in the end we will initialize fields with resetNode function and return the path(null if none).
     *
     * @param src  - start node
     * @param dest - end (target) node
     * @return - the list of the path.
     */
    @Override
    public List<node_info> shortestPath(int src, int dest) {
        if (this.W_graph.getNode(src) == null || this.W_graph.getNode(dest) == null) {
            return null;
        }
        List<node_info> thePath1 = new LinkedList<>();
        List<node_info> thePath2 = new LinkedList<>();
        if (Dij(this.W_graph.getNode(src), this.W_graph.getNode(dest)) == true) {
            WGraph_DS.Node srcCast = (WGraph_DS.Node) this.W_graph.getNode(src);
            WGraph_DS.Node destCast = (WGraph_DS.Node) this.W_graph.getNode(dest);
            while (destCast!=null) {
                thePath1.add(((WGraph_DS.Node) this.W_graph.getNode(destCast.getKey())));
                destCast = (WGraph_DS.Node) this.W_graph.getNode(destCast.getPreviousKey());
            }
            for (int i = thePath1.size()-1 ; i >= 0; i--) {
                thePath2.add(thePath1.get(i));
            }
        }
        resetNodes(this.W_graph);
        if (thePath2.size() == 0) {
            return null;
        }
        return thePath2;
    }

    /**
     * Saves this weighted (undirected) graph to the given
     * file name
     *
     * @param file - the file name (may include a relative path).
     * @return true - iff the file was successfully saved
     */
    @Override
    public boolean save(String file) {
        try {
            FileOutputStream fileName = new FileOutputStream(file);
            ObjectOutputStream out = new ObjectOutputStream(fileName);
            out.writeObject(this.W_graph);
            fileName.close();
            out.close();

        } catch (IOException ex) {
            System.out.println("IOException is caught");
            return false;
        }
        System.out.println("Object has been serialized");
        return true;
    }

    /**
     * This method load a graph to this graph algorithm.
     * if the file was successfully loaded - the underlying graph
     * of this class will be changed (to the loaded one), in case the
     * graph was not loaded the original graph should remain "as is".
     *
     * @param file - file name
     * @return true - iff the graph was successfully loaded.
     */
    @Override
    public boolean load(String file) {
        try {
            FileInputStream fileName = new FileInputStream(file);
            ObjectInputStream in = new ObjectInputStream(fileName);
            weighted_graph g = (WGraph_DS) in.readObject();
            this.init(g);
            in.close();
            fileName.close();

        } catch (IOException ex) {
            System.out.println("IOException is caught");
            return false;
        } catch (ClassNotFoundException ex) {
            System.out.println("ClassNotFoundException is caught");
            return false;
        }
        System.out.println("the W_graph has been deserialized ");
        return true;
    }

    /**
     * an equals function.
     * do this with equals function on WGraph_DS class.
     *
     * @param obj
     * @return - if they equals.
     */
    @Override
    public boolean equals(Object obj) {
        WGraph_Algo graph = (WGraph_Algo) obj;
        return this.W_graph.equals(graph.W_graph);
    }

    /**
     * First we get the first vertex of the graph and build a queue of type node_info We will initialize
     * a counter that will tell us how many vertices there are in the graph.
     * We will add the first vertex to the stack and change its meta_data to "black".
     * We will create a while loop, which will run as long as the queue is not empty,
     * and within it we will create a for loop, which will run on the neighbors of each vertex,
     * and if its meta_data is "white" it will change the meta_data to "black" and do counter ++
     * Finally we reset the meta_data and return the counter.
     *
     * @param graph - the graph that doing on him the BFS algorithm.
     * @return run time:O(V+E) v=vertexes , E=edges
     */
    private int BFS(weighted_graph graph) {
        node_info node = this.W_graph.getV().iterator().next();
        WGraph_DS.Node nodeCa = (WGraph_DS.Node) node;
        Queue<node_info> QueueOfVertexes = new LinkedList<node_info>();
        int counter = 1;
        QueueOfVertexes.add(node);
        node.setInfo("black");

        while (!QueueOfVertexes.isEmpty()) {
            node_info temp = QueueOfVertexes.poll();
            WGraph_DS.Node tempCa = (WGraph_DS.Node) temp;
            Collection<node_info> neighbor = ((WGraph_DS.Node) tempCa).getNi();
            for (node_info nodeNext : neighbor) {
                node_info nodeNextCa = (WGraph_DS.Node) nodeNext;
                if (nodeNextCa.getInfo().equals("white")) {
                    QueueOfVertexes.add(nodeNext);
                    nodeNextCa.setInfo("black");
                    counter++;
                }
            }
        }
        resetNodes(graph);
        return counter;
    }


    /**
     * First we will initialize the src tag to be 0, we will add it to the priority queue
     * We will perform a loop as long as the queue is not empty
     * Ask if the key of the current parent == dest.key, if so
     * we will break from the Dij function.
     * Then we will run over neighbors the same vertex that is in the queue
     * And we are asked if his info is equal to "white" and if so we are asked if his badge is bigger than his tag + his father's badge and if so we will change his badge to be:
     * His tag + his father tag.
     * Then we will change the info of the father to "black".
     * And finally we will return false.
     *
     * @param src,dest - the first node and the last node in the path.
     * @return run time:O(V+E) v=vertexes , E=edges
     */
    private boolean Dij(node_info src, node_info dest) {

        PriorityQueue<WGraph_DS.Node> QueueOfVertexes = new PriorityQueue<WGraph_DS.Node>();
        WGraph_DS.Node srcCast = (WGraph_DS.Node) src;
        WGraph_DS.Node destCast = (WGraph_DS.Node) dest;
        srcCast.setTag(0.0);
        QueueOfVertexes.add(srcCast);
        while (!QueueOfVertexes.isEmpty()) {
            node_info father = QueueOfVertexes.poll();
            WGraph_DS.Node fatherCast = (WGraph_DS.Node) father;
            if (fatherCast.getKey() == destCast.getKey()) {
                return true;
            }
            Collection<node_info> neighbor = fatherCast.getNi();
            for (node_info nodeNext : neighbor) {
                WGraph_DS.Node nodeNextCast = (WGraph_DS.Node) nodeNext;

                if (nodeNextCast.getTag() > this.W_graph.getEdge(fatherCast.getKey(), nodeNextCast.getKey()) + fatherCast.getTag()) {
                    nodeNextCast.setTag(fatherCast.getTag() + this.W_graph.getEdge(fatherCast.getKey(), nodeNextCast.getKey()));
                    nodeNextCast.setPreviousKey(fatherCast.getKey());
                }
            if (nodeNextCast.getInfo().equals("white")) {
                QueueOfVertexes.add(nodeNextCast);
            }
        }


        fatherCast.setInfo("black");

    }
        return false;
    }

    /**
     * This function initializes the fields:tag,PreviousKey,Place.
     *
     * @param W_graph - the graph that we wont to reset him to his first mode.
     */
    private void resetNodes(weighted_graph W_graph) {
        for (node_info node : W_graph.getV()) {
            WGraph_DS.Node nodeCast = (WGraph_DS.Node) node;
            nodeCast.setTag(Double.MAX_VALUE);
            nodeCast.setPreviousKey(-1);
            nodeCast.setInfo("white");
        }
    }
}
