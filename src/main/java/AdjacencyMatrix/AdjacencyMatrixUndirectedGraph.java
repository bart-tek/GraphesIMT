package AdjacencyMatrix;

import Abstraction.AbstractMatrixGraph;
import GraphAlgorithms.GraphTools;
import Nodes.AbstractNode;
import Nodes.UndirectedNode;
import Abstraction.IUndirectedGraph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class represents the undirected graphs structured by an adjacency matrix.
 * It is possible to have simple and multiple graph
 */
public class AdjacencyMatrixUndirectedGraph extends AbstractMatrixGraph<UndirectedNode> implements IUndirectedGraph {
	
	//--------------------------------------------------
	// 				Constructors
	//-------------------------------------------------- 
	
	public AdjacencyMatrixUndirectedGraph() {
		super();
	}
	
	public AdjacencyMatrixUndirectedGraph(int[][] mat) {
		this.order=mat.length;
		this.matrix = new int[this.order][this.order];
		for(int i = 0; i<this.order; i++){
			for(int j = i; j<this.order; j++){
				this.matrix[i][j] = mat[i][j];
				this.matrix[j][i] = mat[i][j];
				this.m += mat[i][j];
			}
		}	
	}
	
	public AdjacencyMatrixUndirectedGraph(IUndirectedGraph g) {
		this.order = g.getNbNodes(); 				
		this.m = g.getNbEdges(); 				
		this.matrix = g.toAdjacencyMatrix(); 
	}

	//--------------------------------------------------
	// 					Accessors
	//--------------------------------------------------

	@Override
	public int getNbEdges() {
		return this.m;
	}

	public List<Integer> getNeighbours(AbstractNode x) {
		List<Integer> l = new ArrayList<>();
		for(int i = 0; i<matrix[x.getLabel()].length; i++){
			if(matrix[x.getLabel()][i]>0){
				l.add(i);
			}
		}
		return l;
	}
	
	// ------------------------------------------------
	// 					Methods 
	// ------------------------------------------------		
	
	@Override
	public boolean isEdge(AbstractNode x, AbstractNode y) {
		return matrix[x.getLabel()][y.getLabel()] > 0 && matrix[y.getLabel()][x.getLabel()] > 0;		
	}
	
	/**
     * removes the edge (x,y) if there exists at least one between these nodes in the graph.
     */
	@Override
	public void removeEdge(AbstractNode x, AbstractNode y) {
		if (isEdge(x,y)) {			
			matrix[x.getLabel()][y.getLabel()]--;
			matrix[y.getLabel()][x.getLabel()]--;
		}
	}

	/**
     * adds the edge (x,y), we allow the multi-graph.
     */
	@Override
	public void addEdge(AbstractNode x, AbstractNode y) {
		matrix[x.getLabel()][y.getLabel()]++;
		matrix[y.getLabel()][x.getLabel()]++;
	}

	
	/**
     * @return the adjacency matrix representation int[][] of the graph
     */
	public int[][] toAdjacencyMatrix() {
		return this.matrix;
	}
	
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder("Adjacency Matrix: \n");
		for (int[] ints : this.matrix) {
			for (int anInt : ints) {
				s.append(anInt).append(" ");
			}
			s.append("\n");
		}
		s.append("\n");
		return s.toString();
	}

	public static void main(String[] args) {
		int[][] mat2 = GraphTools.generateGraphData(10, 35, false, true, false, 100001);
		GraphTools.afficherMatrix(mat2);
		AdjacencyMatrixUndirectedGraph am = new AdjacencyMatrixUndirectedGraph(mat2);
		System.out.println(am);
		System.out.println("N = "+am.getNbNodes()+ "\n M = "+am.getNbEdges());
		List<Integer> t2 = am.getNeighbours(new UndirectedNode(2));
		for (Integer integer : t2) {
			System.out.print(integer + ", ");
		}

		System.out.println("\nisEdge(0,1) : " + am.isEdge(new UndirectedNode(2), new UndirectedNode(3)));
		System.out.println("isEdge(3,0) : " + am.isEdge(new UndirectedNode(3), new UndirectedNode(0))); 
		
		am.addEdge(new UndirectedNode(3), new UndirectedNode(0));
		System.out.println("addEdge(3,0) : " + am.isEdge(new UndirectedNode(3), new UndirectedNode(0)));
		
		am.addEdge(new UndirectedNode(3), new UndirectedNode(0));
		System.out.println("addEdge(3,0) (doit être à 3) : ");
		System.out.println(am);
		
		am.removeEdge(new UndirectedNode(0), new UndirectedNode(1));
		System.out.println("removeEdge(0,1) : " + am.isEdge(new UndirectedNode(2), new UndirectedNode(5)));
		
		am.removeEdge(new UndirectedNode(3), new UndirectedNode(0));
		System.out.println("removeEdge(3,0) : " + am.isEdge(new UndirectedNode(3), new UndirectedNode(0)));
		
		am.removeEdge(new UndirectedNode(3), new UndirectedNode(0));
		System.out.println("removeEdge(3,0) : " + am.isEdge(new UndirectedNode(3), new UndirectedNode(0)));
	
	}
}
