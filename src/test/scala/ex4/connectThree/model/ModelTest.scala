package ex4.connectThree.model

import ex4.commons.GameCommons.Player.X
import ex4.commons.GameCommons.{Board, GameCell, Player, Position}
import ex4.connectThree.model.Model.{bound, computeAnyGame, find, firstAvailableRow, placeAnyDisk, printBoards, randomAI, smartAI, won}
import org.scalatest.matchers.should.Matchers.*
import scala.math.pow

class ModelTest extends org.scalatest.funsuite.AnyFunSuite:

  private val player = Player.X
  private val rowSize = (0 to bound).size

  private def fillRow(y: Int, p: Player): Board =
    for
      x <- 0 to bound
    yield GameCell(Position(x, y), p)

  test("find player given a position"):
    find(List(GameCell(Position(0, 0), Player.X)), 0, 0) shouldBe Some(Player.X)
    find(List(GameCell(Position(0, 0), Player.X), GameCell(Position(0, 1), Player.O), GameCell(Position(0, 2), Player.X)), 0, 1) shouldBe Some(Player.O)
    find(List(GameCell(Position(0, 0), Player.X), GameCell(Position(0, 1), Player.O), GameCell(Position(0, 2), Player.X)), 1, 1) shouldBe None
    
  test("firstAvailableRow given a column"):
    firstAvailableRow(List(), 0) shouldBe Some(0)
    firstAvailableRow(List(GameCell(Position(0, 0), player)), 0) shouldBe Some(1)
    firstAvailableRow(List(GameCell(Position(0, 0), player), GameCell(Position(0, 1), player)), 0) shouldBe Some(2)
    firstAvailableRow(List(GameCell(Position(0, 0), player), GameCell(Position(0, 1), player), GameCell(Position(0, 2), player)), 0) shouldBe Some(3)
    firstAvailableRow(List(GameCell(Position(0, 0), player), GameCell(Position(0, 1), player), GameCell(Position(0, 2), player), GameCell(Position(0, 3), player)), 0) shouldBe None

  test("placeAnyDisk in empty board"):
    placeAnyDisk(List(), player) should have size rowSize
    placeAnyDisk(List(), player).distinct should have size rowSize

  test("placeAnyDisk in a board without a full empty row"):
    var board: Board = Seq[GameCell]()
    for y <- 0 until bound do board = board ++ fillRow(y, player)
    board = board :+ GameCell(Position(0, bound), player)
    placeAnyDisk(board, player) should have size (0 until bound).size

  test("placeAnyDisk in a full board"):
    var board: Board = Seq[GameCell]()
    for y <- 0 to bound do board = board ++ fillRow(y, player)
    placeAnyDisk(board, player) should have size 0

  test("computeAnyGame given a player and number of moves"):
    //this test is not general but the function is used with maxMoves in input = 4
    //TODO generalize
    computeAnyGame(player, 0) should have size 1 //empty board
    computeAnyGame(player, 1) should have size rowSize
    computeAnyGame(player, 4) should have size pow(rowSize, 4).toInt
    computeAnyGame(player, 4).distinct should have size pow(rowSize, 4).toInt

  test("4 or less moves is not enough to win the game"):
    for
      game <- computeAnyGame(player, 4)
      board <- game
    do
      won(board) shouldBe false

  test("all winning situation is recognized"):
    val startBoard: Board = Seq(GameCell(Position(1, 1), player))
    won(startBoard ++ Seq(GameCell(Position(0, 1), player), GameCell(Position(2, 1), player))) shouldBe true
    won(startBoard ++ Seq(GameCell(Position(1, 0), player), GameCell(Position(1, 2), player))) shouldBe true
    won(startBoard ++ Seq(GameCell(Position(0, 0), player), GameCell(Position(2, 2), player))) shouldBe true
    won(startBoard ++ Seq(GameCell(Position(0, 2), player), GameCell(Position(2, 0), player))) shouldBe true

  test("randomAI make move"):
    val board = randomAI(Seq[GameCell](), player)
    board.find(_.player == player) shouldBe defined

  test("smartAI make move"):
    val board = smartAI(Seq[GameCell](), player)
    board.find(_.player == player) shouldBe defined