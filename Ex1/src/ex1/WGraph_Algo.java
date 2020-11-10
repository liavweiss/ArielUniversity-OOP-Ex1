package ex1;

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
 *
 */

public class WGraph_Algo implements weighted_graph_algorithms{

    /**
     * @object W_graph - Represents the graph.
     */
    private weighted_graph W_graph;

    /**
     * default constructor.
     */
    public WGraph_Algo(){
        this.W_graph=new WGraph_DS();
    }

    /**
     * Init the graph on which this set of algorithms will operate .
     * @param g
     */
    @Override
    public void init(weighted_graph g) {
            this.W_graph=g;
    }

    /**
     * Return the underlying graph of which this class works.
     * @return
     */
    @Override
    public weighted_graph getGraph() {
        return this.W_graph;
    }

    /** deep copy constructor.
     * Implemented by a copy function in Node and WGraph_DS classes.
     * @return
     */
    @Override
    public weighted_graph copy() {
        this.W_graph=new WGraph_DS(this.W_graph);
        return this.W_graph;
    }

    /**
     * Returns true if and only if there is a valid path from every node to each other node.
     * Do it by checking if BFS function return the number of nodes in this graph.
     * (An explanation of BFS is given in the function itself).
     * @return
     */
    @Override
    public boolean isConnected() {
        if(this.W_graph==null){return false;}
        if(this.W_graph.nodeSize()==0||this.W_graph.nodeSize()==1) {return true;}
        return( BFS(this.W_graph) == this.W_graph.nodeSize());
    }

    @Override
    public double shortestPathDist(int src, int dest) {

        if (this.W_graph.getNode(src) == null || this.W_graph.getNode(dest) == null) {
            return -1.0;
        }
        WGraph_DS.Node srcCast = (WGraph_DS.Node)this.W_graph.getNode(src);
        WGraph_DS.Node destCast = (WGraph_DS.Node)this.W_graph.getNode(dest);
        node_info[] fathers = new node_info[this.W_graph.nodeSize()];
        if(BFS(fathers, this.W_graph.getNode(src), this.W_graph.getNode(dest))==true){
            return destCast.getTag();
        }
        return-1;
    }

    @Override
    public List<node_info> shortestPath(int src, int dest) {
        return null;
    }

    /**
     * Saves this weighted (undirected) graph to the given
     * file name
     * @param file - the file name (may include a relative path).
     * @return true - iff the file was successfully saved
     */
    @Override
    public boolean save(String file) {
        return false;
    }

    @Override
    public boolean load(String file) {
        return false;
    }


    /**
     * First we get the first vertex of the graph and build a queue of type node_info We will initialize
     * a counter that will tell us how many vertices there are in the graph.
     * We will add the first vertex to the stack and change its meta_data to "black".
     * We will create a while loop, which will run as long as the queue is not empty,
     * and within it we will create a for loop, which will run on the neighbors of each vertex,
     * and if its meta_data is "white" it will change the meta_data to "black" and do counter ++
     * Finally we reset the meta_data and return the counter.
     * @param graph
     * @return
     * run time:O(V+E) v=vertexes , E=edges
     */
    private int BFS (weighted_graph graph){
        node_info node = this.W_graph.getV().iterator().next();
        WGraph_DS.Node nodeCa = (WGraph_DS.Node) node;
        Queue<node_info> QueueOfVertexes = new LinkedList<node_info>();
        int counter=1;
        QueueOfVertexes.add(node);
        node.setInfo("black");

        while(!QueueOfVertexes.isEmpty()){
            node_info temp = QueueOfVertexes.poll();
            WGraph_DS.Node tempCa = (WGraph_DS.Node) temp;
            Collection<node_info> neighbor = ((WGraph_DS.Node) tempCa).getNi();
            for(node_info nodeNext : neighbor){
                node_info nodeNextCa = (WGraph_DS.Node)nodeNext;
                if(nodeNextCa.getInfo().equals("white")) {
                    QueueOfVertexes.add(nodeNext);
                    nodeNextCa.setInfo("black");
                    counter++;
                }
            }
        }
        //for initialize the meta_data to white again.
        Collection<node_info> vertexes = graph.getV();
        for(node_info vertex : vertexes){
            vertex.setInfo("white");
        }
        return counter;
    }




    private boolean BFS (node_info[] fathers  , node_info src , node_info dest ) {
        boolean[] visit = new boolean[this.W_graph.nodeSize()];
        PriorityQueue<WGraph_DS.Node> QueueOfVertexes = new PriorityQueue<WGraph_DS.Node>();

        WGraph_DS.Node srcCast = (WGraph_DS.Node)src;
        WGraph_DS.Node destCast = (WGraph_DS.Node)dest;
        int counterPlace = 1;
        srcCast.setPlace(0);
        srcCast.setTag(0.0);
        QueueOfVertexes.add(srcCast);
        while (!QueueOfVertexes.isEmpty()) {
            node_info father = QueueOfVertexes.poll();
            WGraph_DS.Node fatherCast = (WGraph_DS.Node)father;
            if(fatherCast.getKey()==destCast.getKey()) {return true;}
            Collection<node_info> neighbor = ((WGraph_DS.Node) father).getNi();
            for(node_info nodeNext : neighbor){
                WGraph_DS.Node nodeNextCast  = (WGraph_DS.Node)nodeNext;
                if(nodeNextCast.getPlace()==-1) {
                    nodeNextCast.setPlace(counterPlace++);
                }
                if(visit[nodeNextCast.getPlace()]==false){
                    fathers[nodeNextCast.getPlace()]=father;
                    QueueOfVertexes.add(nodeNextCast);
                    if(nodeNextCast.getTag()> this.W_graph.getEdge(fatherCast.getKey(),nodeNextCast.getKey())+fatherCast.getTag())
                    nodeNextCast.setTag(fatherCast.getTag()+this.W_graph.getEdge(fatherCast.getKey(),nodeNextCast.getKey()));
                }
            }

        }
        return false;
    }
}
