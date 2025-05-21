# Numerical Methods Java Projects

This repository contains implementations of various numerical methods using Java. The projects demonstrate different techniques to solve systems of linear equations, find polynomial roots, and perform interpolation. Below is an overview of each project:

## 1. A* Search Algorithm (Project 1)

This project implements the A* search algorithm to solve the 8-puzzle problem using two different heuristic functions. The first heuristic (h1) counts the number of misplaced tiles, while the second heuristic (h2) calculates the sum of the Manhattan distances of each tile from its goal position. I compared the performance of both heuristics in terms of solution depth, search cost, and execution time.

### Features:
- Handles systems of linear equations in matrix form.
- Implements partial pivoting to increase numerical stability.

### Files:
- `GaussianElimination.java`: Contains the main logic to perform Gaussian Elimination.

## 2. Jacobi/Gauss-Seidel Iterative Methods (Project 2)

This project implements the Jacobi and Gauss-Seidel methods, two iterative techniques used to solve systems of linear equations. Both methods use an initial guess and iterate until a desired accuracy is reached.

### Features:
- Implements both Jacobi and Gauss-Seidel algorithms.
- Allows setting tolerance and maximum iteration limits.

### Files:
- `JacobiMethod.java`: Implements the Jacobi iterative method.
- `GaussSeidelMethod.java`: Implements the Gauss-Seidel iterative method.
