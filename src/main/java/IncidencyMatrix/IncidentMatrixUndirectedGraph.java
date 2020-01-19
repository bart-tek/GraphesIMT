package IncidencyMatrix;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Abstraction.AbstractIncidentGraph;
import Abstraction.IUndirectedGraph;
import AdjacencyMatrix.AdjacencyMatrixUndirectedGraph;
import GraphAlgorithms.GraphTools;
import Nodes.UndirectedNode;

public class IncidentMatrixUndirectedGraph extends AbstractIncidentGraph<UndirectedNode> implements IUndirectedGraph<UndirectedNode>{

	/**
	 * 
	 * @param n, the number of vertices
	 * @param m, the number of edges
	 * @param multi, at true if we want a multi-graph
	 * @param s, at true if the graph is symmetric
	 * @param c, at true if the graph is connexted
	 * @param seed, the unique seed giving a unique random graph
	 */ 
	public IncidentMatrixUndirectedGraph(int n, int e, boolean multi, boolean s, boolean c, int seed) {
		
		this.order = n;
		this.m = e;
		
		int[][] matrix = new int[order][m];
		Random rand = new Random(seed);
		if(c){
			List<Integer> vis = new ArrayList<>();
			int from = rand.nextInt(n);
			vis.add(from);
			from = rand.nextInt(n);
			while(vis.size() < n){
				if(!vis.contains(from)){
					int indDest = rand.nextInt(vis.size());
					int dest = vis.get(indDest);				
					if(s) {
						matrix[dest][from] = 1;
					}
					matrix[from][dest] = 1;
					vis.add(from);
				}
				from = rand.nextInt(n);				
			}
			n -= order-1;
		}

		int edge = 0;
		while(edge != e){
			
			int s1 = rand.nextInt(n);
			int s2 = rand.nextInt(n);

			if(!multi){
				if(s1!=s2 && matrix[s1][edge]!=1 && matrix[s2][edge]!=1 ){
					matrix[s1][edge] = 1;
					matrix[s2][edge] = 1;
					edge++;
				}
			}
			else{
				if(matrix[s1][edge]==0 && matrix[s2][edge]==0){
					int val = ( s1!=s2 ? (e<3 ? e : rand.nextInt(3) +1) : 1);
					matrix[s1][edge] = val;
					matrix[s2][edge] = val;
					edge++;
				}
			}
		}
		this.matrix = matrix;
	}
	
	@Override
	public int[][] toAdjacencyMatrix() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getNbEdges() {
		return m;
	}

	@Override
	public boolean isEdge(UndirectedNode x, UndirectedNode y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeEdge(UndirectedNode x, UndirectedNode y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addEdge(UndirectedNode x, UndirectedNode y) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder("Incidency Matrix: \n");
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
		
		int[][] mat2 = GraphTools.generateGraphData(5, 6, false, true, false, 100001);
		GraphTools.afficherMatrix(mat2);
		
		IncidentMatrixUndirectedGraph am = new IncidentMatrixUndirectedGraph(5, 6, false, true, false, 100001);
		System.out.println(am);
		System.out.println("N = "+am.getNbNodes()+ "\n M = "+am.getNbEdges());

//		System.out.println("\nisEdge(0,1) : " + am.isEdge(new UndirectedNode(2), new UndirectedNode(3)));
//		System.out.println("isEdge(3,0) : " + am.isEdge(new UndirectedNode(3), new UndirectedNode(0))); 
//		
//		am.addEdge(new UndirectedNode(3), new UndirectedNode(0));
//		System.out.println("addEdge(3,0) : " + am.isEdge(new UndirectedNode(3), new UndirectedNode(0)));
//		
//		am.addEdge(new UndirectedNode(3), new UndirectedNode(0));
//		System.out.println("addEdge(3,0) (doit être à 3) : ");
//		System.out.println(am);
//		
//		am.removeEdge(new UndirectedNode(0), new UndirectedNode(1));
//		System.out.println("removeEdge(0,1) : " + am.isEdge(new UndirectedNode(2), new UndirectedNode(5)));
//		
//		am.removeEdge(new UndirectedNode(3), new UndirectedNode(0));
//		System.out.println("removeEdge(3,0) : " + am.isEdge(new UndirectedNode(3), new UndirectedNode(0)));
//		
//		am.removeEdge(new UndirectedNode(3), new UndirectedNode(0));
//		System.out.println("removeEdge(3,0) : " + am.isEdge(new UndirectedNode(3), new UndirectedNode(0)));
	
	}

}
