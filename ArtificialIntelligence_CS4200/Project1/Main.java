import java.util.Scanner;
import java.util.Arrays;
import java.util.List;
import java.util.Collections;



public class Main {
    public static void main(String[] args) {

        System.out.println("CS 4200 Project 1");

        Scanner input = new Scanner(System.in);
        int cases;
        while ((cases = inputMethod(input)) != 2) {
            switch (cases) {
                case 1:
                    // Uses a random or manual input for the puzzle
                    singlePuzzle(input);
                    break;
                default:
                    System.out.println("Unexpected error.");
                    break;
            }
        }
        // exit message
        System.out.println("Thanks!");
        // close the scanner
        input.close();
    }
    // will ask the user to choose between an input method
    // dont need to implemenet the multi test puzzle
    public static int inputMethod(Scanner input) {
        int selection = 0;

        // will keep looping if the user doesn't select to exit
        while (selection != 2) {
            // Display the question
            System.out.println("Select:");
            System.out.println("[1] Single Test Puzzle");
            System.out.println("[2] Exit");

            String select = input.nextLine();

            switch (select) {
                case "1":
                    return 1;
                case "2":
                    selection = 2;
                    break;
                default:
                    System.out.println("Invalid. Choose 1 or 2");
                    break;
            }
        }

        return selection;
    }

    public static String puzzleInput(Scanner sc) {
        // Present the question
        System.out.println("Select Input Method:");
        System.out.println("[1] Random");
        System.out.println("[2] User Input");

        // Retrieve the answer
        String select = sc.nextLine();
        switch (select) {
            case "1":
                return randomPuzzle();
            case "2":
                return getInputPuzzle(sc);
            default:
                // Repeatedly ask if the input is invalid
                System.out.println("Invalid. Choose 1 or 2");
                return puzzleInput(sc);
        }
    }

    public static void singlePuzzle(Scanner sc) {
        // Function to handle single puzzle solution process

        String puzzleInput = puzzleInput(sc);
        Puzzle current = new Puzzle(puzzleInput);
        int hChoice = selectHFunction(sc);

        System.out.println("Puzzle:\n" + current);

        new AStar(current, hChoice);
    }

    public static int selectHFunction(Scanner sc) {
        // Display the question
        System.out.println("Select H Function:");
        System.out.println("[1] H1");
        System.out.println("[2] H2");

        // Get the answer
        String select = sc.nextLine();

        // why does it give me both heuristics when i click on one?
        switch (select) {
            case "1":
                return 1;
            case "2":
                return 2;
            default:
                System.out.println("Invalid. Choose 1 or 2");
                return selectHFunction(sc);
        }
    }

    public static String randomPuzzle() {
        List<Character> numbers = Arrays.asList(new Character[] { '0', '1', '2', '3', '4', '5', '6', '7', '8' });

        try {
            // Randomize the tile order
            Collections.shuffle(numbers);
            String puzzle = "";
            for (Character current : numbers) {
                puzzle += Character.toString(current);
            }
            new Puzzle(puzzle);

            return puzzle;
        } catch (Exception e) {
            return randomPuzzle();
        }
    }

    public static String getInputPuzzle(Scanner sc) {
        String puzzleString = "";
        int rows = 3;

        System.out.println("Enter puzzle: (spaces or no spaces");

        boolean validInput = false;
        // will check for a valid input
        while (!validInput) {
            for (int i = 0; i < rows; i++) {
                System.out.printf("Row %d: ", i + 1);
                String rowInput = sc.nextLine().replace(" ", ""); // Make the string a continuous string of numbers, with spaces/no spaces

                // Validate input (check if it contains only digits)
                if (rowInput.matches("\\d+")) {
                    puzzleString += rowInput;
                } else {
                    System.out.println("Invalid input. Please enter only digits.");
                    break; // Exit the loop and prompt user to re-enter the entire puzzle
                }

            }

            // Check if the puzzleString has the expected length
            if (puzzleString.length() == rows * rows) {
                validInput = true;
            } else {
                System.out.println("Invalid puzzle. Please enter exactly " + (rows * rows) + " digits.");
                puzzleString = ""; // Clear the invalid input
            }
        }
        return puzzleString;
    }
}