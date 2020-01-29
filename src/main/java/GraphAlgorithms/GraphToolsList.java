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

public class GraphToolsList extends GraphTools {

	private static int _DEBBUG = 0;

	private static int[] visite;
	private static int[] debut;
	private static int[] fin;
	private static List<Integer> order_CC;
	private static int cpt = 0;

	// --------------------------------------------------
	// Constructors
	// --------------------------------------------------

	public GraphToolsList() {
		super();
	}

	// ------------------------------------------
	// Accessors
	// ------------------------------------------

	// ------------------------------------------
	// Methods
	// ------------------------------------------

	public void explorerDirectedSommet(DirectedNode node) {
		debut[node.getLabel()] = cpt;
		visite[node.getLabel()] = 1;
		cpt++;
		for (DirectedNode next : node.getSuccs().keySet()) {
			if (visite[next.getLabel()] == 0) {
				explorerDirectedSommet(next);
			}
		}
		visite[node.getLabel()] = 2;
		fin[node.getLabel()] = cpt;
	}

	public void explorerUndirectedSommet(UndirectedNode node) {
		debut[node.getLabel()] = cpt;
		visite[node.getLabel()] = 1;
		cpt++;
		for (UndirectedNode next : node.getNeighbours().keySet()) {
			if (visite[next.getLabel()] == 0) {
				explorerUndirectedSommet(next);
			}
		}
		visite[node.getLabel()] = 2;
		fin[node.getLabel()] = cpt;
	}

	// Calcule les composantes connexes du graphe
	public void explorerGrapheLargeur(AbstractListGraph<AbstractNode> graph, AbstractNode s) {
		boolean mark[] = new boolean[graph.getNbNodes()];

		for (AbstractNode v : graph.getNodes()) {
			mark[v.getLabel()] = false;
		}
		mark[s.getLabel()] = true;

		Queue<AbstractNode> toVisit = new PriorityQueue<AbstractNode>();
		toVisit.add(s);
		while (!toVisit.isEmpty()) {
			AbstractNode v = toVisit.poll();
			if (s instanceof DirectedNode) {
				for (DirectedNode w : ((DirectedNode) v).getSuccs().keySet()) {
					if (!mark[w.getLabel()]) {
						mark[w.getLabel()] = true;
						toVisit.add(w);
					}
				}
			} else {
				for (UndirectedNode w : ((UndirectedNode) v).getNeighbours().keySet()) {
					if (!mark[w.getLabel()]) {
						mark[w.getLabel()] = true;
						toVisit.add(w);
					}
				}
			}
		}
	}

	void explorerGrapheProfondeur(AbstractListGraph<AbstractNode> g, int[] nodes) {
		debut = new int[g.getNbNodes()];
		visite = new int[g.getNbNodes()];
		fin = new int[g.getNbNodes()];
		cpt = 0;
		AbstractNode first = nodes[0]; // Vérification du type de graphe
		if (first instanceof DirectedNode) {
			for (int i : nodes) {
				if (visite[i] == 0) {
					explorerDirectedSommet((DirectedNode) s, atteint);
				}
			}
		} else {
			for (int i : nodes) {
				if (visite[i] == 0) {
					explorerUndirectedSommet((UndirectedNode) s, atteint);
				}
			}
		}
	}

	public void calculComposanteFortementConnexe(AbstractListGraph<AbstractNode> g) {
		if (g instanceof DirectedGraph) {
			int[] nodes = new int[g.getNbNodes()];
			explorerGrapheProfondeur(g, nodes);
			int[] f1 = fin;
			DirectedGraph<DirectedNode> gInverse = ((DirectedGraph) g).computeInverse();

		}

	}

	public static void main(String[] args) {
		int[][] Matrix = GraphTools.generateGraphData(10, 20, false, false, true, 100001);
		GraphTools.afficherMatrix(Matrix);
		AbstractListGraph<AbstractNode> al = new DirectedGraph<>(Matrix);
		System.out.println(al);
	}
}
