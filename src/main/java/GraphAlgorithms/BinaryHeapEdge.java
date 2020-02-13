package GraphAlgorithms;

import java.util.ArrayList;
import java.util.List;

import Collection.Triple;
import Nodes.DirectedNode;

public class BinaryHeapEdge<A> {

	/**
	 * A list structure for a faster management of the heap by indexing
	 * 
	 */
	private List<Triple<A, A, Integer>> binh;

	public BinaryHeapEdge() {
		this.binh = new ArrayList<>();
	}

	public boolean isEmpty() {
		return binh.isEmpty();
	}

	/**
	 * Insert a new edge in the binary heap
	 * 
	 * @param from one node of the edge
	 * @param to   one node of the edge
	 * @param val  the edge weight
	 */
	public void insert(A from, A to, int val) {

		boolean isInserted = false;
		Triple<A, A, Integer> newVal = new Triple<>(from, to, val);

		binh.add(newVal);

		int i = binh.size() - 1;

		while (!isInserted && ((i - 1) / 2) >= 0) {
			if (binh.get((i - 1) / 2).getThird() > val) {
				swap(i, (i - 1) / 2);
				i = (i - 1) / 2;
			} else {
				isInserted = true;
			}
		}
	}

	/**
	 * Removes the root edge in the binary heap, and swap the edges to keep a valid
	 * binary heap
	 * 
	 * @return the edge with the minimal value (root of the binary heap)
	 * 
	 */
	public Triple<A, A, Integer> remove() {

		boolean isInserted = false;
		Triple<A, A, Integer> ret = binh.get(0);
		swap(0, binh.size() - 1);
		binh.remove(binh.size() - 1);

		int i = 0;

		while (!isInserted && !isLeaf(i)) {
			// Check if only one child:
			if (binh.size()-1 < 2 * i + 2) {
				if (binh.get(2 * i + 1).getThird() < binh.get(i).getThird()) {
					swap(i, 2 * i + 1);
					i = 2 * i + 1;
				}else {
					isInserted = true;
				}
			} else {
				if (binh.get(2 * i + 1).getThird() < binh.get(i).getThird()
						&& binh.get(2 * i + 1).getThird() <= binh.get(2 * i + 2).getThird()) {
					swap(i, 2 * i + 1);
					i = 2 * i + 1;
				} else if (binh.get(2 * i + 2).getThird() < binh.get(i).getThird()
						&& binh.get(2 * i + 2).getThird() <= binh.get(2 * i + 1).getThird()) {
					swap(i, 2 * i + 2);
					i = 2 * i + 2;
				} else {
					isInserted = true;
				}
			}
		}
		return ret;
	}

	/**
	 * From an edge indexed by src, find the child having the least weight and
	 * return it
	 * 
	 * @param src an index of the list edges
	 * @return the index of the child edge with the least weight
	 */
	private int getBestChildPos(int src) {
		int lastIndex = binh.size() - 1;
		if (isLeaf(src)) { // the leaf is a stopping case, then we return a default value
			return Integer.MAX_VALUE;
		} else {
			// To complete
			return Integer.MAX_VALUE;
		}
	}

	private boolean isLeaf(int src) {
		return 2 * src + 1 >= binh.size();
	}

	/**
	 * Swap two edges in the binary heap
	 * 
	 * @param father an index of the list edges
	 * @param child  an index of the list edges
	 */
	private void swap(int father, int child) {
		Triple<A, A, Integer> temp = new Triple<>(binh.get(father).getFirst(), binh.get(father).getSecond(),
				binh.get(father).getThird());
		binh.get(father).setTriple(binh.get(child));
		binh.get(child).setTriple(temp);
	}

	/**
	 * Create the string of the visualisation of a binary heap
	 * 
	 * @return the string of the binary heap
	 */
	public String toString() {
		StringBuilder s = new StringBuilder();
		for (Triple<A, A, Integer> no : binh) {
			s.append(no).append(", ");
		}
		return s.toString();
	}

	private String space(int x) {
		StringBuilder res = new StringBuilder();
		for (int i = 0; i < x; i++) {
			res.append(" ");
		}
		return res.toString();
	}

	/**
	 * Print a nice visualisation of the binary heap as a hierarchy tree
	 * 
	 */
	public void lovelyPrinting() {
		int nodeWidth = this.binh.get(0).toString().length();
		int depth = 1 + (int) (Math.log(this.binh.size()) / Math.log(2));
		int index = 0;

		for (int h = 1; h <= depth; h++) {
			int left = ((int) (Math.pow(2, depth - h - 1))) * nodeWidth - nodeWidth / 2;
			int between = ((int) (Math.pow(2, depth - h)) - 1) * nodeWidth;
			int i = 0;
			System.out.print(space(left));
			while (i < Math.pow(2, h - 1) && index < binh.size()) {
				System.out.print(binh.get(index) + space(between));
				index++;
				i++;
			}
			System.out.println("");
		}
		System.out.println("");
	}

	// ------------------------------------
	// TEST
	// ------------------------------------

	/**
	 * Recursive test to check the validity of the binary heap
	 * 
	 * @return a boolean equal to True if the binary tree is compact from left to
	 *         right
	 * 
	 */
	private boolean test() {
		return this.isEmpty() || testRec(0);
	}

	private boolean testRec(int root) {
		int lastIndex = binh.size() - 1;
		if (isLeaf(root)) {
			return true;
		} else {
			int left = 2 * root + 1;
			int right = 2 * root + 2;
			if (right >= lastIndex) {
				return binh.get(left).getThird() >= binh.get(root).getThird() && testRec(left);
			} else {
				return binh.get(left).getThird() >= binh.get(root).getThird() && testRec(left)
						&& binh.get(right).getThird() >= binh.get(root).getThird() && testRec(right);
			}
		}
	}

	public static void main(String[] args) {
		BinaryHeapEdge<DirectedNode> jarjarBin = new BinaryHeapEdge<>();
		System.out.println(jarjarBin.isEmpty() + "\n");
		int k = 17;
		int min = 1;
		int max = 201;
		while (k > 0) {
			int rand = min + (int) (Math.random() * ((max - min) + 1));
			jarjarBin.insert(new DirectedNode(k), new DirectedNode(k + 30), rand);
			k--;
		}

		System.out.println("\nInsert");
		jarjarBin.lovelyPrinting();
		System.out.println(jarjarBin.test());

		System.out.println("\nRemove");
		jarjarBin.remove();
		jarjarBin.lovelyPrinting();
		System.out.println(jarjarBin.test());

		System.out.println("\nRemove");
		jarjarBin.remove();
		jarjarBin.lovelyPrinting();
		System.out.println(jarjarBin.test());

		System.out.println("\nRemove");
		jarjarBin.remove();
		jarjarBin.lovelyPrinting();
		System.out.println(jarjarBin.test());
	}

}
