import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;



/**
 * This class implements the interface that represents an undirectional weighted graph.
 * It support a large number of nodes (over 10^6, with average degree of 10).
 *
 * @author liav.weiss
 */
public class WGraph_DS implements weighted_graph, Serializable {


    /**
     * graph - Represents the list of node_info (the vertexes).
     * node_size - Represents the number of nodes in the graph.
     * edge_size - Represents the number of edges in the graph.
     * mc - Represents the number of changes in the graph.
     */
    private HashMap<Integer, node_info> W_graph;
    private int node_size;
    private int edge_size;
    private int mc;


    /**
     * This class implements the interface that represents the set of operations applicable on a
     * node (vertex) in an weighted graph.
     *
     * @author liav.weiss
     */
    public class Node implements node_info, Comparable<Node>,Serializable{


        /**
         * key - Represents the serial number of each node.
         * neighbor - Represents the list of neighbors of each node.
         * weight - Represents the list of edge's weight of each node with his neighbor.
         * previous - Represents the key of the previous node in the shortest path(help us to to create the path).
         * meta_data - Represents the information of each node.
         * tag - Represents the tag of each node.
         */
        private int key;
        private HashMap<Integer, node_info> neighbor;
        private HashMap<Integer, Double> weight;
        private int previousKey;
        private String meta_data;
        private double tag;


        /**
         * a default constructor.
         *
         * @param key
         */
        public Node(int key) {
            this.key = key;
            this.setTag(Double.MAX_VALUE);
            this.setInfo("white");
            this.weight = new HashMap<>();
            this.neighbor = new HashMap<>();
            this.previousKey = -1;
        }

        /**
         * deep copy constructor
         *
         * @param node
         */
        public Node(node_info node) {
            this.key = node.getKey();
            this.neighbor = new HashMap<Integer, node_info>();
            this.weight = new HashMap<Integer, Double>();
            this.meta_data = new String(node.getInfo());
            this.tag = node.getTag();
            Node n = (Node) node;
            this.previousKey = n.getPreviousKey();
        }

        /**
         * Return the key (the serial number of this node).
         *
         * @return
         */
        @Override
        public int getKey() {
            return this.key;
        }

        /**
         * return the remark (meta data) associated with this node.
         *
         * @return
         */
        @Override
        public String getInfo() {
            return this.meta_data;
        }

        /**
         * A function that allows changing the remark (meta data) associated with this node.
         *
         * @param s
         */
        @Override
        public void setInfo(String s) {
            this.meta_data = s;
        }

        /**
         * Temporary data on each node.
         *
         * @return
         */
        @Override
        public double getTag() {
            return this.tag;
        }

        /**
         * A function that allows you to temporarily mark each node.
         *
         * @param t - the new value of the tag
         */
        @Override
        public void setTag(double t) {
            this.tag = t;
        }

        /**
         * this function compare two nodes only by their tag.
         *
         * @param other
         * @return
         */
        @Override
        public int compareTo(Node other) {
            return Double.compare(this.getTag(), other.getTag());
        }

        /**
         * get the key of the previous node(its for the shortest path function)
         *
         * @return
         */
        public int getPreviousKey() {
            return this.previousKey;
        }

        /**
         * A function that allows you to change the key of the previous node(its for the shortest path function).
         *
         * @param key
         */
        public void setPreviousKey(int key) {
            this.previousKey = key;
        }

        /**
         * This method returns a collection with all the Neighbor nodes of this node_info.
         */
        public Collection<node_info> getNi() {
            return neighbor.values();
        }

        /**
         * return true if key is on the list of neighbors of this node.
         *
         * @param key
         * @return
         */
        public boolean hasNi(int key) {
            return this.neighbor.containsKey(key);
        }

        /**
         * This method adds the node_info (t) to this node_info neighbors.
         * checking if this node==@param t and if not adds it to his neighbors list and add the weight to his weight list.
         */
        public void addNi(node_info t, double weight) {
            if (this.key == t.getKey()) {
                return;
            }
            this.neighbor.put(t.getKey(), t);
            this.weight.put(t.getKey(), weight);
        }

        /**
         * Removes the edge this-key,
         * checking if this node exist in the neighbor list and remove from the list.
         *
         * @param node
         */
        public void removeNode(node_info node) {
            if (!this.neighbor.containsKey(node.getKey())) {
                return;
            }
            this.neighbor.remove(node.getKey());
            this.weight.remove(node.getKey());
        }

        /**
         * an equals function.
         * do this with three Auxiliary functions.
         *
         * @param obj
         * @return
         */
        @Override
        public boolean equals(Object obj) {
            node_info node = (node_info) obj;
            Node nodeCast = (Node) node;
            return (this.equal1(node) && this.equalNeighbor(nodeCast.neighbor) && this.equalWeight(nodeCast.weight));
        }

