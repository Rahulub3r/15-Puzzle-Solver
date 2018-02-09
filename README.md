# 6347_miniproject01

## Mini-project 1 - 15 Puzzle

The purpose of the mini project is to implement a [15 puzzle](https://en.wikipedia.org/wiki/15_puzzle) solver.

The project consists of two parts:
1. The puzzle board
2. The puzzle solver

You will implement the missing methods in the provided code and ensure that all test cases pass before submitting your solution.

Please do not modify the tests to match your code, we will run the unmodified tests after you submit.

### Puzzle Board
The puzzle board is a 4x4 square that has 15 numbers placed in it and one empty square.

When we discuss moves on the board we will look at it as if we're moving the empty square.
The rules for moving the empty square are as follows:
- it has to stay inside of the board
- it can't stay in place
- it can't move diagonally
- it can't move more than one step
- it can't reverse previous move
- it can't exceed the maximum number of moves

The solved board looks like this:

|   |   |   |   |
|--:|--:|--:|--:|
|  1|  2|  3|  4|
|  5|  6|  7|  8|
|  9| 10| 11| 12|
| 13| 14| 15|   |

For this part you will have to implement the solve method.

### Puzzle Solver
The puzzle solver will be implemented using a [Best-First Search algorithm](https://en.wikipedia.org/wiki/Best-first_search).

When doing a best-first search you need a solution heuristic to measure how close each try it to a solution.
In this case we will use a heuristic based on the [Manhattan Distance](https://en.wikipedia.org/wiki/Taxicab_geometry).
The heuristic will be the sum of each the Manhattan Distance for each square from their current position to the solution position.

An example, if the current board looks like this:

|   |   |   |   |
|--:|--:|--:|--:|
|  1|  2|  3|  4|
|  5|  6|  7|  8|
|  9| 10| 11| 12|
| 13| 14|   | 15|
 
We will have to move 15 one step left and the empty square one step right, so the sum of Manhattan Distnaces is 1+1=2.

If the board is solved the sum of the Manhattan Distances will be 0.

The exercise of solving a board is really an optimization problem where we're trying to find a state that minimizes our goal metric (the Manhattan Distance).

For this part you will have to implement the validMoves, isValidMove and manhattanDistance methods.