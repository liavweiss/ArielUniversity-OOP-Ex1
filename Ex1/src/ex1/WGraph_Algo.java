package ex1;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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

    /**
     * returns the length of the shortest path between src to dest
     * we will do it by first check that the input is correct and then send it to the
     * BFS function, if true we will return the tag of the dest.
     * and initialize the fields with resetNode function.
     * Otherwise we will return -1.
     * @param src - start node
     * @param dest - end (target) node
     * @return
     */
    @Override
    public double shortestPathDist(int src, int dest) {

        if (this.W_graph.getNode(src) == null || this.W_graph.getNode(dest) == null) {
            return -1.0;
        }
        WGraph_DS.Node srcCast = (WGraph_DS.Node)this.W_graph.getNode(src);
        WGraph_DS.Node destCast = (WGraph_DS.Node)this.W_graph.getNode(dest);
        if(BFS(this.W_graph.getNode(src), this.W_graph.getNode(dest))==true){
            double ans = destCast.getTag();
            resetNodes(this.W_graph);
            return ans;
        }
        return-1;
    }

    /**
     * returns the the shortest path between src to dest - as an ordered List of nodes:
     * src--> n1-->n2-->...dest
     * we will do it by First check that the input is correct and then we will send it to
     * the BFS function, if true we will use the while loop and enter the list according to the field PreviousKey,
     * in the end we will initialize fields with resetNode function and return the path(null if none).
     * @param src - start node
     * @param dest - end (target) node
     * @return
     */
    @Override
    public List<node_info> shortestPath(int src, int dest) {
        if (this.W_graph.getNode(src) == null || this.W_graph.getNode(dest) == null) {
            return null;
        }
        List<node_info> thePath1 = new LinkedList<>();
        List<node_info> thePath2 = new LinkedList<>();
        if(BFS(this.W_graph.getNode(src),this.W_graph.getNode(dest))==true) {
            WGraph_DS.Node srcCast = (WGraph_DS.Node) this.W_graph.getNode(src);
            WGraph_DS.Node destCast = (WGraph_DS.Node) this.W_graph.getNode(dest);
            while (destCast.getPreviousKey() != srcCast.getPreviousKey()) {
                thePath1.add(((WGraph_DS.Node) this.W_graph.getNode(destCast.getPreviousKey())));
                destCast = (WGraph_DS.Node) this.W_graph.getNode(destCast.getPreviousKey());
            }
            for (int i = thePath1.size()-1; i>=0; i--) {
                thePath2.add(thePath1.get(i));
            }
        }
        resetNodes(this.W_graph);
        if(thePath2.size()==0) {return null;}
        return thePath2;
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
     * an equals function.
     * do this with equals function on WGraph_DS class.
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        WGraph_DS graph = (WGraph_DS)obj;
       return this.equals(obj);
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



    /**
     * First we define a counterplace int that will be responsible for the location of each node in the visit array.
     * we will add to the priority queue the src variable.
     * We will initialize src.tag to be 0 .
     * We will perform a loop while as long as the queue is not empty we will perform:
     * ask if the key of the current father == dest.key , if so
     * we will breake away from the BFS function.
     * after that we will run on the neighbors of the same vertex that is in the queue,  update its place on the visit array (with the counterPlace)
     * We are then asked whether in the Boolean array its value is equal to false and if so we will ask if his tag is greater then his weigt +his father tag and if so we will change his tag to be:his weigt +his father tag.
     * after that we will change to true the place of the father.
     * and finaly we will return false
     * @param  src,dest
     * @return
     * run time:O(V+E) v=vertexes , E=edges
     */
    private boolean BFS ( node_info src , node_info dest ) {
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
                    QueueOfVertexes.add(nodeNextCast);
                    if(nodeNextCast.getTag()> this.W_graph.getEdge(fatherCast.getKey(),nodeNextCast.getKey())+fatherCast.getTag())
                    nodeNextCast.setTag(fatherCast.getTag()+this.W_graph.getEdge(fatherCast.getKey(),nodeNextCast.getKey()));
                    nodeNextCast.setPreviousKey(fatherCast.getKey());
                }
            }
            visit[fatherCast.getPlace()]=true;

        }
        return false;
    }

    /**
     * This function initializes the fields:tag,PreviousKey,Place.
     * @param W_graph
     */
    private void resetNodes(weighted_graph W_graph){
        for(node_info node : W_graph.getV()){
            WGraph_DS.Node nodeCast = (WGraph_DS.Node) node;
            nodeCast.setTag(0.0);
            nodeCast.setPreviousKey(-1);
            nodeCast.setPlace(-1);
        }
    }

    public static void main(String[] args) {
        WGraph_Algo wg = new WGraph_Algo();
        WGraph_DS wg2 = new WGraph_DS();
        wg2.addNode(0);
        wg2.addNode(1);
        wg2.addNode(2);
        wg2.addNode(3);
        wg2.addNode(4);
        wg2.addNode(5);
        wg2.addNode(6);

       wg2.connect(0,1,2);
       // wg2.connect(0,2,1);
        wg2.connect(0,3,2.1);
        wg2.connect(0,5,2);
        wg2.connect(5,6,1.444);
        wg2.connect(6,3,1);
        wg2.connect(4,1,3);




        wg.init(wg2);
        System.out.println(wg.isConnected());
        System.out.println(wg);
        double d = wg.shortestPathDist(0, 6);
        System.out.println(wg.shortestPath(0,6));
        System.out.println(d);
    }
}
