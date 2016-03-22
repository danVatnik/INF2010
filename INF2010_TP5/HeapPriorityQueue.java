import java.util.ArrayList;
import java.util.Hashtable;
import java.util.NoSuchElementException;

public class HeapPriorityQueue<AnyType> implements PriorityQueue<AnyType>{

    private static final int DEFAULT_CAPACITY = 20;

    private int currentSize;              // nombre d'éléments présents
    private PQEntry<AnyType>[] items;     // tableau contenant les éléments ordonnés en tas
    Hashtable<AnyType, Integer> indexMap; // Mappe associant à chaque élément Anytype un entier 
                                          // (sa position dans items)
    
    /**
     * Méthodes déjà implémentées
     */
    public HeapPriorityQueue(){
        initialize();
    }

    public int size(){
        return currentSize;
    }

    public void clear(){
        initialize();
    }

    public boolean isEmpty( ){
        return currentSize == 0;
    }

    public boolean contains(AnyType x){
        return indexMap.containsKey(x);
    }

    @SuppressWarnings("unchecked")
    private void enlargeArray( int newSize )
    {
        if(newSize <= (currentSize+1)) return;

        PQEntry<AnyType> [] old = items;
        items = (PQEntry<AnyType>[]) new PQEntry[ newSize ];

        for( int i = 0; i <= old.length; i++ )
            items[ i ] = old[ i ];        
    }

    private void initialize(){
        initialize( DEFAULT_CAPACITY );
    }
    
    @SuppressWarnings("unchecked")
    private void initialize(int capacity){
      if(capacity < 0)
        capacity = DEFAULT_CAPACITY;
        currentSize = 0;
        items = (PQEntry<AnyType>[]) new PQEntry[ capacity ];
        indexMap = new Hashtable<AnyType, Integer>( capacity );
    }

    public String toString(){
        String output = "";

        for(int i=1; i<=currentSize; i++)
            output += items[i] + ((i!=currentSize) ? ", " : "");

        return output;
    }
    
    /**
     * Méthodes à implémenter
     */
    
    // Commentez la ligne de add1 et décommenter la ligne de add2 pour l'exercice 5
    public boolean add(AnyType x, int priority) throws NullPointerException, IllegalArgumentException {
        return add1(x, priority); // Exercice 1
        //return add2(x, priority); // Exercice 5
    }
    
    /**
     * Exercice 1
     */
    private boolean add1(AnyType x, int priority) throws NullPointerException, IllegalArgumentException {
    	
    	if(priority < 0)
    		throw new IllegalArgumentException();
    	
    	if(x == null)
    		throw new NullPointerException();
    	
    	if(currentSize == items.length - 1)
    		enlargeArray(items.length * 2 + 1);
    	
    	int hole = ++currentSize;
    	
    	for( ; hole > 1 && priority < items[hole/2].priority; hole /= 2){
    		items[hole] = items[hole/2];
    	}
    	
    	items[hole] = new PQEntry<AnyType>(x, priority);
    	
        return true;
    }

    // Commentez la ligne de poll1 et décommenter la ligne de poll2 pour l'exercice 6
    public AnyType poll(){ 
        return poll1(); // Exercice 2
        // return poll2(); // Exercice 6
    }

    /**
     * Exercice 2
     */
    private AnyType poll1(){ 
        
    	if( isEmpty( ) )
    		return null;
    	
		AnyType minItem = items[1].value;
		items[ 1 ] = items[ currentSize--];
		percolateDown1( 1 );
		
		return minItem;
    	
    }

    private void percolateDown1( int hole ){
         
    	int child;
    	PQEntry<AnyType> tmp = items[ hole ];
    	
    	for( ; hole * 2 <= currentSize; hole = child ){
    		child = hole * 2; //Considerer fils de gauche
    		
    		if( child != currentSize && // il y a deux fils
				items[ child + 1 ].priority < items[ child ].priority  )//et fils droit<fils gauche
    			
    			child++; //Considérer fils droit
    		
    		if( items[ child ].priority < tmp.priority )//fils considéré< élément à percoler
    			items[ hole ] = items[ child ];//Remonter le fils courrent de un niveau
    		else
    			break; //sortir de la boucle. L’élément à percoler sera inséré à position hole
    	}
    	items[ hole ] = tmp;// Insérer l’élément à percoler à la position hole
    }
    
    // Commentez la ligne de buildHeap1 et décommenter la ligne de buildHeap2 pour l'exercice 7
    public HeapPriorityQueue( AnyType[] items, int[] priorities )
    throws IllegalArgumentException, NullPointerException{
        buildHeap1( items, priorities ); // Exercice 3
        // buildHeap2( items, priorities ); // Exercice 7
    }
    
    /**
     * Exercice 3
     */
    private void buildHeap1( AnyType[] items, int[] priorities ) 
    throws IllegalArgumentException, NullPointerException{
    	
    	initialize(priorities.length + 1);
    	
    	for(int i = 0; i < priorities.length; i++){
    		
    		if(items[i] == null)
    			throw new NullPointerException();
    		
			if(priorities[i] < 0)
	    		throw new IllegalArgumentException();
    	    
			this.items[i + 1] = new PQEntry<AnyType>(items[i], priorities[i]);
    	    currentSize ++;		
    	}
    	
    	for( int i = currentSize / 2; i > 0; i--)
    		percolateDown1( i );
    	
    }

    
    /**
     * Exercice 4
     */
    public ArrayList<AnyType> getMax() {
        
    	int max = 0;
    	
    	ArrayList<AnyType> maxList = new ArrayList<AnyType>();
    	
    	for( int i = (currentSize + 1) / 2; i <= currentSize; i++){
    		
    		if(items[i].priority > max)
    			max = items[i].priority;
    	}
    	
    	for(int i = 1; i <= currentSize; i++){
    		
    		if(items[i].priority == max)
    			maxList.add(items[i].value);
    		
    	}
    	
        return maxList;
    }
    
    /**
     * Exercice 5
     */
    private boolean add2(AnyType x, int priority) throws IllegalArgumentException{
    	// completer
        return true;
    }
    
    /**
     * Exercice 6
     */
    private AnyType poll2(){ 
        // completer
        return null;
    }

    private void percolateDown2( int hole ){
        // completer
    }
    
    /**
     * Exercice 7
     */
    private void buildHeap2( AnyType[] items, int[] priorities ) 
    throws IllegalArgumentException, NullPointerException{
        // completer
    }

    /**
     * Exercice 8
     */
    public void updatePriority(AnyType x, int priority){
        // completer      
    }
}
