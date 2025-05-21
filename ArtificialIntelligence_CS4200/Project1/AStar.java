import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.HashSet;
import java.lang.reflect.Method;


public class AStar {
    Puzzle input;
    Puzzle solution;

    int selectH;
    int h1Cost;
    double h1Time;
    int h2Cost;
    double h2Time;

    int numSteps;

    // constructor
    public AStar(Puzzle input, int selectedH) {
        this.input = input;
        this.selectH = selectedH;
        this.solution = null;
        // call compare to perform A* search using the selected H
        compare();

        // Print some statistics
        System.out.println("Total Steps: " + numSteps);
        System.out.println("H1 Search Cost: " + h1Cost);
        System.out.println("H2 Search Cost: " + h2Cost);
        System.out.println("\nH1 Time: " + h1Time + " ms");
        System.out.println("H2 Time: " + h2Time + " ms");
        System.out.println();
    }

    // performs A* search twice, once for each heuristic
    public void compare() {
        long start = System.nanoTime();
        int cost = AStarSearch(selectH);
        setCost(selectH, cost);
        setTime(selectH, start);
        printPath();

        selectH ^= 3;
        start = System.nanoTime();
        cost = AStarSearch(selectH);
        setCost(selectH, cost);
        setTime(selectH, start);
    }

    // performs A* using the specified heuristic
    public int AStarSearch(int heuristicToUse) {
        input.setHValue(heuristicToUse);

        Queue<Puzzle> frontier = new PriorityQueue<>((p1, p2) -> Integer.compare(p1.calculateF(), p2.calculateF()));
        frontier.add(input);

        Set<Puzzle> explored = new HashSet<>();

        Puzzle currentPuzzle = null;
        Puzzle childPuzzle = null;
        int searchCost = 0;
        searchCost = puzzleModification(currentPuzzle, childPuzzle, searchCost, frontier, explored);
        return searchCost;
    }

    // prints the path
    public void printPath() {
        List<Puzzle> path = getPath();
        int numSteps = path.size();

        for (int i = path.size() - 1; i >= 0; i--) {
            Puzzle currentStep = path.get(i);
            // prints out each step
            System.out.println("Step: " + (numSteps - i));
            System.out.println(currentStep);
        }
    }

    public List<Puzzle> getPath() {
        if (solution == null) {
            throw new IllegalArgumentException("No solution.");
        }

        List<Puzzle> path = new ArrayList<>();
        Puzzle currentPuzzle = this.solution;

        while (currentPuzzle.hasParent()) {
            path.add(currentPuzzle); // add each puzzle to the path
            currentPuzzle = currentPuzzle.getParent(); // move to the parent puzzle
        }

        this.numSteps = path.size(); // set the num of steps
        return path;
    }

    // performs A* search by exploring puzzle states, generating and updating info
    private int puzzleModification(Puzzle currentPuzzle, Puzzle childPuzzle, int searchCost, Queue<Puzzle> frontier,
                                   Set<Puzzle> explored) {
        while (!frontier.isEmpty()) {
            currentPuzzle = frontier.poll(); // get the next puzzle state from the frontier
            explored.add(currentPuzzle); // current puzzle is marked as explored

            if (currentPuzzle.isGoal()) {
                this.solution = currentPuzzle;
                break;
            }

            for (Method test : currentPuzzle.Moves()) {
                try {
                    childPuzzle = (Puzzle) test.invoke(currentPuzzle); // apply a move to generate a child puzzle
                    if (explored.contains(childPuzzle)) {
                        continue; // skip if the child puzzle has been explored
                    }
                    childPuzzle.setHValue(currentPuzzle.getHValue());
                    childPuzzle.setG(currentPuzzle.getG() + 1);
                    childPuzzle.setParent(currentPuzzle);

                    frontier.add(childPuzzle);
                    searchCost++;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return searchCost;

    }

    // sets the search cost for either h1 or h2
    private void setCost(int h, int searchCost) {
        if (h == 1) {
            h1Cost = searchCost;
        } else {
            h2Cost = searchCost;
        }
    }

    // calculates the execution time for a specific heuristic
    private void setTime(int h, long start) {
        if (h == 1) {
            this.h1Time = (System.nanoTime() - start) / 1_000_000.0;
        }else {
            this.h2Time = (System.nanoTime() - start) / 1_000_000.0;
        }
    }
}