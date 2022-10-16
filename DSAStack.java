package keyMeUp;

import java.util.*;

//Submitted as part of Prac 4 for DSA Semester 2, 2022
public class DSAStack implements Iterable {
    private LList stack;

    public Iterator iterator() { return stack.iterator(); }

    public DSAStack() { stack = new LList(); }

    public boolean isEmpty() { return stack.isEmpty(); }

    public void push(Object data) { stack.insertFirst(data); }
    
    public Object pop() {
            Object topVal = null;
            
            try{
            topVal = stack.peekFirst();
            stack.removeFirst();
            }
            catch (Exception ex) {
                System.out.println("Cannot pop. No items in stack.");
            }

            return topVal;
    }

    public Object top() {
        Object topVal = null;
        
        try { topVal = stack.peekFirst(); }
        catch(Exception ex) {
            System.out.println("Cannot top. No items in stack.");
        }
        
        return topVal;
    }

    public void display() {
        for( Object ob : stack) {
            System.out.print(ob + " ");
        }
    }

    public int getLength() { return stack.getLength(); }

}
