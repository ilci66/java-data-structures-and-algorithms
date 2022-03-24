// Checking again and again to prevent errors seems a little unnecessary right now
// but I guess it's a good habit

public class Main<T> implements Iterable<T> {
  public static void main(String[] args) {
	  System.out.println("had to include");
  }
  private int size = 0;
  private Node<T> head = null;
  private Node<T> tail = null;

  // Internal node class to represent data, in python I did it externally
  private static class Node<T> {
    private T data;
    private Node<T> prev, next;

    public Node(T data, Node<T> prev, Node<T> next) {
      this.data = data;
      this.prev = prev;
      this.next = next;
    }

    @Override
    public String toString() {
      return data.toString();
    }
  }

  public void clear() {
    Node<T> trav = head;
    while (trav != null) {
      Node<T> next = trav.next;
      trav.prev = trav.next = null;
      trav.data = null;
      trav = next;
    }
    head = tail = trav = null;
    size = 0;
  }

  public int size() {
    return size;
  }
  public boolean isEmpty() {
    return size() == 0;
  }

  // Constant time O(1)
  public void add(T elem) {
    addLast(elem);
  }

  // Constant time O(1)
  public void addLast(T elem) {
    if (isEmpty()) {
      head = tail = new Node<T>(elem, null, null);
    } else {
      tail.next = new Node<T>(elem, tail, null); // tail becomes the prev of tail
      tail = tail.next;
    }
    size++;
  }

  // Constant time O(1)
  public void addFirst(T elem) {
    if (isEmpty()) {
      head = tail = new Node<T>(elem, null, null);
    } else {
      head.prev = new Node<T>(elem, null, head);
      head = head.prev;
    }
    size++;
  }

  //Constant time O(1)
  public T peekFirst() {
    if (isEmpty()) throw new RuntimeException("Empty list");
    return head.data;
  }

  //Constant time O(1)
  public T peekLast() {
    if (isEmpty()) throw new RuntimeException("Empty list");
    return tail.data;
  }

  //Constant time O(1)
  public T removeFirst() {
    if (isEmpty()) throw new RuntimeException("Empty list");
    
    T data = head.data;
    head = head.next;
    --size;

    if (isEmpty()) tail = null;

    else head.prev = null; // Memory cleanup, remove the unused node, did't do it python

    return data;
  }

  // Constant time O(1)
  public T removeLast() {
    if (isEmpty()) throw new RuntimeException("Empty list");

    T data = tail.data;
    tail = tail.prev;
    --size;

    if (isEmpty()) head = null;

    else tail.next = null;

    return data;
  }

  // Constant time O(1)
  private T remove(Node<T> node) {
	
	// check for head or tail
    if (node.prev == null) return removeFirst();
    if (node.next == null) return removeLast();

    // Make the pointers of adjacent nodes skip over 'node'
    node.next.prev = node.prev;
    node.prev.next = node.next;

    T data = node.data;

    // Memory cleanup, didn't do any cleanups in python though, probably should have
    node.data = null;
    node = node.prev = node.next = null;

    --size;

    return data; // Return the data in the node we just removed
  }

  // Linear time O(n)
  public T removeAt(int index) {
	  
    if (index < 0 || index >= size) {
      throw new IllegalArgumentException();
    }

    int i;
    Node<T> trav;

    // Search from the front of the list, another thing I should have added in my python implementation
    if (index < size / 2) {
      for (i = 0, trav = head; i != index; i++) {
        trav = trav.next;
      }
      // Search from the back of the list
    } else
      for (i = size - 1, trav = tail; i != index; i--) {
        trav = trav.prev;
      }

    return remove(trav);
  }

  // ***************//
  // Remove a particular value in the linked list, O(n)
  public boolean remove(Object obj) {
    Node<T> trav = head;

    // Support searching for null
    if (obj == null) {
      for (trav = head; trav != null; trav = trav.next) {
        if (trav.data == null) {
          remove(trav);
          return true;
        }
      }
      // Search for non null object
    } else {
      for (trav = head; trav != null; trav = trav.next) {
        if (obj.equals(trav.data)) {
          remove(trav);
          return true;
        }
      }
    }
    return false;
  }

  // Find the index of a particular value in the linked list, O(n)
  public int indexOf(Object obj) {
    int index = 0;
    Node<T> trav = head;

    // Support searching for null
    if (obj == null) {
      for (; trav != null; trav = trav.next, index++) {
        if (trav.data == null) {
          return index;
        }
      }
      // Search for non null object
    } else
      for (; trav != null; trav = trav.next, index++) {
        if (obj.equals(trav.data)) {
          return index;
        }
      }

    return -1;
  }

  // Check is a value is contained within the linked list
  public boolean contains(Object obj) {
    return indexOf(obj) != -1;
  }

  @Override
  public java.util.Iterator<T> iterator() {
    return new java.util.Iterator<T>() {
      private Node<T> trav = head;

      @Override
      public boolean hasNext() {
        return trav != null;
      }

      @Override
      public T next() {
        T data = trav.data;
        trav = trav.next;
        return data;
      }

      @Override
      public void remove() {
        throw new UnsupportedOperationException();
      }
    };
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("[ ");
    Node<T> trav = head;
    while (trav != null) {
      sb.append(trav.data + ", ");
      trav = trav.next;
    }
    sb.append(" ]");
    return sb.toString();
  }
}