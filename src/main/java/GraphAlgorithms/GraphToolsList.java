package GraphAlgorithms;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import Abstraction.AbstractListGraph;
import AdjacencyList.DirectedGraph;
import AdjacencyList.DirectedValuedGraph;
import AdjacencyList.UndirectedValuedGraph;
import Collection.Triple;
import Nodes.AbstractNode;
import Nodes.DirectedNode;
import Nodes.UndirectedNode;

public class GraphToolsList  extends GraphTools {

	private static int _DEBBUG =0;

	private static int[] visite;
	private static int[] debut;
	private static int[] fin;
	private static List<Integer> order_CC;
	private static int cpt=0;

	//--------------------------------------------------
	// 				Constructors
	//--------------------------------------------------

	public GraphToolsList(){
		super();
	}

	// ------------------------------------------
	// 				Accessors
	// ------------------------------------------



	// ------------------------------------------
	// 				Methods
	// ------------------------------------------
	
	public void explorerDirectedSommet(DirectedNode node) {
		
		debut[node.getLabel()] = cpt;
		visite[node.getLabel()]
		
		for (DirectedNode next : node.getSuccs().keySet()) {
			if (!visitedNodes.contains(next)) {
				explorerDirectedSommet(next, visitedNodes);
			}
		}
	}

	public void explorerUndirectedSommet(UndirectedNode node, Set<UndirectedNode> visitedNodes) {
		visitedNodes.add(node);
		for (UndirectedNode next : node.getNeighbours().keySet()) {
			if (!visitedNodes.contains(next)) {
				explorerUndirectedSommet(next, visitedNodes);
			}
		}
	}

	void explorerGrapheProfondeur(AbstractListGraph<AbstractNode> g) {
		
		AbstractNode first = g.getNodes().get(0); // Vérification du type de graphe

		debut = new int[g.getNbNodes()];
		visite = new int[g.getNbNodes()];
		fin = new int[g.getNbNodes()];
		
		if (first instanceof DirectedNode) {
			for (AbstractNode s : g.getNodes()) {
				if (!atteint.contains(s)) {
					explorerDirectedSommet((DirectedNode)s, atteint);
				}
			}
		}
		else {
			Set<UndirectedNode> atteint = new HashSet<UndirectedNode>();
			for (AbstractNode s : g.getNodes()) {        
				if (!atteint.contains(s)) {
					explorerUndirectedSommet((UndirectedNode)s, atteint);
				}
			}
		}
	}

	public static void main(String[] args) {
		int[][] Matrix = GraphTools.generateGraphData(10, 20, false, false, true, 100001);
		GraphTools.afficherMatrix(Matrix);
		DirectedGraph<DirectedNode> al = new DirectedGraph<>(Matrix);
		System.out.println(al);

		// A completer
	}
}
