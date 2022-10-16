package keyMeUp;
import java.io.*;
import java.util.*;
//Submitted as part of Prac 4 for DSA Semester 2, 2022
public class LList implements Serializable, Iterable {
    
    private DSAListNode head;
    private DSAListNode tail;
    private int length;
    
    public Iterator iterator() {
        return new DSALinkedListIterator(this);
    }
    
    private class DSALinkedListIterator implements Iterator {
        private DSAListNode iterNext; //Cursor to track current node

        public DSALinkedListIterator(LList list) {
            iterNext = list.head;
        }

        @Override
        public Object next() {
            Object value;
            if(iterNext == null)
                value = null;
            else {
                value = iterNext.getValue();
                iterNext = iterNext.getNext();
            }
            return value;
        }
        
        @Override
        public boolean hasNext() { return (iterNext != null); }

        @Override
        public void remove() { throw new UnsupportedOperationException(); }
    }

    private class DSAListNode implements Serializable {
        private Object value;
        private DSAListNode next;
        private DSAListNode prev;
    
        DSAListNode(Object inValue) {
            value = inValue;
            next = null;
            prev = null;
        }
    
        public Object getValue() {
            return value;
        }
    
        public void setValue(Object inValue) {
            value = inValue;
        }
    
        public DSAListNode getNext() {
            return next;
        }
    
        public void setNext(DSAListNode newNext) {
            next = newNext;
        }
        
        public DSAListNode getPrev() {
            return prev;
        }
    
        public void setPrev(DSAListNode newPrev) {
            prev = newPrev;
        }
    }

    public LList(){
        head = null;
        tail = null;
        length = 0;
    }

    public void insertFirst(Object newValue) {
        DSAListNode newNd = new DSAListNode(newValue);
        if(isEmpty()) {
            head = newNd;
            tail = newNd;
        }
        else {
            head.setPrev(newNd);
            newNd.setNext(head);
            head = newNd;
        }
        length++;
    }

    public void insertLast(Object newValue) {
        DSAListNode newNd = new DSAListNode(newValue);
        if(isEmpty()) {
            head = newNd;
            tail = newNd;
        }
        else {
            tail.setNext(newNd);
            newNd.setPrev(tail);
            tail = newNd;
        }
        length++;
    }

    public Object peekFirst() throws Exception {
        Object nodeValue = null;
        
        if(isEmpty())
            throw new Exception("Cannot access value. List is empty.");
        else
            nodeValue = head.getValue();
        
        return nodeValue;
    }

    public Object peekLast() throws Exception {
        Object nodeValue = null;
        
        if(isEmpty())
            throw new Exception("Cannot access value. List is empty.");
        else
            nodeValue = tail.getValue();

        return nodeValue;
    }

    public Object removeFirst() throws Exception {
        Object nodeValue = null;

        if(isEmpty())
            throw new Exception("Cannot access value. List is empty.");
        else if(head.getNext() == null) {
            nodeValue = head.getValue();
            head = null;
            length--;
        }
        else {
            nodeValue = head.getValue();
            head = head.getNext();
            head.setPrev(null);
            length--;
        }
        return nodeValue;
    }

    public Object removeLast() throws Exception {
        Object nodeValue = null;
        
        if(isEmpty())
            throw new Exception("Cannot access value. List is empty.");
        else if(head.getNext() == null) {
            nodeValue = head.getValue();
            head = null;
            length--;
        }
        else {
            nodeValue = tail.getValue();
            tail = tail.getPrev();
            tail.setNext(null);      
            length--;      
        }
        return nodeValue;
    }

    public int getLength() { return length; }

    public void setLength(int newLength) { length = newLength; }

    public boolean isEmpty() {
        boolean empty = false;

        if(head == null)
            empty = true;

        return empty;
    }
}