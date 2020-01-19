package AdjacencyMatrix;

import Abstraction.AbstractMatrixGraph;
import GraphAlgorithms.GraphTools;
import Nodes.AbstractNode;
import Nodes.DirectedNode;
import Nodes.UndirectedNode;
import Abstraction.IDirectedGraph;
import AdjacencyList.DirectedGraph;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the directed graphs structured by an adjacency matrix.
 * It is possible to have simple and multiple graph
 */
public class AdjacencyMatrixDirectedGraph extends AbstractMatrixGraph<DirectedNode> implements IDirectedGraph {

	//--------------------------------------------------
	// 				Constructors
	//-------------------------------------------------- 

	public AdjacencyMatrixDirectedGraph() {
		super();
	}

	public AdjacencyMatrixDirectedGraph(int[][] M) {
		this.order = M.length;
		this.matrix = new int[this.order][this.order];
		for(int i = 0; i<this.order; i++){
			for(int j = 0; j<this.order; j++){
				this.matrix[i][j] = M[i][j];
				this.m += M[i][j];
			}
		}
	}

	public AdjacencyMatrixDirectedGraph(IDirectedGraph<DirectedNode> g) {
		this.order = g.getNbNodes();
		this.m = g.getNbArcs();
		this.matrix = g.toAdjacencyMatrix();
	}

	//--------------------------------------------------
	// 					Accessors
	//--------------------------------------------------

	@Override
	public int getNbArcs() {
		return this.m;
	}

	public List<Integer> getSuccessors(AbstractNode x) {
		List<Integer> v = new ArrayList<Integer>();
		for(int i =0;i<this.matrix[x.getLabel()].length;i++){
			if(this.matrix[x.getLabel()][i]>0){
				v.add(i);
			}
		}
		return v;
	}

	public List<Integer> getPredecessors(AbstractNode x) {
		List<Integer> v = new ArrayList<Integer>();
		for(int i =0;i<this.matrix.length;i++){
			if(this.matrix[i][x.getLabel()]>0){
				v.add(i);
			}
		}
		return v;
	}
	
	
	// ------------------------------------------------
	// 					Methods 
	// ------------------------------------------------		
	
	@Override
	public boolean isArc(AbstractNode from, AbstractNode to) {
		return matrix[from.getLabel()][to.getLabel()] > 0;
	}

	/**
	 * removes the arc (from,to) if there exists at least one between these nodes in the graph.
	 */
	@Override
	public void removeArc(AbstractNode from, AbstractNode to) {
		if (isArc(from,to)) {			
			matrix[from.getLabel()][to.getLabel()]--;
		}
	}

	/**
	 * Adds the arc (from,to). we allow multiple graph.
	 */
	@Override
	public void addArc(AbstractNode from, AbstractNode to) {
		matrix[from.getLabel()][to.getLabel()]++;
	}


	/**
	 * @return the adjacency matrix representation int[][] of the graph
	 */
	public int[][] toAdjacencyMatrix() {
		return this.matrix;
	}

	@Override
	public IDirectedGraph<DirectedNode> computeInverse() {
		
		int[][] ret = new int[order][order];
		
		for (int i = 0 ; i < order ; i++)  {
			for (int j = 0 ; j < order ; i++)  {
				ret[j][i] = matrix[i][j];
			}
		}
		
		
		return new AdjacencyMatrixDirectedGraph(ret);
	}

	@Override
	public String toString(){
		StringBuilder s = new StringBuilder("Adjacency Matrix: \n");
		for (int[] ints : matrix) {
			for (int anInt : ints) {
				s.append(anInt).append(" ");
			}
			s.append("\n");
		}
		s.append("\n");
		return s.toString();
	}

	public static void main(String[] args) {
		
		int[][] matrix2 = GraphTools.generateGraphData(10, 20, false, false, false, 100001);
		AdjacencyMatrixDirectedGraph am = new AdjacencyMatrixDirectedGraph(matrix2);
		System.out.println(am);
		List<Integer> t = am.getSuccessors(new DirectedNode(1));
		for (Integer integer : t) {
			System.out.print(integer + ", ");
		}
		System.out.println();
		List<Integer> t2 = am.getPredecessors(new DirectedNode(2));
		for (Integer integer : t2) {
			System.out.print(integer + ", ");
		}
		
		System.out.println("\nisArc(2,5) : " + am.isArc(new UndirectedNode(2), new UndirectedNode(3)));
		System.out.println("isArc(3,0) : " + am.isArc(new UndirectedNode(3), new UndirectedNode(0))); 
		
		am.addArc(new UndirectedNode(3), new UndirectedNode(0));
		System.out.println("addArc(3,0) : " + am.isArc(new UndirectedNode(3), new UndirectedNode(0)));
		
		am.addArc(new UndirectedNode(3), new UndirectedNode(0));
		System.out.println("addArc(3,0) (doit être à 2) : ");
		System.out.println(am);
		
		am.removeArc(new UndirectedNode(2), new UndirectedNode(5));
		System.out.println("removeArc(2,5) : " + am.isArc(new UndirectedNode(2), new UndirectedNode(5)));
		
		am.removeArc(new UndirectedNode(3), new UndirectedNode(0));
		System.out.println("removeArc(3,0) : " + am.isArc(new UndirectedNode(3), new UndirectedNode(0)));
		
		am.removeArc(new UndirectedNode(3), new UndirectedNode(0));
		System.out.println("removeArc(3,0) : " + am.isArc(new UndirectedNode(3), new UndirectedNode(0)));
		
	}
}
