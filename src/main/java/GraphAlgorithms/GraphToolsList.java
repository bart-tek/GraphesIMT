package GraphAlgorithms;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import Abstraction.AbstractListGraph;
import AdjacencyList.DirectedGraph;
import AdjacencyList.DirectedValuedGraph;
import AdjacencyList.UndirectedGraph;
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
		cpt++;
		debut[node.getLabel()] = cpt;
		visite[node.getLabel()] = 1;
		for (DirectedNode next : node.getSuccs().keySet()) {
			if (visite[next.getLabel()] == 0) {
				explorerDirectedSommet(next);
			}
		}
		cpt++;
		visite[node.getLabel()] = 2;
		fin[node.getLabel()] = cpt;
	}

	public void explorerUndirectedSommet(UndirectedNode node) {
		cpt++;
		debut[node.getLabel()] = cpt;
		visite[node.getLabel()] = 1;
		for (UndirectedNode next : node.getNeighbours().keySet()) {
			if (visite[next.getLabel()] == 0) {
				explorerUndirectedSommet(next);
			}
		}
		cpt++;
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

		Queue<AbstractNode> toVisit = new LinkedList<AbstractNode>();
		toVisit.add(s);
		while (!toVisit.isEmpty()) {
//			System.out.println(toVisit.toString());
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
		AbstractNode first = g.getNodes().get(0); // Vérification du type de graphe
		if (first instanceof DirectedNode) {
			for (int i : nodes) {
				if (visite[i] == 0) {
					explorerDirectedSommet((DirectedNode) g.getNodes().get(i));
				}
			}
		} else {
			for (int i : nodes) {
				if (visite[i] == 0) {
					explorerUndirectedSommet((UndirectedNode) g.getNodes().get(i));
				}
			}
		}
	}

	public void calculComposanteFortementConnexe(AbstractListGraph<AbstractNode> g) {
		if (g instanceof DirectedGraph) {
			int[] nodes = new int[g.getNbNodes()];
			
			for (AbstractNode n : g.getNodes()) {
				nodes[n.getLabel()] = n.getLabel();
			}
			
			explorerGrapheProfondeur(g, nodes);
			int[] f1 = fin;
			SortedMap<Integer, Integer> nodesEnd = new TreeMap<>();
			for (int i = 0; i < f1.length; i++) {
				nodesEnd.put(f1[i], i);
			}
			List<Integer> f1sorted = new ArrayList<>(nodesEnd.values());

			Collections.reverse(f1sorted);
			
			int[] f1SortedTab = new int[f1sorted.size()];
			
			for (int i = 0 ; i < f1sorted.size() ; i++) {
				f1SortedTab[i] = f1sorted.get(i);
			}

			DirectedGraph<DirectedNode> gInverse = (DirectedGraph<DirectedNode>) ((DirectedGraph) g).computeInverse();
			explorerGrapheProfondeur((AbstractListGraph) gInverse, f1SortedTab);
		}

	}

	public static void main(String[] args) {
		int[][] Matrix = GraphTools.generateGraphData(10, 20, false, false, true, 100001);
		GraphTools.afficherMatrix(Matrix);
		UndirectedGraph<UndirectedNode> al = new UndirectedGraph<>(Matrix);
		System.out.println(al);
		
		GraphToolsList gtl = new GraphToolsList();
		
		int[] nodes = new int[al.getNbNodes()];
		
		for (AbstractNode s : al.getNodes()) {
			nodes[s.getLabel()] = s.getLabel();
		}
		
		
		long startTimeProfondeur = System.nanoTime();
		gtl.explorerGrapheProfondeur((AbstractListGraph) al, nodes);
		long durationProfondeur = System.nanoTime() - startTimeProfondeur;
		System.out.println("Exploration du graphe en profondeur :");
		System.out.println("Fin : " + Arrays.toString(fin));
		System.out.println("Début :" + Arrays.toString(debut));
		
		System.out.println("Durée d'exécution du parcours en profondeur : " + durationProfondeur + " ns\n");
		// Pour un graphe orienté de 100 noeuds et 150 arc, on a un temps d'exécution de 527091 ns.
		
		System.out.println("Exploration du graphe en largeur :");
		long startTimeLargeur = System.nanoTime();
		gtl.explorerGrapheLargeur((AbstractListGraph) al, al.getNodes().get(0));
		long durationLargeur = System.nanoTime() - startTimeLargeur;
		
		System.out.println("Durée d'exécution du parcours en largeur : " + durationLargeur + " ns\n");
		// Pour un graphe orienté de 100 noeuds et 150 arc, on a un temps d'exécution de 2142581 ns.
		
		// Le parcours en largeur est donc environ 4 fois plus lent que le parcours en profondeur.
		
//		System.out.println("Calcul des composantes fortement connexes :");
//		System.out.println(getCompFortementConnexe((AbstractListGraph) al));
	}
}
