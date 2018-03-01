import scala.util.Random

/**
  * Represents a 15-puzzle board with 4 rows and 4 columns
  *
  * The board is represented by a sequence of 16 numbers
  * Row 0 is stored in position 0 through 3
  * Row 1 is stored in position 4 through 7
  * Row 2 is stored in position 8 through 11
  * Row 3 is stored in position 12 through 15
  * 0 represents the empty position
  *
  * For example, the board:
  * 1  2  3  4
  * 5  6  7  8
  * 9 10 11 12
  * 13 14 15
  *
  * is represented by the sequence:
  * 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0
  *
  * Each board has a heuristic value that tracks how far away the board
  * is from a solution.The heuristic is the sum of the
  * Manhattan distance for each piece. The lower the heuristic value the
  * closer it is to a solution.
  *
  * The board also tracks all positions that empty square has had in a sequence.
  *
  * @param board    a sequence of values
  * @param moves    the sequence of moves of the empty square
  * @param maxMoves the maximum number of moves allowed to solve
  * @throws IllegalArgumentException if the constructor arguments are invalid
  */
@throws[IllegalArgumentException]
case class PuzzleBoard(
                        board: Seq[Int], // Board state
                        moves: Seq[Int], // Tracks empty(0) positions
                        maxMoves: Int // Maximum number of moves before halting
                      ) {

  import PuzzleBoard._

  require(board.lengthCompare(16) == 0)
  require(moves.nonEmpty)
  require(maxMoves > 0)

  // The number of inversions (wrong order pairs)
  // excluding the empty (0)
  val inversions: Int = inversionPairs
    .map { pair =>
      if (board(pair(0)) != 0 && board(pair(1)) != 0 && board(pair(0)) > board(pair(1)))
        1
      else
        0
    }
    .sum

  /**
    * Puzzles with even number of rows and columns are only solvable if
    *  - the empty square is on an even row and the number of inversions is odd or
    *  - the empty square is on an odd row and the number of inversions is even
    *
    * See http://cseweb.ucsd.edu/~ccalabro/essays/15_puzzle.pdf
    *
    */
  val solvable: Boolean = {
    val (row, _) = indexToCoordinates(moves.last)
    if ((row % 2) == 0 && (inversions % 2) == 1)
      true
    else if ((row % 2) == 1 && (inversions % 2) == 0)
      true
    else
      false
  }

  /**
    * Sum of Manahattan Distances between
    * current position and done position
    * for all numbers
    */
  val heuristic: Int = (0 to 15).iterator.map(i => manhattanDistance(board.indexOf(i), DONE.indexOf(i))).sum

  /**
    * The puzzle is solved when the heuristic is 0
    * i.e. all pieces are in the right place
    */
  val solved: Boolean = heuristic == 0

  /*** No changes above this line ***/

  /**
    * The empty piece can only move
    *  - up, down, left or right
    * We only allow valid moves
    *
    * The resulting moves should be shuffled (randomly ordered)
    * to avoid always picking the same move if multiple moves
    * have the same heuristic.
    *
    * @return a sequence of valid moves (new positions for the empty square)
    */
  def validMoves(): Seq[Int] = {
    val zeroIndex = board.indexOf(0)
    val indexes = board.zipWithIndex.map(_._2)
    val distances = indexes.map(x => manhattanDistance(x, zeroIndex))
    val movesToBeReturned = distances.zipWithIndex.filter(_._1 == 1).map(_._2)
    val maskForMoves: Seq[Boolean] = movesToBeReturned.map(isValidMove)
    Random.shuffle(movesToBeReturned.zip(maskForMoves).collect {case (v, true) => v})
  }

  /**
    * Create new board that records the new board state
    * and the move of the empty square
    *
    * @param x the position to move the empty marker to
    * @return a new board with the marker moved to the new position
    * @throws IllegalArgumentException if the move is not valid
    */
  @throws[IllegalArgumentException]
  def makeMove(x: Int): PuzzleBoard = {
    require(isValidMove(x))
    new PuzzleBoard(
      board.updated(moves.last, board(x)).updated(x, 0),
      moves ++ Seq(x),
      maxMoves
    )
  }

  /**
    * Convert from board sequence index to board coordinates
    *
    * @param i the board sequence index
    * @return a tuple with row and column
    */
  @inline
  private def indexToCoordinates(i: Int): (Int, Int) = {
    val xRow = i / 4
    val xCol = i - xRow * 4
    (xRow, xCol)
  }

  /**
    * Verify validity of move:
    *  - has to stay inside of the board
    *  - can't be a "stand-still" move
    *  - can't move diagonally
    *  - can't move more than one step
    *  - can't reverse previous move
    *  - can't exceed max number of moves
    *
    * @param move the move
    * @return true if the move is valid, false otherwise
    */
  def isValidMove(move: Int): Boolean = {
    if (move == board.indexOf(0)) false //does not allow standstill move
    else if (manhattanDistance(move, board.indexOf(0)) > 1) false //does not allow diagonal movement or more than one step movemenet
    else if (maxMoves < 2) false //cannot exceed max number of moves
    else if (moves.lengthCompare(1) != 0){
      if (move == moves(moves.length-2)) false
      else true
    } //no going back and forth
    else true
  }

  /**
    * Calculate the Manhattan Distance moving between two positions
    * on the board.
    * https://en.wikipedia.org/wiki/Taxicab_geometry
    *
    * @param x the starting position (index in board array)
    * @param y the ending position (index in board array)
    * @return the manhattan distance
    */
  @inline
  private def manhattanDistance(x: Int, y: Int): Int = {
    val (x1_coord, y1_coord) = indexToCoordinates(x)
    val (x2_coord, y2_coord) = indexToCoordinates(y)
    Math.abs(x1_coord - x2_coord) + Math.abs(y1_coord - y2_coord)
  }

  /*** No changes below this line ***/

}

object PuzzleBoard {

  // Definition of done
  private val DONE: Seq[Int] = Seq[Int](1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0)

  // Inversion pairs
  private val inversionPairs: Seq[Seq[Int]] = (0 to 15)
    .combinations(2)
    .filter(pair => pair(0) < pair(1))
    .toSeq

  /**
    * Constructor for manual board
    *
    * @param positions the board positions
    * @param maxMoves  the maximum number of moves allowed to solve
    * @return a board instance
    * @throws IllegalArgumentException if the arguments are not valid
    */
  @throws[IllegalArgumentException]
  def apply(positions: Seq[Int], maxMoves: Int): PuzzleBoard = {
    val emptyPosition = positions.indexOf(0)
    PuzzleBoard(positions, Seq(emptyPosition), maxMoves)
  }
}
