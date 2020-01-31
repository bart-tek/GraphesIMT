package GraphAlgorithms;


public class BinaryHeap {

    private int[] nodes;
    private int pos;

    public BinaryHeap() {
        this.nodes = new int[32];
        for (int i = 0; i < nodes.length; i++) {
            this.nodes[i] = Integer.MAX_VALUE;
        }
        this.pos = 0;
    }

    public void resize() {
        int[] tab = new int[this.nodes.length + 32];
        for (int i = 0; i < nodes.length; i++) {
            tab[i] = Integer.MAX_VALUE;
        }
        System.arraycopy(this.nodes, 0, tab, 0, this.nodes.length);
        this.nodes = tab;
    }

    public boolean isEmpty() {
        return pos == 0;
    }
    
    /** Dans le pire des cas, on doit remonter tout l'arbre pour insérer l'élément ce qui a une complexité en O(log2 n).
     * Si il y a besoin de resize le tableau, on passe en O(n) à cause de la copie du tableau.
     *
     * @param element
     */
    public void insert(int element) {
    	
    	boolean isInserted = false;

    	if (pos < nodes.length) {
    		nodes[pos] = element;
    	}
    	else {
    		resize();
    		nodes[pos] = element;
    	}
    	
    	int i = pos;
    	
    	while (!isInserted && ((i-1) / 2) >= 0) {
    		if (nodes[(i-1)/2] > element) {
    			swap(i, (i-1)/2);
    			i = (i-1)/2;
    		}
    		else {
    			isInserted = true;
    		}
    	}
    	
    	pos++;
    }

    /**
     * On parcourt une seule branche du tableau pour remettre la valeur du dernier noeud au bon endroit, donc c'est du O(log2 n).
     * @return
     */
    public int remove() {

    	int ret = nodes[0];
    	nodes[0] = nodes[pos - 1];
    	pos--;
    	
    	boolean isInserted = false;
    	int i = 0;
    	
    	while(!isInserted && i < pos) {
    		if (nodes[2*i+1] < nodes[i]) {
    			swap(i, 2*i+1);
    			i = 2*i+1;
    		}
    		else if (nodes[2*i+2] < nodes[i]) {
    			swap(i, 2*i+2);
    			i = 2*i+2;
    		} 
    		else {
    			isInserted = true;
    		}
    	}
    	return ret;
    }

    private int getBestChildPos(int src) {
        if (isLeaf(src)) { // the leaf is a stopping case, then we return a default value
            return Integer.MAX_VALUE;
        } else {
        	// A completer
        	return Integer.MAX_VALUE;
        }
    }

    
    /**
	 * Test if the node is a leaf in the binary heap
	 * 
	 * @returns true if it's a leaf or false else
	 * 
	 */	
    private boolean isLeaf(int src) {
    	return 2*src+1 > pos;
    }

    private void swap(int father, int child) {
        int temp = nodes[father];
        nodes[father] = nodes[child];
        nodes[child] = temp;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < pos; i++) {
            s.append(nodes[i]).append(", ");
        }
        return s.toString();
    }
    
    /**
	 * Recursive test to check the validity of the binary heap
	 * 
	 * @returns a boolean equal to True if the binary tree is compact from left to right
	 * 
	 */
    public boolean test() {
        return this.isEmpty() || testRec(0);
    }

    private boolean testRec(int root) {
        if (isLeaf(root)) {
            return true;
        } else {
            int left = 2 * root + 1;
            int right = 2 * root + 2;
            if (right >= pos) {
                return nodes[left] >= nodes[root] && testRec(left);
            } else {
                return nodes[left] >= nodes[root] && testRec(left) && nodes[right] >= nodes[root] && testRec(right);
            }
        }
    }

    public static void main(String[] args) {
        BinaryHeap jarjarBin = new BinaryHeap();
        System.out.println(jarjarBin.isEmpty()+"\n");
        int k = 20;
        int m = k;
        int min = 2;
        int max = 20;
        while (k > 0) {
            int rand = min + (int) (Math.random() * ((max - min) + 1));
            System.out.print("insert " + rand + " | ");
            jarjarBin.insert(rand);            
            k--;
        }
     // A completer
        System.out.println("\n" + jarjarBin);
        System.out.println(jarjarBin.test());
    }

}
