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
  def solve(startBoard: PuzzleBoard): Option[PuzzleBoard] =  {

    //first check that the board is solvable (step 1)
    require(startBoard.solvable)

    //store the first index of zero in moves
    startBoard.moves ++ Seq(startBoard.board.indexOf(0))

    def boardOrder(boardInput: PuzzleBoard) = boardInput.heuristic

    //create an empty priority queue (step 2)
    val boardPriorityQueue: mutable.PriorityQueue[PuzzleBoard] = mutable.PriorityQueue(startBoard)(
      Ordering.by(boardOrder))

    //use best first search to find the solution (step 4)
    while (boardPriorityQueue.nonEmpty) {

      //Choose the last board in the queue as it has the lowest heuristic value
      if (boardPriorityQueue.last.heuristic == 0) boardPriorityQueue.last

      //Find all the valid moves for that current state of the board
      val validMoves: Seq[Int] = boardPriorityQueue.last.validMoves()
      println(f"valid moves are $validMoves")

      //for each possible move create a new board and make move and enqueue it to the queue
      for (move <- validMoves) {
        val tempBoard: PuzzleBoard = boardPriorityQueue.last
        println(f"last board is ${boardPriorityQueue.last}")
        boardPriorityQueue.enqueue(tempBoard.makeMove(move))
        println(f"board after enqueue: $boardPriorityQueue")
        boardPriorityQueue.dequeue()
        println(f"board after dequeue: $boardPriorityQueue")
      }
      println(f"Last board is ${boardPriorityQueue.last}")
    }

    if (boardPriorityQueue.last.heuristic == 0) Some(boardPriorityQueue.last)
    else None
  }
  /*** No changes below this line ***/

}
