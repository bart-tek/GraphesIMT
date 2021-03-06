package AdjacencyList;

import java.util.ArrayList;
import java.util.List;

import Abstraction.AbstractListGraph;
import GraphAlgorithms.GraphTools;
import Nodes.UndirectedNode;
import Abstraction.IUndirectedGraph;

public class UndirectedGraph<A extends UndirectedNode> extends AbstractListGraph<A> implements IUndirectedGraph<A> {

    //--------------------------------------------------
    // 				Constructors
    //--------------------------------------------------

	public UndirectedGraph() {
		 this.nodes = new ArrayList<>();
	}
	
	public UndirectedGraph(List<A> nodes) {
        super(nodes);
        for (UndirectedNode i : nodes) {
            this.m += i.getNbNeigh();
        }
    }

    public UndirectedGraph(int[][] matrix) {
        this.order = matrix.length;
        this.nodes = new ArrayList<>();
        for (int i = 0; i < this.order; i++) {
            this.nodes.add(this.makeNode(i));
        }
        for (A n : this.getNodes()) {
            for (int j = n.getLabel(); j < matrix[n.getLabel()].length; j++) {
                A nn = this.getNodes().get(j);
                if (matrix[n.getLabel()][j] != 0) {
                    n.getNeighbours().put(nn,0);
                    nn.getNeighbours().put(n,0);
                    this.m++;
                }
            }
        }
    }

    public UndirectedGraph(UndirectedGraph<A> g) {
        super();
        this.order = g.getNbNodes();
        this.m = g.getNbEdges();
        this.nodes = new ArrayList<>();
        for (A n : g.getNodes()) {
            this.nodes.add(makeNode(n.getLabel()));
        }
        for (A n : g.getNodes()) {
            A nn = this.getNodes().get(n.getLabel());
            for (UndirectedNode sn : n.getNeighbours().keySet()) {
                A snn = this.getNodes().get(sn.getLabel());
                nn.getNeighbours().put(snn,0);
                snn.getNeighbours().put(nn,0);
            }
        }

    }

    // ------------------------------------------
    // 				Accessors
    // ------------------------------------------

    @Override
    public int getNbEdges() {
        return this.m;
    }

    @Override
    public boolean isEdge(A x, A y) {    

    	return getNodeOfList(x).getNeighbours().containsKey(getNodeOfList(y)) && getNodeOfList(y).getNeighbours().containsKey(getNodeOfList(x));
    }

    @Override
    public void removeEdge(A x, A y) {
    	if(isEdge(x,y)){
    		getNodeOfList(x).getNeighbours().remove(getNodeOfList(y));
    		getNodeOfList(y).getNeighbours().remove(getNodeOfList(x));
    	}
    }

    @Override
    public void addEdge(A x, A y) {
    	if(!isEdge(x,y)){
    		getNodeOfList(x).addNeigh(getNodeOfList(y), getNodeOfList(y).getLabel());
    		getNodeOfList(y).addNeigh(getNodeOfList(x), getNodeOfList(x).getLabel());
    	}
    }

    //--------------------------------------------------
    // 					Methods
    //--------------------------------------------------
    
    /**
     * Method to generify node creation
     * @param label of a node
     * @return a node typed by A extends UndirectedNode
     */
    @Override
    public A makeNode(int label) {
        return (A) new UndirectedNode(label);
    }

    /**
     * @return the corresponding nodes in the list this.nodes
     */
    public A getNodeOfList(A src) {
        return this.getNodes().get(src.getLabel());
    }
    
    /**
     * @return the adjacency matrix representation int[][] of the graph
     */
    @Override
    public int[][] toAdjacencyMatrix() {
        int[][] matrix = new int[order][order];

        for (A node : this.getNodes()) {
        	for (UndirectedNode neigh : node.getNeighbours().keySet()) {
        		matrix[node.getLabel()][neigh.getLabel()]++;
        	}
        }
        
        return matrix;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (UndirectedNode n : nodes) {
            s.append("neighbours of ").append(n).append(" : ");
            for (UndirectedNode sn : n.getNeighbours().keySet()) {
                s.append(sn).append(" ");
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

    @SuppressWarnings("unchecked")
	public static void main(String[] args) {
        int[][] mat = GraphTools.generateGraphData(10, 20, false, true, false, 100001);
        GraphTools.afficherMatrix(mat);
        @SuppressWarnings("rawtypes")
		UndirectedGraph al = new UndirectedGraph(mat);
        System.out.println(al);

        System.out.println("isEdge(0,3) (true) : " + al.isEdge(new UndirectedNode(0), new UndirectedNode(3)));
        System.out.println("isEdge(3,0) (true) : " + al.isEdge(new UndirectedNode(3), new UndirectedNode(0)));
        System.out.println("isEdge(0,5) (false) : " + al.isEdge(new UndirectedNode(0), new UndirectedNode(5)));

        al.addEdge(new UndirectedNode(0), new UndirectedNode(5));
        al.addEdge(new UndirectedNode(5), new UndirectedNode(0));
        System.out.println("addEdge(0,5) (true) : " + al.isEdge(new UndirectedNode(0), new UndirectedNode(5)));
        System.out.println("addEdge(5,0) (true) : " + al.isEdge(new UndirectedNode(5), new UndirectedNode(0)));
        
        GraphTools.afficherMatrix(al.toAdjacencyMatrix());
        
        al.removeEdge(new UndirectedNode(0), new UndirectedNode(5));
        System.out.println("removeEdge(0,5) (false) : " + al.isEdge(new UndirectedNode(0), new UndirectedNode(5)));
        
        GraphTools.afficherMatrix(al.toAdjacencyMatrix());
    }

}
