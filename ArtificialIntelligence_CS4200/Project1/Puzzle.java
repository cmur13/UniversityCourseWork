import java.util.List;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.HashSet;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class Puzzle {
    //constants for the goal matrix
    private static final int[][] goal = { { 0, 1, 2 }, { 3, 4, 5 }, { 6, 7, 8 } };
    private static final int matrixSize = 3;
    private static final Set<Integer> numsAllowed = new HashSet<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8));

    // instance variables
    private List<List<Integer>> matrix;
    private int positionX;
    private int positionY;
    private int g;
    private int hValue;
    private Puzzle parent;

    // Constructors
    public Puzzle(List<List<Integer>> matrix) {
        initializeMatrix(matrix);
    }
    public Puzzle(Integer[][] matrix) {
        List<List<Integer>> list = convertArrayToList(matrix);
        initializeMatrix(list);
    }

    public Puzzle(String readString) {
        List<List<Integer>> list = convertStringToList(readString);
        initializeMatrix(list);
    }

    // convert 2D array to lists
    private List<List<Integer>> convertArrayToList(Integer[][] matrix) {
        return Arrays.stream(matrix).map(Arrays::asList).collect(Collectors.toList());
    }

    // convert string to list
    private List<List<Integer>> convertStringToList(String matrix) {
        List<List<Integer>> matrixList = new ArrayList<>();
        List<Integer> row = new ArrayList<>();

        for (int i = 0; i < matrix.length(); i++) {
            if (row.size() == 3) {
                matrixList.add(new ArrayList<>(row));
                row.clear();
            }
            row.add(Character.getNumericValue(matrix.charAt(i)));
        }

        if (!row.isEmpty()) {
            matrixList.add(row);
        }

        return matrixList;
    }

    // initialize the matrix and other instance variables
    public void initializeMatrix(List<List<Integer>> matrix) {
        if (!checkValidity(matrix))
            throw new IllegalArgumentException("Wrong matrix.");

        this.matrix = matrix;
        this.parent = null;
        this.g = 0;
        this.hValue = 0;

        findEmptyTilePosition(matrix);
    }

    // this method will find the position of the empty tile in each row
    private void findEmptyTilePosition(List<List<Integer>> matrix) {
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                if (matrix.get(y).get(x) == 0) {
                    this.positionX = x;
                    this.positionY = y;
                    return;
                }
            }
        }
    }

    // this is the method for the first heuristic function (h1)
    public int h1Function() {
        int h1 = 0;

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (matrix.get(row).get(col) == 0) {
                    continue; // skip the empty tile
                }


                if (goal[row][col] != matrix.get(row).get(col)) {
                    h1++; // increment if tile is not in its goal position
                }
            }
        }

        return h1;
    }

    // this is the second heuristic function
    public int h2function() {
        int h2 = 0;

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (matrix.get(row).get(col) == 0) { // Skip the empty tile
                    continue;
                }
                h2 += calculateDistance(row, col);
            }
        }
        return h2;
    }

    // Calculate the distance for the h2 function
    public int calculateDistance(int row, int col) {
        int tile = matrix.get(row).get(col);
        Point position = findPosition(tile);

        return calculateH2Distance(position, row, col);
    }

    // finds the goal position for the tile
    private Point findPosition(int tile) {
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                if (goal[y][x] == tile) {
                    return new Point(x, y); // found the position
                }
            }
        }
        throw new RuntimeException("No position found.");
    }

    // Calculate the Manhattan distance between the current position and the intended position
    private int calculateH2Distance(Point intendedPosition, int row, int col) {
        // Compute the absolute differences in X and Y coordinates
        int x = Math.abs(intendedPosition.x - col);
        int y = Math.abs(intendedPosition.y - row);
        return x + y; // total distance
    }


    private class Point {
        int x, y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    // calculates the total cost f(n) for the puzzle
    public int calculateF() {
        if (hValue != 1 && hValue != 2) {
            throw new RuntimeException("Illegal hValue"); // invalid heristic value
        }

        int h = (hValue == 1) ? h1Function() : h2function();
        return g + h; // total cost
    }
    // other move methods, might not be used
    public Puzzle moveLeft() {
        // Moves the empty tile to the left
        return swap(positionX, positionY, positionX - 1, positionY);
    }

    // even with no uses, you need this because it will give an error without
    public Puzzle moveRight() {
        // Moves the empty tile to the right
        return swap(positionX, positionY, positionX + 1, positionY);
    }

    public Puzzle moveUp() {
        // Moves the empty tile up
        return swap(positionX, positionY, positionX, positionY - 1);
    }

    public Puzzle moveDown() {
        // Moves the empty tile down
        return swap(positionX, positionY, positionX, positionY + 1);
    }

    public List<Method> Moves() {
        List<Method> moves = new ArrayList<>();
        // add avaible moves based on the current position
        addMove(moves, "moveLeft", positionX > 0);
        addMove(moves, "moveRight", positionX < 2);
        addMove(moves, "moveUp", positionY > 0);
        addMove(moves, "moveDown", positionY < 2);

        return moves;
    }

    // adds a move to the list of possible moves
    private void addMove(List<Method> list, String method, boolean condition) {
        if (condition) {
            try {
                list.add(this.getClass().getDeclaredMethod(method));
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }

    //swaps the position of two tiles in the matrix
    private Puzzle swap(int x1, int y1, int x2, int y2) {
        if(
                x1 < 0 || x2 < 0 || y1 < 0 || y2 < 0 ||
                        x1 > 3 || x2 > 3 || y1 > 3 || y2 > 3
        ){
            throw new IllegalArgumentException("Wrong values.");
        }
        List<List<Integer>> matrix = copyMatrix();

        int original = this.matrix.get(y1).get(x1);
        int target = this.matrix.get(y2).get(x2);
        matrix.get(y1).set(x1, target);
        matrix.get(y2).set(x2, original);


        Puzzle newP = new Puzzle(matrix);
        newP.setHValue(this.hValue);

        return newP;
    }

    //creates a copy of the current matrix
    private List<List<Integer>> copyMatrix() {
        List<List<Integer>> copy = new ArrayList<>();
        // iterates through each row and creates a new list
        for (List<Integer> list : this.matrix) {
            copy.add(new ArrayList<>(list));
        }
        // returns the copied matrix
        return copy;
    }

    // validates the puzzle by checking its size, values, and solvability
    public static boolean checkValidity(List<List<Integer>> matrix) {
        return checkIfSizeValid(matrix) && checkValues(matrix) && isSolvable(matrix);
    }

    // check to see if the matrix has the correct dimensions
    private static boolean checkIfSizeValid(List<List<Integer>> matrix) {
        // returns false is the size is not valid
        if (matrix.size() != matrixSize) {
            return false;
        }
        for (List<Integer> row : matrix) {
            if (row.size() != matrixSize) {
                return false;
            }
        }
        return true;
    }

    // checks if all values are within the range (0-8)
    private static boolean checkValues(List<List<Integer>> matrix) {
        Set<Integer> values = new HashSet<>();
        for (List<Integer> row : matrix) {
            for (int value : row) {
                if (!numsAllowed.contains(value) || !values.add(value)) {
                    return false;
                }
            }
        }
        return true;
    }

    //checks if the puzzle is solvable based on the number of inversions
    private static boolean isSolvable(List<List<Integer>> matrix) {
        return inversionCheck(matrix) % 2 == 0;
    }

    // calculates the inversions for a single element in the matrix
    private static int countNumInversions(List<List<Integer>> matrix, int row, int col) {
        int inversions = 0;
        int current = matrix.get(row).get(col);

        // Skips the empty tile value (0)
        if (current == 0) {
            return 0;
        }

        for (int i = row; i < matrixSize; i++) {
            for (int j = (i == row ? col + 1 : 0); j < matrixSize; j++) {
                int next = matrix.get(i).get(j);

                // Skip the empty tile
                if (next == 0) {
                    continue;
                }

                if (current > next) {
                    inversions++;
                }
            }
        }

        return inversions;
    }

    // calculates the total number of inversions in the matrix
    public static int inversionCheck(List<List<Integer>> matrix) {
        int inversions = 0;

        for (int i = 0; i < matrixSize; i++) {
            for (int j = 0; j < matrixSize; j++) {
                inversions += countNumInversions(matrix, i, j);
            }
        }
        // return the total inversions
        return inversions;
    }

    // Organizes the matrix into a readable string format
    @Override
    public String toString() {
        StringBuilder puzzle = new StringBuilder();
        for (List<Integer> row : this.matrix) {
            puzzle.append(row).append("\n");
        }
        return puzzle.toString();
    }

    @Override
    public boolean equals(Object otherPuzzle) {
        if (this == otherPuzzle) {
            return true;
        }
        if (otherPuzzle == null || getClass() != otherPuzzle.getClass()) {
            return false;
        }
        return this.equals((Puzzle) otherPuzzle);
    }


    public boolean equals(Puzzle otherPuzzle) {
        return checkPuzzles(this.matrix, otherPuzzle.matrix);
    }

    // compares two puzzles for equality
    public boolean checkPuzzles(List<List<Integer>> firstPuzzle, List<List<Integer>> secondPuzzle) {
        if (firstPuzzle.size() != secondPuzzle.size() || firstPuzzle.isEmpty()) {
            return false; // Different sizes or empty matrices are not equal
        }

        for (int i = 0; i < firstPuzzle.size(); i++) {
            if (!firstPuzzle.get(i).equals(secondPuzzle.get(i))) {
                return false; // Rows differ, matrices are not equal
            }
        }
        return true; // puzzles are equal
    }

    // calculates the hash code for the puzzle
    @Override
    public int hashCode() {
        int result = 0;
        for (List<Integer> row : matrix) {
            result = 31 * result + row.hashCode();
        }
        return result;
    }


    // getter/setter methods:
    public boolean isGoal() {
        return h1Function() == 0;
    }


    public int getG() {
        return this.g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public int getHValue() {
        return this.hValue;
    }

    public void setHValue(int h) {
        this.hValue = h;
    }

    public boolean hasParent() {
        return parent != null;
    }

    public Puzzle getParent() {
        return this.parent;
    }

    public void setParent(Puzzle parent) {
        this.parent = parent;
    }

    public void setPositionX(int x) {
        this.positionX = x;
    }

    public void setPositionY(int y) {
        this.positionY = y;
    }
}