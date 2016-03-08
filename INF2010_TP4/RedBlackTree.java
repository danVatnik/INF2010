import java.util.LinkedList;
import java.util.Queue;

public class RedBlackTree<T extends Comparable<? super T> > 
{
   private RBNode<T> root;  // Racine de l'arbre
   
   enum ChildType{ left, right }
   
   public RedBlackTree()
   { 
      root = null;
   }
   
   public void printFancyTree()
   {
      printFancyTree( root, "", ChildType.right);
   }
   
   private void printFancyTree( RBNode<T> t, String prefix, ChildType myChildType)
   {
      System.out.print( prefix + "|__"); // un | et trois _
      
      if( t != null )
      {
         boolean isLeaf = (t.isNil()) || ( t.leftChild.isNil() && t.rightChild.isNil() );
         
         System.out.println( t );
         String _prefix = prefix;
         
         if( myChildType == ChildType.left )
            _prefix += "|   "; // un | et trois espaces
         else
            _prefix += "   " ; // trois espaces
         
         if( !isLeaf )
         {
            printFancyTree( t.leftChild, _prefix, ChildType.left );
            printFancyTree( t.rightChild, _prefix, ChildType.right );
         }
      }
      else
         System.out.print("null\n");
   }
   
   public T find(int key)
   {
      return find(root, key);
   }
   
   private T find(RBNode<T> current, int key)
   {
	   int currentKey = current.value.hashCode();
	   
	   if(currentKey == key)
		   return current.value;
	   
	   if(current.isBlack()){
		   if(key < currentKey)
			   return find(current.leftChild, key);
		   else
			   return find(current.rightChild, key);
	   }
	   
	   return null;
   }
   
   public void insert(T val)
   {
      insertNode( new RBNode<T>( val ) );
   }
   
   private void insertNode( RBNode<T> newNode )
   { 
      if (root == null)  // Si arbre vide
         root = newNode;
      else
      {
         RBNode<T> position = root; // On se place a la racine
         
         while( true ) // on insere le noeud (ABR standard)
         {
            int newKey = newNode.value.hashCode();
            int posKey = position.value.hashCode();
            
            if ( newKey < posKey ) 
            {
               if ( position.leftChild.isNil() ) 
               {
                  position.leftChild = newNode;
                  newNode.parent = position;
                  break;
               } 
                  else 
                  position = position.leftChild;
            } 
            else if ( newKey > posKey ) 
            {
               if ( position.rightChild.isNil() )
               {
                  position.rightChild = newNode;
                  newNode.parent = position;
                  break;
               }
               else 
                  position = position.rightChild;
            }
            else // pas de doublons
               return;
         }
      }
      
      insertionCases( newNode );
   }

   private void insertionCases( RBNode<T> X )
   {
      if(X != null)
      {
    	  insertionCase1( X );
      }
   }
   
   private void insertionCase1 ( RBNode<T> X )
   {
      if(X.parent == null)
      {
    	  X.setToBlack();
      }
      else
      {
    	  insertionCase2( X ); 
      }
   }

   private void insertionCase2( RBNode<T> X )
   {
      if(X.parent.isRed())
      {
    	  insertionCase3( X );
      }
      return;
   }

   private void insertionCase3( RBNode<T> X )
   {
	  RBNode<T> uncle = X.uncle();
      if(uncle.isRed())
      {
    	  X.parent.setToBlack();
    	  uncle.setToBlack();
    	  X.grandParent().isRed();
    	  this.insertionCases(X.grandParent());
      }
      else
      {
    	  insertionCase4( X ); 
      }
   }

   private void insertionCase4( RBNode<T> X )
   {
      if(X.parent.leftChild == X && X.grandParent().rightChild == X.parent)
      {
    	  this.rotateRight(X.parent);
    	  insertionCase5( X.rightChild );
      }
      else if(X.parent.rightChild == X && X.grandParent().leftChild == X.parent)
      {
    	  this.rotateLeft(X.parent);
    	  insertionCase5(X.leftChild);
      }
      else
      {
    	  insertionCase5( X );
      }
   }

   private void insertionCase5( RBNode<T> X )
   {
	   RBNode<T> gParent = X.grandParent();
	   if(X.parent.rightChild == X && gParent.rightChild == X.parent)
       {
		   X.parent.ToggleColor();
		   gParent.ToggleColor();
    	   this.rotateLeft(gParent);
       }
       else if(X.parent.leftChild == X && gParent.leftChild == X.parent)
       {
    	   X.parent.ToggleColor();
		   gParent.ToggleColor();
    	   this.rotateRight(gParent);
       }
      return;
   }
   
   private void rotateLeft( RBNode<T> X )
   {
	   RBNode<T> parent = X.parent;
       RBNode<T> temp = X;
       RBNode<T> child = X.rightChild;
       RBNode<T> gChild = X.rightChild.leftChild;
       
       if(parent.rightChild == temp)
       {
     	  parent.rightChild = child;
       }
       else
       {
     	  parent.leftChild = child;
       }
       child.parent = parent;
  
       child.leftChild = temp;
       temp.parent = child;
       
       temp.rightChild = gChild;
       gChild.parent = temp;
       
      return; 
   }
   
   private void rotateRight( RBNode<T> X )
   {
       RBNode<T> parent = X.parent;
       RBNode<T> temp = X;
       RBNode<T> child = X.leftChild;
       RBNode<T> gChild = X.leftChild.rightChild;
       
       if(parent.rightChild == temp)
       {
     	  parent.rightChild = child;
       }
       else
       {
     	  parent.leftChild = child;
       }
       child.parent = parent;
  
       child.rightChild = temp;
       temp.parent = child;
       
       temp.leftChild = gChild;
       gChild.parent = temp;
      
       return; 
   }

