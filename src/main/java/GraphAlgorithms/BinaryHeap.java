package GraphAlgorithms;

import java.util.Arrays;

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
    	
    	while(!isInserted && !isLeaf(i)) {
    		if (nodes[2*i+1] < nodes[i] && nodes[2*i+1] <= nodes[2*i+2]) {
    			swap(i, 2*i+1);
    			i = 2*i+1;
    		}
    		else if (nodes[2*i+2] < nodes[i] && nodes[2*i+2] <= nodes[2*i+1]) {
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

    private String space(int x) {
		StringBuilder res = new StringBuilder();
		for (int i=0; i<x; i++) {
			res.append(" ");
		}
		return res.toString();
	}

    /**
	 * Print a nice visualisation of the binary heap as a hierarchy tree
	 * 
	 */	
	public void lovelyPrinting(){
		int nodeWidth = String.valueOf(nodes[0]).length();
		int depth = 1+(int)(Math.log(nodes.length)/Math.log(2));
		int index=0;
		
		for(int h = 1; h<=depth; h++){
			int left = ((int) (Math.pow(2, depth-h-1)))*nodeWidth - nodeWidth/2;
			int between = ((int) (Math.pow(2, depth-h))-1)*nodeWidth;
			int i =0;
			System.out.print(space(left));
			while(i<Math.pow(2, h-1) && index<pos){
				System.out.print(nodes[index] + space(between));
				index++;
				i++;
			}
			System.out.println("");
		}
		System.out.println("");
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
        
        jarjarBin.lovelyPrinting();
        System.out.println(jarjarBin.test());
        
        System.out.println("\nSuppression du plus petit : " + jarjarBin.remove());
        jarjarBin.lovelyPrinting();
        System.out.println(jarjarBin.test());   
    }
    
    // Q6 : Comment utiliseriez-vous la structure de tas binaire dans votre mise en forme de l'algorithme de Primm ? 
    // Quel opération faut-il ajouter dans la structure de tas binaire ? Quel gain en terme de complexité par rapport à votre algorithme ?
    
    /*
     * On utiliserait le tas binaire pour trouver l'arête avec le poids le plus léger parmi les arêtes incidentes aux noeuds déjà parcourus.
     * On teste si l'arête la plus légère est incidente à un noeud pas encore parcouru.
     * Si c'est le cas, on ajoute ce noeud à la liste des noeuds parcouru, on ajoute ses arêtes incidentes au tas et on ajoute le poids
     * de l'arête sélectionnée au poids total.
     * Sinon, on passe à l'arête suivante.
     *
     * Il n'y a besoin que de retirer l'élément le plus léger et ajouter des éléments au tas, donc pas besoin d'ajouter d'opération.
     * 
     * Par rapport à un algorithme de Primm "brut" de complexité O(n²), cet algorithme proposerait une complexité en O(n*log m) :
     * Pour chaque noeud (n), on regarde l'arête appropriée dans le tas binaire (log m). 
     */
}


