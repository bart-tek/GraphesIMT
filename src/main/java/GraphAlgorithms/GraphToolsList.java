package GraphAlgorithms;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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
import Collection.Pair;
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
	public int[] explorerGrapheLargeur(AbstractListGraph<AbstractNode> graph, AbstractNode s) {
		boolean mark[] = new boolean[graph.getNbNodes()];
		int[] dist = new int[graph.getNbNodes()];
		int currentDist = 0;
		for (AbstractNode v : graph.getNodes()) {
			mark[v.getLabel()] = false;
		}
		mark[s.getLabel()] = true;
		dist[s.getLabel()] = 0;
		boolean hasUnknownSuc = false;
		Queue<AbstractNode> toVisit = new LinkedList<AbstractNode>();
		toVisit.add(s);
		while (!toVisit.isEmpty()) {
			AbstractNode v = toVisit.poll();
			hasUnknownSuc = false;
			if (s instanceof DirectedNode) {
				for (DirectedNode w : ((DirectedNode) v).getSuccs().keySet()) {
					if (!mark[w.getLabel()]) {
						if (!hasUnknownSuc) {
							hasUnknownSuc = true;
							currentDist++;
						}
						mark[w.getLabel()] = true;
						toVisit.add(w);
						dist[w.getLabel()] = currentDist;
					}
				}
			} else {
				for (UndirectedNode w : ((UndirectedNode) v).getNeighbours().keySet()) {
					if (!mark[w.getLabel()]) {
						if (!hasUnknownSuc) {
							hasUnknownSuc = true;
							currentDist++;
						}
						mark[w.getLabel()] = true;
						toVisit.add(w);
						dist[w.getLabel()] = currentDist;
					}
				}
			}
		}
		return dist;
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

			for (int i = 0; i < f1sorted.size(); i++) {
				f1SortedTab[i] = f1sorted.get(i);
			}

			DirectedGraph<DirectedNode> gInverse = (DirectedGraph<DirectedNode>) ((DirectedGraph) g).computeInverse();
			explorerGrapheProfondeur((AbstractListGraph) gInverse, f1SortedTab);
		}

	}

	public static void main(String[] args) {
		int[][] Matrix = GraphTools.generateGraphData(10, 20, false, false, true, 110001);
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
		// Pour un graphe orienté de 100 noeuds et 150 arc, on a un temps d'exécution de
		// 527091 ns.

		System.out.println("Exploration du graphe en largeur :");
		long startTimeLargeur = System.nanoTime();
		gtl.explorerGrapheLargeur((AbstractListGraph) al, al.getNodes().get(0));
		long durationLargeur = System.nanoTime() - startTimeLargeur;

		System.out.println("Durée d'exécution du parcours en largeur : " + durationLargeur + " ns\n");
		// Pour un graphe orienté de 100 noeuds et 150 arc, on a un temps d'exécution de
		// 2142581 ns.

		// Le parcours en largeur est donc environ 4 fois plus lent que le parcours en
		// profondeur.

		// System.out.println("Calcul des composantes fortement connexes :");
		// System.out.println(getCompFortementConnexe((AbstractListGraph) al));

		DirectedValuedGraph dg = new DirectedValuedGraph(
				GraphTools.generateValuedGraphData(15, false, false, true, false, 100001));

		System.out.println(dg.toString());
		Pair<int[], List<DirectedNode>> ret = bellman(dg, dg.getNodes().get(0));
		System.out.println(Arrays.toString(ret.getLeft()));
		System.out.println(ret.getRight());

		testDijsktra();
		
		
	}

	private static void testDijsktra() {
		DirectedValuedGraph dijTest = new DirectedValuedGraph(
				GraphTools.generateValuedGraphData(5, false, true, true, false, 100001));

		System.out.println(dijTest.toString());
		Pair<int[], List<DirectedNode>> retDij = dijsktra(dijTest, dijTest.getNodes().get(0));
		System.out.println(Arrays.toString(retDij.getLeft()));
		System.out.println(retDij.getRight());
	}

	/**
	 * Time complexity:  O(n*m)
	 * Space complexity: O(2n + n²)
	 */
	public static Pair<int[], List<DirectedNode>> bellman(DirectedValuedGraph g, DirectedNode s) {

		int n = g.getNbNodes();
		int[] values = new int[n];
		List<DirectedNode> precedent = new ArrayList<>(n);
		Map<Integer, List<DirectedNode>> distMap = new HashMap<Integer, List<DirectedNode>>();

		// Initialisation
		for (int i = 0; i < n; i++) {
			values[i] = 999999999; // MAX_INTEGER overflowed for unknown reasons 
			precedent.add(null);
		}
		values[s.getLabel()] = 0;
		precedent.set(s.getLabel(), s);
		int[] distancesFromS = new GraphToolsList().explorerGrapheLargeur((AbstractListGraph) g, (AbstractNode) s);

		// Order nodes by distance from S
		for (int i = 0; i < distancesFromS.length; i++) {
			if (distMap.containsKey(distancesFromS[i])) {
				distMap.get(distancesFromS[i]).add(g.getNodes().get(i));
			} else {
				List<DirectedNode> tmp = new ArrayList<DirectedNode>();
				tmp.add(g.getNodes().get(i));
				distMap.put(distancesFromS[i], tmp);
			}
		}
		// k is the number of edge separating node from s.
		for (int k = 0; k < n; k++) {
			if (distMap.containsKey(k))
				for (DirectedNode node : distMap.get(k)) {
					for (Entry<DirectedNode, Integer> entry : node.getSuccs().entrySet()) {
						if (entry.getValue() + values[node.getLabel()] < values[entry.getKey().getLabel()]) {
							values[entry.getKey().getLabel()] = entry.getValue() + values[node.getLabel()];
							precedent.set(entry.getKey().getLabel(), node);
						}
					}
				}
		}
		
		// If one step further changes the values then there is a negative cycle.
		DirectedNode node = g.getNodes().get((n + s.getLabel()) % n);
		for (Entry<DirectedNode, Integer> entry : node.getSuccs().entrySet()) {
			if (entry.getValue() + values[node.getLabel()] < values[entry.getKey().getLabel()]) {
				System.out.println("/!\\ There is a negative cycle.");
			}
		}

		return new Pair<int[], List<DirectedNode>>(values, precedent);
	}
	
	
	/**
	 * Time complexity:  O(n*m)
	 * Space complexity: O(4*n)
	 */
	public static Pair<int[], List<DirectedNode>> dijsktra(DirectedValuedGraph g, DirectedNode s) {
		
		int n = g.getNbNodes();
		boolean[] mark = new boolean[n];
		int[] values = new int[n];
		List<DirectedNode> precedent = new ArrayList<>(n);
		
		//Init
		for (int i = 0; i < n; i++) {
			values[i] = 999999999;
			precedent.add(null);
		}
		values[s.getLabel()] = 0;
		precedent.set(s.getLabel(), s);
		
		//SortedValues will give the lowest available value in log(n) time.
		TreeMap<Integer, DirectedNode> sortedValues = new TreeMap<>();
		sortedValues.put(0, s);
		
		while (!sortedValues.isEmpty()) {
			Entry<Integer, DirectedNode> currentNode = sortedValues.firstEntry();
			sortedValues.remove(currentNode.getKey());
			mark[currentNode.getValue().getLabel()] = true;
			for(Entry<DirectedNode, Integer> succ: currentNode.getValue().getSuccs().entrySet()) {
				if(!mark[succ.getKey().getLabel()]) {
					int potentialCost = succ.getValue()+currentNode.getKey();
					int oldCost = values[succ.getKey().getLabel()];
					if( potentialCost< oldCost) {
						values[succ.getKey().getLabel()] = potentialCost;
						sortedValues.put(potentialCost, succ.getKey());
						precedent.set(succ.getKey().getLabel(), currentNode.getValue());
						
					}
				}
			}
		}
		return new Pair<int[], List<DirectedNode>>(values, precedent);
	}
}
