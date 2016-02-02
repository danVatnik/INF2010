
public class LinkedListQueue<AnyType> implements Queue<AnyType>
{	
	// Un noeud de la file
	@SuppressWarnings("hiding")
	private class Node<AnyType> 
	{
		private AnyType data;
		private Node<AnyType> next;
		
		public Node(AnyType data, Node<AnyType> next) 
		{
			this.data = data;
			this.next = next;
		}

		public void setNext(Node<AnyType> next) 
		{
			this.next = next;
		}
		
		public Node<AnyType> getNext() 
		{
			return next;
		}
		
		public AnyType getData() 
		{
			return data;
		}
	}
   
	private int size = 0;		//Nombre d'elements dans la file.
	private Node<AnyType> last;	//Dernier element de la liste
	private Node<AnyType> first; //Premier element de la liste
	
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
		AnyType element;
		if(empty())
		{
			element = null;
		}
		else
		{
			element = last.getData();
		}
		return element;
	}
	
	//Retire l'element en tete de file
	//complexité asymptotique: O(1)
	public void pop() throws EmptyQueueException
	{
		if(empty())
		{
			throw new EmptyQueueException();
		}
		last = last.getNext();
		--size;
	}
	
	//Ajoute un element a la fin de la file
	//complexité asymptotique: O(1)
	public void push(AnyType item)
	{
		if(empty())
		{
			last = new Node<AnyType>(item, null);
			first = last;
		}
		else
		{
			first.setNext(new Node<AnyType>(item, null));
			first = first.getNext();
		}
		++size;
	}  
}
