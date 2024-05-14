package ex4.ticTacToe.model

import ex4.commons.GameCommons.{GameCell, Player, Position}
import ex4.ticTacToe.model.GameBoard.bound
import org.scalatest.matchers.should.Matchers.*

class GameBoardTest extends org.scalatest.funsuite.AnyFunSuite:

  private val player = Player.X
  private val rowSize = (0 to bound).size
  private val nCell = rowSize * rowSize
  private val gameBoard = GameBoard()

  private def filledBoard: GameBoard =
    var newGameBoard = gameBoard
    for
      x <- 0 to bound
      y <- 0 to bound
    do newGameBoard = newGameBoard :+ GameCell(Position(x, y), player)
    newGameBoard

  private def factorial(n: Int, end: Int = 0): Int =
      if n == end then 1
      else n * factorial(n - 1, end)

  test("find player given a position"):
    (gameBoard :+ GameCell(Position(0, 0), Player.X)).find(Position(0, 0)) shouldBe Some(Player.X)
    (gameBoard :+ GameCell(Position(0, 0), Player.O)).find(Position(0, 0)) shouldBe Some(Player.O)
    gameBoard.find(Position(0, 0)) shouldBe None

  test("find the chosen player"):
    GameBoard.findPlayer(gameBoard :+ GameCell(Position(0, 0), Player.X), Player.X) shouldBe Some(Player.X)
    GameBoard.findPlayer(gameBoard :+ GameCell(Position(0, 0), Player.O), Player.O) shouldBe Some(Player.O)
    GameBoard.findPlayer(gameBoard, Player.X) shouldBe None
    GameBoard.findPlayer(gameBoard, Player.O) shouldBe None

  test("cell available given a position"):
    gameBoard.available(Position(0, 0)) shouldBe true
    (gameBoard :+ GameCell(Position(0, 0), Player.X)).available(Position(0, 0)) shouldBe false

  test("makeAnyMove in empty board"):
    GameBoard.doAllPossibleMove(gameBoard, player) should have size nCell
    GameBoard.doAllPossibleMove(gameBoard, player).distinct should have size nCell

  test("makeAnyMove in a non empty board"):
    GameBoard.doAllPossibleMove(gameBoard :+ GameCell(Position(0, 0), player), player) should have size (nCell - 1)

  test("makeAnyMove in a full board"):
    GameBoard.doAllPossibleMove(filledBoard, player) should have size 0

  test("computeAnyGame given a player and number of moves"):
    GameBoard.computeAnyGame(player, 0) should have size 1 //empty board
    GameBoard.computeAnyGame(player, 1) should have size nCell
    GameBoard.computeAnyGame(player, 2) should have size (nCell * (nCell - 1))
    GameBoard.computeAnyGame(player, 4) should have size factorial(nCell, nCell-4)
    GameBoard.computeAnyGame(player, 4).distinct should have size factorial(nCell, nCell-4)
    //GameBoard.computeAnyGame(player, nCell) should have size factorial(nCell) //too slow

  test("4 or less moves is not enough to win the game"):
    for
      game <- GameBoard.computeAnyGame(player, 4)
      board <- game
    do
      board.won shouldBe false

  test("all winning situation is recognized"):
    val startBoard = gameBoard :+ GameCell(Position(1, 1), player)
    ((startBoard :+ GameCell(Position(0, 1), player)) :+ GameCell(Position(2, 1), player)).won shouldBe true
    ((startBoard :+ GameCell(Position(1, 0), player)) :+ GameCell(Position(1, 2), player)).won shouldBe true
    ((startBoard :+ GameCell(Position(0, 0), player)) :+ GameCell(Position(2, 2), player)).won shouldBe true
    ((startBoard :+ GameCell(Position(0, 2), player)) :+ GameCell(Position(2, 0), player)).won shouldBe true

  test("randomAI make move"):
    val board = GameBoard.randomAI(gameBoard, player)
    GameBoard.findPlayer(board, player) shouldBe defined

  test("smartAI make move"):
    val board = GameBoard.smartAI(gameBoard, player)
    GameBoard.findPlayer(board, player) shouldBe defined
