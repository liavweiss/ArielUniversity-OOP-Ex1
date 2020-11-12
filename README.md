# Ex1
-------------------------------------------------------
My project deals with undirectional weighted graph, in this project there are two classes and one internal class that apply an weighted graph. I would like to explain about each class written in this project.

### The internal class: Node:
This class implements the interface that represents the set of operations applicable on a node (vertex) in an weighted graph.

In this class we have seven feilds:
1) key - Represents the serial number of each node.
2) neighbor - Represents the list of neighbors of each node.
3) weight - Represents the list of edge's weight of each node with his neighbor.
4) previous - Represents the key of the previous node in the shortest path(help us to to create the path).
5) meta_data - Represents the information of each node.
6) tag - Represents the tag of each node.
7) laceArray - Represents the place of visit array in the shortest path function.

In this class we have two constructors and sixteen functions.
#### constructors:
* defoult- `Node()`
* copy -  `Node(int key)`

#### functions:
the baisic functions are: `getKey()`,`getInfo()`,`setInfo(String s)`,`getTag()` ,`setTag(double t)`,
`compareTo(Node other)`,`getPreviousKey()`,`setPreviousKey(int key)`, `getPlace()` , `setPlace(int place)`.
* `getNi()` - returns a collection with all the Neighbor nodes of this node_info
* `hasNi(int key)` - return true if key is on the list of neighbors of this node.
* `addNi(node_info t, double weight)` -adds the node_info (t) to this node_info neighbors.checking if this node==@param t and if not adds it to his neighbors list and add the weight to his weight list.
* `removeNode(node_info node)` - checking if this node exist in the neighbor list and remove from the list.
* `equals(Object obj)` - an equals function.do this with three private Auxiliary functions.
* `toString()` - toString function that string each node without his neighbor.

###  class: WGraph_DS:
This class implements the interface that represents an undirectional weighted graph.It support a large number of nodes (over 10^6, with average degree of 10).

In this class we have four feilds:
1) graph - Represents the list of node_info (the vertexes).
2) node_size - Represents the number of nodes in the graph.
3) edge_size - Represents the number of edges in the graph.
4) mc - Represents the number of changes in the graph.

In this class we have two constructors and fourteen functions.
#### constructors:
* `WGraph_DS()` - default constructor.
* `WGraph_DS (weighted_graph graph)` - a deep copy constructor.

#### functions:
* `getNode(int key)` - return the node by the key .
* `hasEdge(int node1, int node2)` - return true if and only if there is an edge between node1 and node2.
* `getEdge(int node1, int node2)` - return the weight if the edge (node1, node1).
* `addNode(int key)` - add a new node to the graph with the given key by building the node and insert it into the HashMap.
* `connect(int node1, int node2, double w)` - Connect an edge between node1 and node2, with an edge with weight >=0.
* `getV()`- return a pointer (shallow copy) for a collection representing all the nodes in the graph.
* `getV(int node_id)` - returns a Collection containing all the nodes connected to node_id.
* `removeNode(int key)` - * Delete the node (with the given ID) from the graph and removes all edges which starts or ends at this node.Do this by for each loop that acts on         this node and removes the edges between its neighbors.
* `removeEdge(int node1, int node2)` - Delete the edge between two nodes.
* `nodeSize()` - return the number of vertices (nodes) in the graph.
* `edgeSize()` - return the number of edges (undirectional graph).
* `getMC()` - return the Mode Count - for testing changes in the graph.
* `equals(Object obj)` - an equals function , do this with two iterator that run on the vertexes of the graph and inside asked if its the same node in both graph with Node          equals function.
* `toString()` - toString function that string each node with his neighbor.

###  class: WGraph_Algo:
This class implements the interface that represents an Undirected (positive) Weighted Graph Theory algorithms including:
0. `clone();` (copy)
1. `init(graph);`
2. `isConnected();`
3. `double shortestPathDist(int src, int dest);`
4. `List<node_data> shortestPath(int src, int dest);`
5. `Save(file);`
6. `Load(file);`

In this class we have one feilds:
W_graph - Represents the graph.

In this class we have one constructor , nine public functions and three private function.
#### constructors:
`WGraph_Algo()` - default constructor.

#### functions:
##### (public):
* `init(weighted_graph g)` - Init the graph on which this set of algorithms will operate .
* `getGraph()` - Return the underlying graph of which this class works.
* `copy()` - deep copy constructor. Implemented by a copy function in Node and WGraph_DS classes.
* `isConnected()` - Returns true if and only if there is a valid path from every node to each other node.Do it by checking if BFS function return the number of nodes in this        graph.(An explanation of BFS is given in the function itself).
* `shortestPathDist(int src, int dest)` - * returns the length of the shortest path between src to dest we will do it by first check that the input is correct and then send it      to the BFS function, if true we will return the tag of the dest.and initialize the fields with resetNode function.Otherwise we will return -1.
* `shortestPath(int src, int dest)` -   * returns the the shortest path between src to dest - as an ordered List of nodes:src--> n1-->n2-->...dest we will do it by First check      that the input is correct and then we will send it to the BFS function, if true we will use the while loop and enter the list according to the field PreviousKey,in the end we    will initialize fields with resetNode function and return the path(null if none).
* `save(String file)` - Saves this weighted (undirected) graph to the given file name.
* `load(String file)` -  This method load a graph to this graph algorithm.if the file was successfully loaded - the underlying graph of this class will be changed 
   (to the loaded one), in case the graph was not loaded the original graph should remain "as is".
* `equals(Object obj)` - an equals function.do this with equals function on WGraph_DS class.
   
   ##### (private):
* `BFS (weighted_graph graph)` - First we get the first vertex of the graph and build a queue of type node_info We will initialize a counter that will tell us how many             vertices there are in the graph.We will add the first vertex to the stack and change its meta_data to "black".We will create a while loop, which will run as long as the         queue is not empty,and within it we will create a for loop, which will run on the neighbors of each vertex,and if its meta_data is "white" it will change the meta_data to       "black" and do counter ++. Finally we reset the meta_data and return the counter.
* `BFS ( node_info src , node_info dest )` - First we define a counterplace int that will be responsible for the location of each node in the visit array.we will add to the        priority queue the src variable.We will initialize src.tag to be 0 .We will perform a loop while as long as the queue is not empty we will perform:ask if the key of the          current father == dest.key , if so we will breake away from the BFS function.after that we will run on the neighbors of the same vertex that is in the queue,  update its        place on the visit array (with the counterPlace)We are then asked whether in the Boolean array its value is equal to false and if so we will ask if his tag is greater then      his weigt +his father tag and if so we will change his tag to be:his weigt +his father tag.after that we will change to true the place of the father.and finally we will          return false.
* `resetNodes(weighted_graph W_graph)` - This function initializes the fields:tag,PreviousKey,Place.



And it is all my project.

@author liav.weiss
