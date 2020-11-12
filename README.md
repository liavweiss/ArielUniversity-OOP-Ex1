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
