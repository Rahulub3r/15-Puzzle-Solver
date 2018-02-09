import org.scalatest.FlatSpec

class PuzzleTest extends FlatSpec {

  it should "not allow a stand-still move" in {
    val board = PuzzleBoard(Seq(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 0, 12, 13, 14, 15), maxMoves = 100)
    assert(!board.isValidMove(11))
  }

  it should "not allow a two-step move" in {
    val board = PuzzleBoard(Seq(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 0, 12, 13, 14, 15), maxMoves = 100)
    assert(!board.isValidMove(9))
  }

  it should "not allow a diagonal move" in {
    val board = PuzzleBoard(Seq(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 0, 12, 13, 14, 15), maxMoves = 100)
    assert(!board.isValidMove(14))
  }

  it should "not allow moves beyond max moves" in {
    val board = PuzzleBoard(Seq(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 0, 12, 13, 14, 15), maxMoves = 1)
    assert(!board.isValidMove(10))
  }

  it should "not allow back and forth moves" in {
    val board = PuzzleBoard(Seq(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 0, 12, 13, 14, 15), maxMoves = 5)
    val newBoard = board.makeMove(10)
    assert(!newBoard.isValidMove(11))
  }

  it should "calculate odd inversions for solvable puzzles" in {
    val board = PuzzleBoard(Seq(13, 2, 10, 3, 1, 12, 8, 4, 5, 0, 9, 6, 15, 14, 11, 7), maxMoves = 3)
    assert(board.inversions == 41)
    assert(board.solvable)
  }

  it should "calculate even inversions for solvable puzzles" in {
    val board = PuzzleBoard(Seq(6, 13, 7, 10, 8, 9, 11, 0, 15, 2, 12, 5, 14, 3, 1, 4), maxMoves = 3)
    assert(board.inversions == 62)
    assert(board.solvable)
  }

  it should "calculate even inversions for insolvable puzzles" in {
    val board = PuzzleBoard(Seq(3, 9, 1, 15, 14, 11, 4, 6, 13, 0, 10, 12, 2, 7, 8, 5), maxMoves = 3)
    assert(board.inversions == 56)
    assert(!board.solvable)
  }

  it should "solve a one horizontal move puzzle in one step" in {
    val board = PuzzleBoard(Seq(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 0, 15), maxMoves = 2)
    val solutionOption = PuzzleSolver.solve(board)
    assert(solutionOption.isDefined)
    val solution = solutionOption.get
    assert(solution.moves.lengthCompare(2) == 0)
    assert(solution.moves == Seq(14, 15))
  }

  it should "detect an unsolvable puzzle" in {
    val board = PuzzleBoard(Seq(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 0, 12, 13, 14, 15), maxMoves = 100)
    assertThrows[Exception](
      PuzzleSolver.solve(board)
    )
  }

  it should "solve a two move puzzle in two steps" in {
    val board = PuzzleBoard(Seq(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 0, 14, 15), maxMoves = 3)
    val solutionOption = PuzzleSolver.solve(board)
    assert(solutionOption.isDefined)
    val solution = solutionOption.get
    assert(solution.moves.lengthCompare(3) == 0)
    assert(solution.moves == List(13, 14, 15))
  }

  it should "solve the defined puzzle" in {
    val board = PuzzleBoard(Seq(1, 0, 2, 4, 5, 7, 3, 8, 9, 6, 11, 12, 13, 10, 14, 15), maxMoves = 10)
    assert(board.solvable)
    val solution = PuzzleSolver.solve(board)
    assert(solution.isDefined)
    assert(solution.get.moves == List(1, 2, 6, 5, 9, 13, 14, 15))
  }

  it should "return None if there is no solution within max moves" in {
    val board = PuzzleBoard(Seq(1, 0, 2, 4, 5, 7, 3, 8, 9, 6, 11, 12, 13, 10, 14, 15), maxMoves = 1)
    assert(board.solvable)
    val solution = PuzzleSolver.solve(board)
    assert(solution.isEmpty)
  }
}
