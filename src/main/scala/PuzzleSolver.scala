import scala.collection.mutable

object PuzzleSolver {

  /*** No changes above this line ***/

  private def boardOrder(boardInput: PuzzleBoard) = boardInput.heuristic
  /**
    * Finds a solution to the 15 puzzle with the fewest number of moves using BFS
    *
    * @param startBoard the board to solve
    * @return an option with either Some(board) or None
    * @throws IllegalArgumentException thrown if puzzle is not solvable
    */
  @throws[IllegalArgumentException]
  def solve(startBoard: PuzzleBoard): Option[PuzzleBoard] =  {

    //first check that the board is solvable (step 1)
    require(startBoard.solvable)

    //create an empty priority queue (step 2)
    val boardPriorityQueue: mutable.PriorityQueue[PuzzleBoard] = mutable.PriorityQueue(startBoard)(
      Ordering.by(boardOrder))

    var counter = 0

    //use best first search to find the solution (step 4)
    while (boardPriorityQueue.nonEmpty && counter < boardPriorityQueue.last.maxMoves) {

      counter += 1
      val currentBoard = boardPriorityQueue.dequeue()
      var heuristicSeq: Seq[Int] = Seq(currentBoard.heuristic)

      if (currentBoard.heuristic == 0) return Some(currentBoard)

      val validMoves = currentBoard.validMoves()

      for (move <- validMoves){
        val nextBoard = currentBoard.makeMove(move)
        heuristicSeq = heuristicSeq ++ Seq(nextBoard.heuristic)
        boardPriorityQueue.enqueue(nextBoard)
        }

      for(i <- 0 until boardPriorityQueue.length-1) boardPriorityQueue.dequeue()

      if (heuristicSeq.distinct.lengthCompare(1) == 0) {
        boardPriorityQueue.dequeueAll
        if(validMoves.nonEmpty) boardPriorityQueue.enqueue(currentBoard.makeMove(validMoves.min))
      }

    }
    None
  }
  /*** No changes below this line ***/
}
