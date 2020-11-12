# Ex1

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

In this class we have two constructors and sixtin functions.
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

In this class we have two constructors and fourtin functions.
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
