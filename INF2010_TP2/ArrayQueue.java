

public class ArrayQueue<AnyType> implements Queue<AnyType>
{
	private int size = 0;		//Nombre d'elements dans la file.
	private int startindex = 0;	//Index du premier element de la file
	private AnyType[] table;
   
	private final int RESIZE_FACTOR = 2;
	private final int DEFAULT_SIZE = 10;
	
	@SuppressWarnings("unchecked")
	public ArrayQueue() 
	{
		table = (AnyType[]) new Object[DEFAULT_SIZE];
	}
	
	//Indique si la file est vide
	public boolean empty() 
	{ 
		return size == 0; 
	}
	
	//Retourne la taille de la file
	public int size() 
	{ 
		return size; 
	}
	
	//Retourne l'element en tete de file
	//Retourne null si la file est vide
	//complexité asymptotique: O(1)
	public AnyType peek()
	{
		return table[startindex];
	}
	
	//Retire l'element en tete de file
	//complexité asymptotique: O(1)
	public void pop() throws EmptyQueueException
	{
		if(empty())
		{
			throw new EmptyQueueException();
		}
		//Alternative à la condition : startindex = (startindex + 1) % table.length, mais la condition est moins gourmande qu'un modulo
		if(startindex == table.length - 1)
		{
			startindex = 0;
		}
		else
		{
			++startindex;
		}
		--size;
	}
	
	//Ajoute un element a la fin de la file
	//Double la taille de la file si necessaire (utiliser la fonction resize definie plus bas)
	//complexité asymptotique: O(1) ( O(N) lorsqu'un redimensionnement est necessaire )
	public void push(AnyType item)
	{
		if(size == table.length)
		{
			resize(RESIZE_FACTOR);
		}
		
		table[(startindex + size) % table.length] = item;
		++size;
	}
   
	//Redimensionne la file. La capacite est multipliee par un facteur de resizeFactor.
	//Replace les elements de la file au debut du tableau
	//complexité asymptotique: O(N)
	@SuppressWarnings("unchecked")
	private void resize(int resizeFactor)
	{
		//A completer
		AnyType[] newTable = (AnyType[]) new Object[table.length * resizeFactor];
		for(int i = 0; i < size; ++i)
		{
			newTable[i] = table[(startindex + i) % table.length];
		}
		table = newTable;
		startindex = 0;
	}
}

