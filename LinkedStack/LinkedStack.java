package LinkedStack;

/**
 * Implementation of the stack by means of a singly linked list.
 */
public class LinkedStack {
	private class Node {
		// Instance variables:
		private Object data;
		private Node next;
		/** Creates a node with the given element and next node. */
		public Node(Object e, Node n) {
			this.data = e;
			this.next = n;
		}
	}//end of node class
	
  protected Node top;// reference to the head node
  protected int size;// number of elements in the stack

  /** Creates an empty stack. */
  public LinkedStack() {	// constructs an empty stack
    this.top = null;
    this.size = 0;
  }
  public int size() { return size; }
  
  public boolean isEmpty() {
    if (top == null) return true;
    return false;
  }
  public void push(Object elem) {
    Node v = new Node(elem, top);	// create and link-in a new node, new node's next gets top; 
    this.top = v;                        // equivalent to addFirst operation on linked list.
    this.size++;
  }
  public Object top() throws EmptyStackException {
    if (isEmpty()) 
    		throw new EmptyStackException("Stack is empty.");
    return top.data;
  }
  public Object pop() throws EmptyStackException {
    if (isEmpty()) 
    	throw new EmptyStackException("Stack is empty.");
    
    Object temp = top.data;
    this.top = top.next;	// equivalent to removeFirst operation on linked list.
    this.size--;
    return temp;
  }

 /**
  * Returns a string representation of the stack as a list of elements,
  * with the top element at the end: [ ... , prev, top ].
  * This method runs in O(n) time, where n is the size of the stack.
  * @return textual representation of the stack.
  */
  public String toString() {
    String s;
    Node cur = null;
    s = "[";
    int n = size();
    if (n > 0) {
      cur = top;
      s += cur.data;
    }
    if (n > 1)
      for (int i = 1; i <= n-1; i++) {
	cur = cur.next;
	s += ", " + cur.data;
      }
    s += "]";
    return s;
  }

 /**
  * Prints information about an operation and the stack.
  * @param op operation performed
  * @param element element returned by the operation
  * @return information about the operation performed, the element
  * returned by the operation and the content of the stack after 
  * the operation.
  */ 
  public static void status(LinkedStack S, String op, Object element) {
    System.out.println("---------------------------------");
    System.out.println(op);
    System.out.println("Returned: " + element);
    String emptyStatus;
    if (S.isEmpty())
      emptyStatus = "empty";
    else
      emptyStatus = "not empty";
    System.out.println("size = " + S.size() + ", " + emptyStatus);
    System.out.println("Stack: " + S);
  }

 /**
  * Test program that performs a series of operations on on a stack and
  * prints the operation performed, the returned element and the
  * content of the stack after each operation.
  */
  public static void main(String[] args) {
    Object o;
    LinkedStack A = new LinkedStack();
    status (A, "New empty stack", null);
    A.push(5);
    status (A, "push(5)", null);
    A.push(3);
    status (A, "push(3)", null);
    A.push(7);
    status (A, "push(7)", null);
    o = A.pop();
    status (A, "pop()", o);
    A.push(9);
    status (A, "push(9)", null);
    o = A.pop();
    status (A, "pop()", o);
    o = A.top();      //returns the last inserted element without removing it from stack.
    status (A, "top()", o);
  }
}
