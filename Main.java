import java.util.Iterator;

public class Main <T> implements Iterable <T> {
	public static void main(String[] args) {
		System.out.println("create the dynamic array here");
	}
	private T[] arr; 
	private int len = 0; // length user thinks array is
	private int capacity = 0;
	
	
	public Main() {
		this(8);
	}
	// thanks to java method signature I can use the same one again
	public Main(int capacity) {
		if (capacity < 0) throw new IllegalArgumentException("Illegal Capacity: " + capacity);
		this.capacity = capacity;
		arr = (T[]) new Object[capacity];
	}
	
	public int size() {
		// just return len
		return len;
	}
	
	public boolean isEmpty() {
		// is the length 0
		return size() == 0;
	}
	
	public T get(int index) {
		// O(1)
		return arr[index]
	}
	
	public void set(int index, T elem) {
		// add the item at a given index
		arr[index] = elem;
	}
	
	public void clear() {
		// go one by one null'ing all the values, O(n)
		for(int i = 0; i < len; i++) arr[i] = null;
		len = 0;
	}
	
	public void add(T elem) {
		
	// resize if the capacity is reached
		if(len + 1 >= capacity) {
			// handle 0 capacity
			if (capacity == 0) capacity = 1;
			// double the size 
			else capacity *= 2;
			// create a new array with the new capacity
			T[] new_arr = (T[]) new Object[capacity];
			// move the items into the new array
			for (int i = 0; i < len; i++) new_arr[i] = arr[i];
			// now make it your main array
			arr = new_arr;
		}
		arr[len++] = elem; // finally add the item
	}
	
	// to remove at a certain given index
	public T removeAt(int rm_index) {
		// handle invalid index
		if (rm_index >= len || rm_index < 0) throw new IndexOutOfBoundsException();
		
		T data = arr[rm_index];
		// new array with a shorter length
		T[] new_arr = (T[]) new Object[len - 1];
		
		// gonna iterate and skip the item I want to remove
		for(int i = 0, j = 0; i < len; i++, j++) {
			if (i == rm_index) j--; // Skip over rm_index by fixing j temporarily
			// pass the items one by one into the new array 
			else new_arr[j] = arr[i];	
		}
		arr = new_arr;
		capacity = --len;
		// returning the deleted value
		return data;
	}
	
	public boolean remove(Object obj) {
		int index = indexOf(obj);
		if ( index == -1) return false;
		removeAt(index);
		return true;
	}
	
	public int indexOf(Object obj) {
		for (int i = 0; i < len; i++) {
			if(obj == null) {
				if (arr[i] == null) return i; 
			} else {
				if (obj.equals(arr[i])) return i;
			}
		}
		return -1;
	}
	
	// well I wouldn't add this but this code was written by someone
	// who's much more experienced than I am so yeah 
	public boolean contains(Object obj) {
		return indexOf(obj) != -1;
	}
	
	
	
	// one automatically given by java
	//@Override
	//public Iterator<T> iterator() {
		// TODO Auto-generated method stub
		//return null;
	//}
	@Override
	public java.util.Iterator<T> iterator() {
		return new java.util.Iterator<T>() {
			int index = 0;

			@Override
			public boolean hasNext() {
	        	return index < len;
			}

		    @Override
		    public T next() {
		    	return arr[index++];
		    }
	
		    @Override
		    public void remove() {
		    	throw new UnsupportedOperationException();
		    }
	    };
	}
	
	@Override
	public String toString() {
		if (len == 0) return "[]";
	    else {
	    	StringBuilder sb = new StringBuilder(len).append("[");
	    	for (int i = 0; i < len - 1; i++) sb.append(arr[i] + ", ");
	    	return sb.append(arr[len - 1] + "]").toString();
	    }
	}
}
