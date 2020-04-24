package assignment_2;

import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] array;
    private int n;
    
    // construct an empty randomized queue
    public RandomizedQueue() {
        n = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size() == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return n;
    }

    // add the item
    public void enqueue(Item item) {
        if (item==null) throw new IllegalArgumentException();
        if (isEmpty()){
            array = (Item[]) new Object[]{item};
            n++;
        }
        else{
            if (array.length==n){
                resize(2*array.length);
            }
            array[n++] = item;
        }
    }

    private void resize(int capacity) {
        assert capacity >= n;
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < size(); i++) {
            copy[i] = array[i];
        }
        array = copy;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException();
        int randIndex = StdRandom.uniform(n);
        Item item = array[randIndex];
        array[randIndex] = array[--n];
        array[n] = null;
        if (n == array.length/4) resize(array.length/2);
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        int randIndex = StdRandom.uniform(n);
        Item item = array[randIndex];
        return item;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator(){
        return new ArrayIterator();
    }
    
    private class ArrayIterator implements Iterator<Item>{
        
        private int current;
        private int[] index;
        
        public ArrayIterator() {
            index = new int[n];
            for (int i = 0; i< n; i++) {
                index[i] = i;
            }
            StdRandom.shuffle(index);
            current = 0;
        }
        @Override
        public boolean hasNext() {  
            return (!isEmpty() && current < n);
        }
        @Override
        public void remove() {
            throw new UnsupportedOperationException();  
        }
        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            return array[index[current++]];       
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();

      System.out.println(rq.isEmpty());
      System.out.println(rq.size());
        
        for (int i=0;i<10;i++){
            rq.enqueue(i);
        }

        Iterator<Integer> randomizedQueueIterator = rq.iterator();
        while (randomizedQueueIterator.hasNext()) {
            System.out.println(randomizedQueueIterator.next());
        }
        
        System.out.println(rq.sample());
        System.out.println(rq.dequeue());
    }
}