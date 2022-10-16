package keyMeUp;

import java.util.Iterator;

//Submitted as part of Prac 4 for DSA Semester 2, 2022
public class DSAQueue implements Iterable {
    protected LList queue;

    public Iterator iterator() {
        return queue.iterator();
    }

    public DSAQueue() { queue = new LList(); }

    public boolean isEmpty() { return queue.isEmpty(); }

    public void enqueue(Object data) { 
        queue.insertLast(data); 
    }

    public Object dequeue() {
        Object first = null;
        
        try{
            first = queue.peekFirst();
            queue.removeFirst();
        }
        catch(Exception ex) {
            System.out.println("Cannot dequeue. No items in queue.");
        }

        return first;
    }

    public Object peekNext() {
        Object first = null;
        
        try{ first = queue.peekFirst(); }
        catch (Exception ex) { 
            System.out.println("Cannot read next item in queue. Queue is empty."); 
        }
        
        return first;
    }

    public void display() {
        for( Object ob : queue) {
            System.out.print(ob + " ");
        }
    } 

    public int getLength() { return queue.getLength(); }
}
