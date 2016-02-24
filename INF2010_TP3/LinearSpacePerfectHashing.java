import java.util.Random;
import java.util.ArrayList;

public class LinearSpacePerfectHashing<AnyType>
{
   static int p = 46337;
   
   QuadraticSpacePerfectHashing<AnyType>[] data;
   int a, b;
   
   LinearSpacePerfectHashing()
   {
      a=b=0; data = null;
   }
   
   LinearSpacePerfectHashing(ArrayList<AnyType> array)
   {
      AllocateMemory(array);
   }
   
   public void SetArray(ArrayList<AnyType> array)
   {
      AllocateMemory(array);
   }
      
   @SuppressWarnings("unchecked")
   private void AllocateMemory(ArrayList<AnyType> array)
   {
      Random generator = new Random( System.nanoTime() );
      
      if(array == null || array.size() == 0)
      {
         data =  (QuadraticSpacePerfectHashing<AnyType>[]) new QuadraticSpacePerfectHashing[0];
         return;
      }
      if(array.size() == 1)
      {
         a = b = 0;
         data = (QuadraticSpacePerfectHashing<AnyType>[]) new QuadraticSpacePerfectHashing[1];
         data[0] = new QuadraticSpacePerfectHashing<AnyType>(array);
         return;
      }
      
      //On crée un tableau qui contiendra des ArrayList qui eux contiendront les différents éléments qui ont le même index.
      int nbElements = array.size();
      data = (QuadraticSpacePerfectHashing<AnyType>[]) new QuadraticSpacePerfectHashing[nbElements];
      ArrayList<ArrayList<AnyType>> tempArray = new ArrayList<ArrayList<AnyType>>(nbElements);
      for(int i = 0; i < nbElements; ++i)
      {
    	  tempArray.add(new ArrayList<AnyType>());
      }
      
      //On calcule l'index pour chacun des éléments et on les ajoute dans notre tableau
      a = generator.nextInt(p - 1) + 1;
      b = generator.nextInt(p);
      for(AnyType element : array)
      {
    	  int index = ( ( a*element.hashCode() + b ) % p ) % data.length;
          index = ( index < 0 ? index + data.length : index );
          tempArray.get(index).add(element);
      }
      
      //On crée un QuadraticSpacePerfectHashing pour chacun de nos ArrayList
      for(int i = 0; i < nbElements; ++i)
      {
    	  data[i] = new QuadraticSpacePerfectHashing<AnyType>(tempArray.get(i));
      }
   }
   
   public int Size()
   {
      if( data == null ) return 0;
      
      int size = 0;
      for(int i=0; i<data.length; ++i)
      {
         size += (data[i] == null ? 1 : data[i].Size());
      }
      return size;
   }
   
   public boolean contains(AnyType x )
   {
      if( Size() == 0 ) return false;
      
      int m = data.length;
      
      int index = ( ( a*x.hashCode() + b ) % p ) % m;
      
      index = ( index < 0 ? index + m : index );
      
      return ((data[index] != null) && (data[index].contains(x)));
   }
}
