import java.util.ArrayList;
import java.util.Random;

import javax.swing.text.html.HTMLDocument.Iterator;

import com.sun.xml.internal.bind.AnyTypeAdapter;

public class QuadraticSpacePerfectHashing<AnyType> 
{
   static int p = 46337;
   
   int a, b;
   AnyType[] items;
      
   QuadraticSpacePerfectHashing()
   {
      a=b=0; items = null;
   }
   
   QuadraticSpacePerfectHashing(ArrayList<AnyType> array)
   {
      AllocateMemory(array);
   }
   
   public void SetArray(ArrayList<AnyType> array)
   {
      AllocateMemory(array);
   }
   
   public int Size()
   {
      if( items == null ) return 0;
      
      return items.length;
   }
   
   public boolean contains(AnyType x )
   {
      if( Size() == 0 ) return false;
      
      if( a == 0 ) return ( items[0].equals(x) );
      int m = items.length;
      int index = ( ( a*x.hashCode() + b ) % p ) % m;
      
      index = ( index < 0 ? index + m : index );
      
      return ( ( items[index] != null ) &&
             ( items[index].equals(x) ) );
   }
   
   
   @SuppressWarnings("unchecked")
   private void AllocateMemory(ArrayList<AnyType> array)
   {
      Random generator = new Random( System.nanoTime() );
      
      if(array == null || array.size() == 0)
      {
         // A completer
    	 items = (AnyType[]) new Object[0];
         return;
      }
      if(array.size() == 1)
      {
         a = b = 0;
         
         // A completer
    	 items = (AnyType[]) new Object[1];
    	 items[0] = array.get(0);
         return;
      }
      
      do
      {
         items = null;
         
         // A completer
         a = generator.nextInt(p - 1) + 1;
         b = generator.nextInt(p);
         
      }
      while( collisionExists( array ) );
   }
   
   @SuppressWarnings("unchecked")
   private boolean collisionExists(ArrayList<AnyType> array)
   {
      // A completer
	   items = (AnyType[]) new Object[(int)Math.pow(array.size(),2)];
	   
	   for(AnyType item:array){
		   
		   int index = ( ( a * item.hashCode() + b ) % p ) % items.length;
		   
		   index = ( index < 0 ? index + items.length : index );
		   
		   items[index] = item;
	   }
	   
      return false;
   }
}
