import scala.collection.mutable

object PuzzleSolver {

  /*** No changes above this line ***/

  /**
    * Finds a solution to the 15 puzzle with the fewest number of moves using
    * Best-First Search (BFS) - https://en.wikipedia.org/wiki/Best-first_search
    *
    * Pseudo-code:
    * 1. Require that the starting board is solvable.
    * 2. Maintain a minimum priority queue of boards ordered by their heuristic,
    * see http://www.scala-lang.org/api/2.12.0/scala/collection/mutable/PriorityQueue.html
    * for details.
    * 3. Add the starting board to the priority queue.
    * 4. Until the queue is empty pick the board with the lowest heuristic value
    * (closest to a solution) from the priority queue
    * 4a. If the current board has a heuristic of 0, we are done and return that board.
    * 4b. For each possible move, make the move and add the resulting board
    * to the priority queue.
    * If no solution is found after exhausting the search space, return None.
    *
    * @param startBoard the board to solve
    * @return an option with either Some(board) or None
    * @throws IllegalArgumentException thrown if puzzle is not solvable
    */
  @throws[IllegalArgumentException]
  def solve(startBoard: PuzzleBoard): Option[PuzzleBoard] = ??? // Implement method here

  /*** No changes below this line ***/

}
