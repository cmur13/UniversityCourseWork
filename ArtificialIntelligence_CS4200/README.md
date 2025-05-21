# Numerical Methods Java Projects

This repository contains implementations of various numerical methods using Java. The projects demonstrate different techniques to solve systems of linear equations, find polynomial roots, and perform interpolation. Below is an overview of each project:

## 1. A* Search Algorithm (Project 1)

This project implements the A* search algorithm to solve the 8-puzzle problem using two different heuristic functions. The first heuristic (h1) counts the number of misplaced tiles, while the second heuristic (h2) calculates the sum of the Manhattan distances of each tile from its goal position. I compared the performance of both heuristics in terms of solution depth, search cost, and execution time.

### Features:
- Users can choose to generate a random puzzle or manually enter a puzzle configuration.
- Two heuristic functions to choose from

## 2. N-Queens Problem Solver (Project 2)

This project focuses on solving the N-Queens problem, where the goal is to place N queens on an 8×8 chessboard such that no two queens attack each other. Two algorithms were implemented and compared: the straightforward Steepest-Ascent Hill Climbing and the Min-Conflicts algorithm.
### Features:
-Two Algorithm Implementations:
  *Steepest-Ascent Hill Climbing: A local search algorithm that selects the next state with the steepest improvement.
  *Min-Conflicts: A heuristic repair algorithm that minimizes the number of conflicts in each move.
- Users can select which algorithm to use for solving the puzzle at runtime.

## 3. Tic-Tac-Toe Game (Project 3)
In this project, we implemented a variation of the classic Tic-Tac-Toe game on an 8×8 board. The objective is for a player to get four pieces in a row or column (diagonals are not considered for wins). The user plays as O and the computer (AI) plays as X. The computer makes its decisions using the Alpha-Beta Pruning algorithm.
### Features:
- AI Opponent Using Alpha-Beta Pruning
- Evaluation Heuristic
- Strategic Board Assessment
- 8×8 Game Board
