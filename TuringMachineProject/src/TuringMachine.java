import java.util.InputMismatchException;
import java.util.Scanner;

public class TuringMachine {

    char[][] transitionFunction;
    char[] alphabet;
    int numOfStates;
    int numOfFunctions;
    private char[] tape;
    private int head;
    char currentState;

    public TuringMachine() throws InputMismatchException {
        numOfFunctions = getNumOfFunctions();
        transitionFunction = new char[numOfFunctions][5];
        fillTransitionFn();
        getTransitionFunction(numOfFunctions);
    }

    public void readString(String string, int initialPosition) {
        tape = string.toCharArray();
        head = initialPosition;
        currentState = '0';
        Character acceptStatues = '0';
        //read the string and move the head according to the transition
        //states, so first we read the current char at the head index
        //then we check the current state we are in and then find the suitable transition from the states array

        while (!acceptStatues.equals('n') && !acceptStatues.equals('y')) {
            char currentChar = tape[head];
            acceptStatues = transition(currentChar);
            if (acceptStatues.equals('r'))//if the transition method returns 'r' then head will go right
                head++;
            else if (acceptStatues.equals('l'))//if it returns 'l' then the head will go left
                head--;
        }//after the loop ends the variable acceptStatues must equal 'n':No, or 'y':Yes.
        if (acceptStatues.equals('y'))
            System.out.println("String accepted");
        else
            System.out.println("String Rejected");
        System.out.println("Head position: " + head);

        System.out.print("Final String: ");
        for (char ch : tape) {
            System.out.print(ch);
        }
        System.out.println();
    }

    public char transition(char currentChar) {
        for (char[] ch : transitionFunction) {
            //finding the right transition rule for the current state and the current character
            if (ch[0] == currentState && ch[1] == currentChar) {
                //change the state according to the rule
                currentState = ch[2];
                //and changing the character according the rule
                tape[head] = ch[3];
                //now returning the action (yes/no/right/left)
                return ch[4];
            }
        }
        //if the rule wasn't found return 'no'
        return 'n';
    }

    //the transitionFunction should be ready with the first 2 columns
    //and for each row the program will ask the user for the next state, the alphabet replacement, and the direction (r,l,y,n)
    public void getTransitionFunction(int numOfFunctions) {// numberOfRules = number of states * (letters of alphabet + machine alphabet)
        Scanner sc = new Scanner(System.in);

        for (int i = 0; i < numOfFunctions; i++) {
            System.out.println("\n------------");
            System.out.println("\nEnter transition for state: " + "(" + transitionFunction[i][0] + ", " + transitionFunction[i][1] + ")");
            System.out.print("New state: ");
            char newState = sc.nextLine().charAt(0);
            System.out.print("New alphabet: ");
            char newAlphabet = sc.nextLine().charAt(0);
            System.out.println("Transition: (y:yes, n:no, l:left, r:right)");
            char transition = sc.nextLine().charAt(0);

            if (charExists(alphabet, newAlphabet)) {
                transitionFunction[i][3] = newAlphabet;
            } else {
                System.out.println("Error.. you entered a value that doesn't belong to the alphabet");
                i--;
            }
            if (isTransitionChar(transition))
                transitionFunction[i][4] = transition;
            else {
                System.out.println("Error.. invalid transition. please enter a transition from the following: y,n,l,r");
                i--;
            }
            if (newState <= numOfStates + '0')
                transitionFunction[i][2] = newState;
            else {
                System.out.println("Error.. there are only " + numOfStates + " states");
                i--;
            }
        }
    }

    public int getNumOfFunctions() throws InputMismatchException {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Number Of States: ");
        numOfStates = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter number of string alphabet: ");
        int numOfAlphabet = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter number of machine alphabet: ");
        int numOfMachineAlphabet = sc.nextInt();
        sc.nextLine();

        alphabet = new char[numOfAlphabet + numOfMachineAlphabet];
        for (int i = 0; i < numOfAlphabet; i++) {
            System.out.print("Enter alphabet " + (i + 1) + ": ");
            alphabet[i] = sc.next().charAt(0);
        }
        for (int i = numOfAlphabet; i < alphabet.length; i++) {//'i' will start from the last index in the array
            System.out.print("Enter machine alphabet " + (i - numOfAlphabet + 1) + ": ");//(i-numOfAlphabet+1) to start from index 1
            alphabet[i] = sc.next().charAt(0);
        }

        return numOfStates * alphabet.length;
    }

    public void fillTransitionFn() {
        //filling the first 2 columns of the transition function: (state, alphabet)
        int totalLength = alphabet.length;
        for (int i = 0, j = 0; i < numOfFunctions; i++) {
            //'j' represents the current state
            if (i % totalLength == 0 && i != 0)//increment 'j' every alphabet cycle
                j++;
            transitionFunction[i][0] = (char) (j + '0');//fills the first column with the states
            transitionFunction[i][1] = alphabet[i % totalLength];//this keeps the 'i' value in bounds of 'alphabet' array
        }
    }

    public boolean charExists(char[] array, char item) {
        for (char i : array) {
            if (i == item)
                return true;
        }
        return false;
    }

    //checks if a character is a transition character (y: yes, r: right, l: left, n: no)
    public boolean isTransitionChar(char ch) {
        return ch == 'y' || ch == 'r' || ch == 'l' || ch == 'n';
    }

    public void printTransitionFn() {
        System.out.println("Machine Language: ");
        for (char[] ch : transitionFunction) {
            System.out.print("(" + ch[0] + ", " + ch[1] + "), ");
            System.out.println("(" + ch[2] + ", " + ch[3] + ", " + ch[4] + ")");

        }
    }

}
