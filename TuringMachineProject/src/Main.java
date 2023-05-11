import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            TuringMachine TM = new TuringMachine();
            TM.printTransitionFn();
            while (true) {
                System.out.println("\n-------------" +
                        "\n(enter q to end the program)\n");
                System.out.print("Enter String: ");
                String string = sc.nextLine();
                if (string.equals("q")) {
                    break;
                }
                System.out.print("Enter initial position: ");
                int position = sc.nextInt();
                sc.nextLine();
                TM.readString(string, position);

            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid Input. Please enter an integer value");
        }
    }
}