        /**
         * this function checks if all the regular fields are the same.
         *
         * @param node
         * @return
         */
        private boolean equal1(node_info node) {
            Node nodeCast = (Node) node;
            return (this.getKey() == nodeCast.getKey() && this.getTag() == nodeCast.getTag() && this.getInfo().equals(nodeCast.getInfo()));
        }

        /**
         * this function checks if two hashMap of Integer and node_data are the same.
         * run on two iterators and asked if all the fields are equals.
         *
         * @param nodeNeighbor
         * @return
         */
        private boolean equalNeighbor(HashMap<Integer, node_info> nodeNeighbor) {
            boolean flag = false;
            Collection<node_info> this_Neighbor = this.neighbor.values();
            Collection<node_info> node_Neighbor = nodeNeighbor.values();
            Iterator<node_info> this_Neighbor_Ite = this_Neighbor.iterator();
            while (this_Neighbor_Ite.hasNext()) {
                Node nodeCast1 = (Node) this_Neighbor_Ite.next();
                for (node_info nodeNi : node_Neighbor) {
                    if (nodeCast1.equal1(nodeNi)) {
                        flag = true;
                    }
                }
                if(flag==false) {return false;}
            }
            return true;
        }

        /**
         * this function checks if two hashMap of Integer and Double are the same.
         * run on two iterators and asked if all the fields are equals.
         *
         * @param nodeWeight
         * @return
         */
        private boolean equalWeight(HashMap<Integer, Double> nodeWeight) {

            Collection<Double> node1Weight = this.weight.values();
            Collection<Double> node2Weight = nodeWeight.values();
            Iterator<Double> node1Weight_Ite = node1Weight.iterator();
            Iterator<Double> node2Weight_Ite = node2Weight.iterator();
            while (node1Weight_Ite.hasNext() && node2Weight_Ite.hasNext()) {
                double weight1 = node1Weight_Ite.next();
                double weight2 = node2Weight_Ite.next();
                if (weight1 != weight2) {
                    return false;
                }
            }
            return true;
        }

        /**
         * toString function that string each node without his neighbor.
         *
         * @return
         */
        @Override
        public String toString() {
            String s = "";
            s += "NodeData{" +
                    "key=" + this.key +
                    ", meta_data='" + this.meta_data + '\'' +
                    ", tag=" + this.tag;
            s += '}';
            return s;
        }


    }

    /**
     * default constructor.
     */
    public WGraph_DS() {
        this.W_graph = new HashMap<Integer, node_info>();
        this.node_size = 0;
        this.edge_size = 0;
    }

    /**
     * a deep copy constructor.
     *
     * @param graph
     */
    public WGraph_DS(weighted_graph graph) {
        this.W_graph = new HashMap<Integer, node_info>();

        for (node_info node : graph.getV()) {
            node_info n1 = (Node) node;
            this.W_graph.put(node.getKey(), new Node(n1));
        }
        Collection<node_info> theVertex = graph.getV();
        for (node_info node : theVertex) {
            node_info n1 = (Node) node;
            Collection<node_info> theNeighbor = graph.getV(n1.getKey());
            for (node_info neighbor : theNeighbor) {
                Node neighbor1 = (Node) neighbor;
                connect(n1.getKey(), neighbor1.getKey(), graph.getEdge(n1.getKey(), neighbor1.getKey()));
            }
        }
        this.node_size = graph.nodeSize();
        this.edge_size = graph.edgeSize();
    }


    /**
     * return the node_info by the node_id,
     *
     * @param key - the node_id
     * @return the node_data by the node_id, null if none.
     */
    @Override
    public node_info getNode(int key) {
        if (W_graph.containsKey(key)) {
            return W_graph.get(key);
        }
        return null;
    }

    /**
     * return true if and only if there is an edge between node1 and node2.
     *
     * @param node1
     * @param node2
     * @return run time:O(1).
     */
    @Override
    public boolean hasEdge(int node1, int node2) {
        if (node1 == node2) {
            return false;
        }
        Node n1 = (Node) getNode(node1);
        return n1.hasNi(node2);
    }

    /**
     * return the weight if the edge (node1, node1) exist. In case
     * there is no such edge return -1.
     *
     * @param node1
     * @param node2
     * @return run time:O(1).
     */
    @Override
    public double getEdge(int node1, int node2) {
        if (hasEdge(node1, node2)) {
            Node n1 = (Node) getNode(node1);
            return n1.weight.get(node2);
        }
        return -1;
    }

    /**
     * add a new node to the graph with the given key.
     * if there is already a node with such a key -> no action should be performed.
     *
     * @param key run time:O(1).
     */
    @Override
    public void addNode(int key) {
        if (W_graph.containsKey(key)) {
            return;
        }
        Node n1 = (Node) new Node(key);
        this.W_graph.put(key, n1);
        node_size++;
        mc++;
    }

