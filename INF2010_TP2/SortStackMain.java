import java.util.Random;
import java.util.Stack;


public class SortStackMain 
{
	static final int COUNT = 30;
	static final int MAX_VALUE = 1000;
	
	public static void main(String[] args) 
	{
		boolean sortIsGood = true;
		
		Random generator = new Random( System.nanoTime() );
		Stack<Integer> stack = new Stack<Integer>();
		
		for(int i = 0; i < COUNT; i++)
			stack.push(generator.nextInt(MAX_VALUE));
		
		stack = sortStack(stack);
		
		boolean countIsGood = size(stack) == COUNT;
			
		int tmp = stack.pop();
		while(!stack.isEmpty())
		{
			System.out.print(tmp + ", ");
			
			if(tmp > stack.peek())
				sortIsGood = false;
			
			tmp = stack.pop();
		}
		System.out.println(tmp);
		
		if(!countIsGood)
			System.out.println("Erreur: il manque des elements dans la pile");
		else if(!sortIsGood)
			System.out.println("Erreur: le trie a echoue");
		else
			System.out.println("It's all good");
	}
    
	private static int size(Stack<Integer> stack) {
		//A completer
		int size = 0;
		Stack<Integer> tempStack  = new Stack<Integer>(); 
		
		while(!stack.empty()){
			tempStack.push(stack.pop());
			size++;
		}
		
		while(!tempStack.empty()){
			stack.push(tempStack.pop());
		}

		return size;
    }
    
	static Stack<Integer> sortStack(Stack<Integer> stack)
	{
		// Setup useful variables
		int pivot;
		int value;
		int size = 0;
		Stack<Integer> tempStack  = new Stack<Integer>();
		
		// Initial sort and saved to tempStack
		
		// Set the first value of the stack as pivot 
		pivot = stack.pop();
		
		// Sort through all the stack with the pivot.
		// When a value is larger than the pivot, push the pivot to the stack
		// and replace it by the said value.
		while(!stack.empty()){
			value = stack.pop();
			if(value <= pivot)
				tempStack.push(value);
			else{
				tempStack.push(pivot);
				pivot = value;
			}
			size++;
		}
		// Add pivot back to the stack
		tempStack.push(pivot);
		size++;
		
		// Re-stack tempStack back to the original stack by sorting it again
		
		// Set pivot.
		pivot = tempStack.pop();
		
		// Sort tempStack the same way as previous with the only difference being
		// that the pivot is replaced when a smaller value is encountered. 
		while(!tempStack.empty()){
			value = tempStack.pop();
			if(value >= pivot)
				stack.push(value);
			else{
				stack.push(pivot);
				pivot = value;
				size--;
			}
		}
		// Add pivot back to the stack
		stack.push(pivot);
		size--;
		
		// If he pivot was replaced as often as the size of the stack,
		// the stack is sorted.
		if(size != 0)
			return sortStack(stack);
		else
			return stack;
	}
}
