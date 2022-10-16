import keyMeUp.DSAQueue;

//Submitted as part of Prac 4 for DSA Semester 2, 2022
public class UnitTestQueue {
    public static void main(String Args[]) {
        //initialise an array of test values
        int testData[] = {4, 15, 102, 11, 5, 7, 2, 67, 100, 20};

        //Display test data
        System.out.println("Input test data is as follows: ");
        for(int ii = 0; ii < testData.length; ii++)
            System.out.print(testData[ii] + " ");
        System.out.println("\n");

        float numTests = 0;
        float numPassed = 0;
        float percPass = 0;
        Object result = 0;
        DSAQueue queue = new DSAQueue(); //initialise new queue

        System.out.println("\nTesting Shuffling Queue.");

        //isEmpty() test with empty queue
        numTests++;
        System.out.println("Test 1: isEmpty() functionality on empty queue.");
        try {
            if (!queue.isEmpty())
                throw new Exception("Shuffling queue failed: isEmpty() did not return expected boolean value (true).");
            System.out.println("Passed.\n");
            numPassed++;   
        }
        catch(Exception ex) { System.out.println(ex.getMessage()); };

        //Exception handling when calling "peek()" on an empty array
        System.out.println("Test 2: Exception handling for 'peek()'.");
        numTests++;
        try {
            queue.peekNext();
            System.out.println("Passed.\n");
            numPassed++;
        }
        catch(Exception ex) {
            System.out.println("Failed.\n");        
        }

        //Fill stack with test data, testing enqueue();
        System.out.println("Test 3: Testing enqueue(), filling stack with all testArray elements.");
        numTests++;
        try {
            for(int ii = 0; ii < testData.length; ii++)
                queue.enqueue(testData[ii]);
            System.out.println("Passed.");
            queue.display();
            System.out.println();
            numPassed++;
        }
        catch(Exception ex) {
            System.out.println("Failed.\n");
        }

        //isEmpty() Test with filled queue
        numTests++;
        System.out.println("Test 4: isEmpty() functionality on full queue.");
        try{
            if(queue.isEmpty())
                throw new Exception("Test failed: isEmpty() returned true on filled queue.");
            System.out.println("Passed.\n");
            numPassed++;
        }
        catch(Exception ex) {
            System.out.println(ex.getMessage());
        }

        //Verify data returned from peek() is the same as in the testdata array
        numTests++;
        System.out.println("Test 5: Check peek() returns correct value from queue.");
        try{
            result = queue.peekNext();
            if( !(result.equals(testData[0])) )
                throw new Exception ("Failed: peek() returns incorrect value.");
            System.out.println("Peek returned: " + result); 
            System.out.println("Passed.\n");
            numPassed++;
        }
        catch(Exception ex) { System.out.println(ex.getMessage()); }
        
        //Dequeue() functionality on array.
        numTests++;
        System.out.println("Test 6: dequeue() value from the queue, verify value is correct");
        try{
            result = queue.dequeue();
            if( !(result.equals(testData[0])) )
                throw new Exception("Failed: dequeue() returned incorrect value.");
            System.out.println("Dequeue returned: " + result); 
            System.out.println("Passed.");
            numPassed++;
        }
        catch(Exception ex) { System.out.println(ex.getMessage()); }

        //verify data returned from peek() after the dequeue() is correct
        numTests++;
        System.out.println("Test 7: Check peek() returns correct value from stack after dequeue().");
        try{
            result = queue.peekNext();
            if( !(result.equals(testData[1])) )
                throw new Exception ("Failed: peek() returns incorrect value.");
            System.out.println("Peek returned: " + result); 
            System.out.println("Passed.");
            numPassed++;
        }
        catch(Exception ex) { System.out.println(ex.getMessage()); }

        System.out.println("Number of tests passed: " + numPassed + " out of " + numTests);
        percPass = (numPassed/numTests)*100;
        System.out.println("Pass rate: " + percPass);

    }
}