    /**
     * Connect an edge between node1 and node2, with an edge with weight >=0.
     * if the edge node1-node2 already exists - the method simply updates the weight of the edge.
     * run time_O(1).
     */
    @Override
    public void connect(int node1, int node2, double w) {
        if (w < 0) {
            throw new RuntimeException("the weight must be a positive number");
        }
        Node n1 = (Node) getNode(node1);
        Node n2 = (Node) getNode(node2);
        if (n1 == null || n2 == null) {
            return;
        } else if (node1 != node2 && hasEdge(node1, node2) && getEdge(node1, node2) != w) {
            n1.weight.put(node2, w);
            n2.weight.put(node1, w);
            mc++;
        } else if (!hasEdge(node1, node2) && node1 != node2) {
            n1.addNi(n2, w);
            n2.addNi(n1, w);
            mc++;
            edge_size++;
        }
    }


    /**
     * This method return a pointer (shallow copy) for a
     * Collection representing all the nodes in the graph.
     *
     * @return Collection<node_data>
     * run time:O(1).
     */
    @Override
    public Collection<node_info> getV() {
        return this.W_graph.values();
    }

    /**
     * This method returns a Collection containing all the
     * nodes connected to node_id
     *
     * @return Collection<node_data>
     * run time: O(1).
     */
    @Override
    public Collection<node_info> getV(int node_id) {
        Node n1 = (Node) getNode(node_id);
        return n1.getNi();
    }

    /**
     * Delete the node (with the given ID) from the graph -
     * and removes all edges which starts or ends at this node.
     * do this by for each loop that acts on this node and removes the edges between its neighbors.
     *
     * @param key run time: O(n), |V|=n, as all the edges should be removed.
     * @return the data of the removed node (null if none).
     */
    @Override
    public node_info removeNode(int key) {
        if (!this.W_graph.containsKey(key)) {
            return null;
        }
        node_info n1 = getNode(key);
        Node N1 = (Node) getNode(key);
        if (!N1.getNi().isEmpty()) {
            for (node_info n : N1.getNi()) {
                Node N2 = (Node) n;
                N2.removeNode(N1);
                this.edge_size--;
                this.mc++;
            }
        }
        W_graph.remove(key);
        this.mc++;
        this.node_size--;
        return n1;
    }

    /**
     * Delete the edge between two nodes.
     *
     * @param node1
     * @param node2 run time:O(1).
     */
    @Override
    public void removeEdge(int node1, int node2) {
        if (!hasEdge(node1, node2)) {
            return;
        }
        Node n1 = (Node) getNode(node1);
        Node n2 = (Node) getNode(node2);
        n1.removeNode(n2);
        n2.removeNode(n1);

        this.edge_size--;
        this.mc++;
    }

    /**
     * return the number of vertices (nodes) in the graph.
     *
     * @return run time:O(1).
     */
    @Override
    public int nodeSize() {
        return this.node_size;
    }

    /**
     * return the number of edges (undirectional graph).
     *
     * @return run time:O(1).
     */
    @Override
    public int edgeSize() {
        return this.edge_size;
    }

    /**
     * return the Mode Count - for testing changes in the graph.
     *
     * @return
     */
    @Override
    public int getMC() {
        return this.mc;
    }

    /**
     * an equals function , do this with two iterator that run on the vertexes of the graph
     * and inside asked if its the same node in both graph with
     * Node equals function.
     *
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        weighted_graph graph = (weighted_graph) obj;
        if (this.node_size != graph.nodeSize() || this.edge_size != graph.edgeSize()) {
            return false;
        }
        Collection<node_info> this_W_graph = this.getV();
        Collection<node_info> graph_W_graph = graph.getV();
        Iterator<node_info> this_W_graph_Ite = this_W_graph.iterator();
        Iterator<node_info> graph_W_graph_Ite = graph_W_graph.iterator();
        while (this_W_graph_Ite.hasNext() && graph_W_graph_Ite.hasNext()) {
            Node vertex1 = (Node) this_W_graph_Ite.next();
            Node vertex2 = (Node) graph_W_graph_Ite.next();
            if (!vertex1.equals(vertex2)) {
                return false;
            }
        }

        return true;
    }

    /**
     * toString function that string each node with his neighbor.
     *
     * @return
     */
    @Override
    public String toString() {
        String s = "";
        for (node_info n : getV()) {
            int counter = 1;
            Node n1 = (Node) n;
            s += "NodeData{" +
                    "key=" + n1.key +
                    ", meta_data='" + n1.meta_data +
                    ", tag=" + n1.tag;
            for (node_info neighbor : n1.getNi())
                s += "{ neighbor" + counter++
                        + ":key=" + neighbor.getKey() +
                        ", meta_data='" + neighbor.getInfo() +
                        ", tag=" + neighbor.getTag() +
                        ", weight:" + getEdge(n1.getKey(), neighbor.getKey());
            s += '}';
        }
        return s;
    }
}
