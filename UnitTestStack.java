import keyMeUp.DSAStack;

//Submitted as part of Prac 4 for DSA Semester 2, 2022
public class UnitTestStack {    
    public static void main(String Args[]) {
        // Initialise an array of test values
        int testDataArr[] = {4, 15, 102, 11, 5, 7, 2, 67, 100, 20};
        DSAStack testStack = new DSAStack();
        float numTests = 0;
        float numPassed = 0;
        float percPass = 0;
        Object result = new Object();

        //Display test data
        System.out.println("Input test data is as follows: ");
        for(int ii = 0; ii < testDataArr.length; ii++)
            System.out.print(testDataArr[ii] + " ");
        System.out.println();

        System.out.println("Starting tests for the stack: ");

        //isEmpty() test with empty stack
        numTests++;
        System.out.println("Test 1: isEmpty() functionality on empty stack.");
        try {
            if (!testStack.isEmpty())
                throw new Exception("Test Failed: isEmpty() doesn't identify empty stack.\n");
            System.out.println("Passed. Returned true.\n");
            numPassed++;   
        }
        catch(Exception ex) { System.out.println(ex.getMessage()); }

        //Exception handling when calling "top()" on an empty array
        System.out.println("Test 2: Exception handling for 'top()'.");
        numTests++;
        try {
            testStack.top();
            System.out.println("Passed.\n");
            numPassed++;
        }
        catch(Exception ex) {
            System.out.println("Failed. Error unhandled\n");   
        }

        //Fill stack with test data, testing push();
        System.out.println("Test 3: Testing push(), filling stack with all testArray elements.");
        numTests++;
        try {
            for(int ii = 0; ii < testDataArr.length; ii++)
                testStack.push(testDataArr[ii]);
            testStack.display();
            System.out.println("Passed.\n");
            numPassed++;
        }
        catch(Exception ex) {
            System.out.println("Failed.\n");
        }

        //isEmpty() Test with filled Stack
        numTests++;
        System.out.println("Test 4: isEmpty() functionality on full stack.");
        try{
            if(testStack.isEmpty())
                throw new Exception("Test failed: isEmpty() returned true on filled stack.\n");
            System.out.println("Passed. Returned false.\n");
            numPassed++;
        }
        catch(Exception ex) {
            System.out.println(ex.getMessage());
        }

        //verify data returned from top() is the same as in the testdata array
        numTests++;
        System.out.println("Test 5: Check top() returns correct value from stack.");
        try{
            result = testStack.top();
            if( !(result.equals(testDataArr[testDataArr.length - 1])) )
                throw new Exception ("Failed: top() returns incorrect value.\n");
            System.out.println("Passed. Peek returned: " + result + "\n");
            numPassed++;
        }
        catch(Exception ex) { System.out.println(ex.getMessage()); }

        //Pop() functionality on array.
        numTests++;
        System.out.println("Test 6: pop() value from the stack");
        try{
            result = testStack.pop();
            if( !(result.equals(testDataArr[testDataArr.length - 1])) )
                throw new Exception("Failed: pop() returned incorrect value.\n");
                System.out.println("Passed. Pop returned: " + result + "\n");
            numPassed++;
        }
        catch(Exception ex) { System.out.println(ex.getMessage()); }

        //verify data returned from top() after the pop() is correct
        numTests++;
        System.out.println("Test 7: Check top() returns correct value from stack after pop().");
        try{
            result = testStack.top();
            if( !(result.equals(testDataArr[testDataArr.length - 2])) )
                throw new Exception ("Failed: top() returns incorrect value.");
            System.out.println("Passed. Peek returned: " + result + "\n");
            numPassed++;
        }
        catch(Exception ex) { System.out.println(ex.getMessage()); }

        System.out.println("Number of tests passed: " + numPassed + " out of " + numTests);
        percPass = (numPassed/numTests)*100;
        System.out.println("Pass rate: " + percPass);
    }
}
