package assignment_2;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    
    private Node first = null;
    private Node last = null;
    private int nSize;
    
    private class Node {
        Item item;
        Node next;
        Node prev;
    }
    
    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        nSize = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return nSize == 0;
    }

    // return the number of items on the deque
    public int size() {
        return nSize;
    }

    // add the item to the front
    public void addFirst(Item item) {
        CheckIllegal(item);
        Node oldFirst = first;
        first = new Node();
        first.next = oldFirst;
        first.item = item;
        // special cases for empty queue
        if (isEmpty()) {
            last = first;
        }else {
            oldFirst.prev = first;
        }
        nSize++;
    }

    // add the item to the back
    public void addLast(Item item) {
        CheckIllegal(item);
        Node oldLast = last;
        last = new Node();
        last.prev = oldLast;
        last.item = item;
        // special cases for empty queue
        if (isEmpty()) {
            first = last;
        }else {
            oldLast.next = last;
        }
        nSize++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        CheckException();
        Item item = first.item;
        first = first.next;
        // special cases for empty queue
        if (isEmpty()) first = null;
        nSize--;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        CheckException();
        Item item = last.item;
        last = last.next;
        // special cases for empty queue
        if (isEmpty()) last = null;
        nSize--;
        return item;
        
    }
    
    private void CheckException() {
        if (isEmpty()) throw new NoSuchElementException("deque is empty");
    }
    
    private void CheckIllegal(Item item) {
        if (item == null) throw new IllegalArgumentException("illegal (null) argument");
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator(){
        return new IterItem();
    }
    
    private class IterItem implements Iterator<Item>{
        private Node current = first;
        
        public void remove() {
            throw new UnsupportedOperationException("no remove method");
        }
      
        public boolean hasNext() {
            return current != null;
        }
        public Item next() {
            if (!hasNext()) {throw new NoSuchElementException("no more items to return");}
            else
            {
                Item item = current.item;
                current = current.next;
                return item;
            }
        }
        
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<String> d = new Deque<>();
        d.addFirst("aa");
        d.addFirst("bb");
        d.addLast("last");
        d.removeFirst();
        System.out.print(d.size());
        System.out.println(d.first.item);
        System.out.println(d.first.next.item);
        System.out.println(d.removeLast());
        
    }

}