   public void printTreePreOrder()
   {
      if(root == null)
         System.out.println( "Empty tree" );
      else
      {
         System.out.print( "PreOrdre ( ");
         printTreePreOrder( root );
         System.out.println( " )");
      }
      return;
   }
   
   private void printTreePreOrder( RBNode<T> P )
   {
	  if(!P.isNil())
	  {
		  System.out.print("{"+P.toString()+"}, ");
		  printTreePreOrder(P.leftChild);
		  printTreePreOrder(P.rightChild);
	  }
      return; 
   }
   
   public void printTreePostOrder()
   {
      if(root == null)
         System.out.println( "Empty tree" );
      else
      {
         System.out.print( "PostOrdre ( ");
         printTreePostOrder( root );
         System.out.println( ")");
      }
      return;
   }
  
   private void printTreePostOrder( RBNode<T> P )
   {
	  if(!P.isNil())
	  {
		  printTreePostOrder(P.leftChild);
		  printTreePostOrder(P.rightChild);
		  System.out.print("{"+P.toString()+"}, ");
	  }
      return; 
   }
   

   public void printTreeAscendingOrder()
   {
      if(root == null)
         System.out.println( "Empty tree" );
      else
      {
         System.out.print( "AscendingOrdre ( ");
         printTreeAscendingOrder( root );
         System.out.println( " )");
      }
      return;
   }
  
   private void printTreeAscendingOrder( RBNode<T> P )
   {
	  if(!P.isNil())
	  {
		  printTreeAscendingOrder(P.leftChild);
		  System.out.print("{"+P.toString()+"}, ");
		  printTreeAscendingOrder(P.rightChild);
	  }
      return; 
   }
   
   
   public void printTreeDescendingOrder()
   {
      if(root == null)
         System.out.println( "Empty tree" );
      else
      {
         System.out.print( "DescendingOrdre ( ");
         printTreeDescendingOrder( root );
         System.out.println( " )");
      }
      return;
   }

   private void printTreeDescendingOrder( RBNode<T> P )
   {
	   if(!P.isNil())
	   {
		   printTreeDescendingOrder(P.rightChild);
		   System.out.print("{"+P.toString()+"}, ");
		   printTreeDescendingOrder(P.leftChild);
	   }
	   return; 
   }
   
   public void printTreeLevelOrder()
   {
      if(root == null)
         System.out.println( "Empty tree" );
      else
      {
         System.out.print( "LevelOrdre ( ");
         
         Queue<RBNode<T>> q = new LinkedList<RBNode<T>>();
         
         q.add(root);
         
         //  À COMPLÉTER
         
         RBNode<T> current = null;
         while(!q.isEmpty()){
        	 current = q.remove();
        	 
        	 if(current != root)
        		 System.out.print(", ");
        	 	 
        	System.out.print("{"+current.toString()+"}");
        	 
        	 if(!current.leftChild.isNil())
        		 q.add(current.leftChild);
        	 if(!current.rightChild.isNil())
        		 q.add(current.rightChild); 
        	 
         }
         System.out.println( " )");
      }
      return;
   }
   
   private static class RBNode<T extends Comparable<? super T> > 
   {
      enum RB_COLOR { BLACK, RED }  // Couleur possible
      
      RBNode<T>  parent;      // Noeud parent
      RBNode<T>  leftChild;   // Feuille gauche
      RBNode<T>  rightChild;  // Feuille droite
      RB_COLOR   color;       // Couleur du noeud
      T          value;       // Valeur du noeud
      
      // Constructeur (NIL)   
      RBNode() { setToBlack(); }
      
      // Constructeur (feuille)   
      RBNode(T val)
      {
         setToRed();
         value = val;
         leftChild = new RBNode<T>();
         leftChild.parent = this;
         rightChild = new RBNode<T>();
         rightChild.parent = this;
      }
      
      RBNode<T> grandParent()
      {
         // À COMPLÉTER
    	  RBNode<T> gParent = null;
    	  
    	  if(this.parent != null)
    		  gParent = this.parent.parent;
    	 
    	  return gParent;
      }
      
      RBNode<T> uncle()
      {
         // À COMPLÉTER
    	  
    	  RBNode<T> gParent = this.grandParent();
    	  RBNode<T> uncle = null;
    	  
    	  if(gParent != null){
    		  if(gParent.leftChild == this.parent)
    			  uncle = gParent.rightChild;
    		  else
    			  uncle = gParent.leftChild;
    	  }
    	  
    	  return uncle;
      }
      
      RBNode<T> sibling()
      {
         // À COMPLÉTER
    	  RBNode<T> sibling = null;
    	  
    	  if(this.parent.leftChild == this)
    		  sibling = this.parent.rightChild;
    	  else
    		  sibling = this.parent.leftChild;
    	  
    	  return sibling;
      }
      
      public void ToggleColor()
      {
    	  if(this.isBlack())
    	  {
    		  this.setToRed();
    	  }
    	  else
    	  {
    		  this.setToBlack();
    	  }
      }
      
      
      public String toString()
      {
         return value + " (" + (color == RB_COLOR.BLACK ? "black" : "red") + ")"; 
      }
      
      boolean isBlack(){ return (color == RB_COLOR.BLACK); }
      boolean isRed(){ return (color == RB_COLOR.RED); }
      boolean isNil(){ return (leftChild == null) && (rightChild == null); }
      
      void setToBlack(){ color = RB_COLOR.BLACK; }
      void setToRed(){ color = RB_COLOR.RED; }
   }
}
