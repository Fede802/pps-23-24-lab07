package ex4.ticTacToe.controller

import ex4.commons.GameCommons.{GameType, Player, Position}
import ex4.ticTacToe.model.GameBoard
import org.scalatest.matchers.should.Matchers.*

class ControllerTest extends org.scalatest.funsuite.AnyFunSuite:

  private val controller = Controller()
  private val startPlayer = controller.currentPlayer

  test("playWithRandomAI mode"):
    controller.setupGame(GameType.RANDOM)
    controller.updateGame(Position(0,0)) shouldBe true
    controller.board should have size 2
    GameBoard.findPlayer(controller.board, startPlayer) shouldBe defined
    GameBoard.findPlayer(controller.board, startPlayer.other) shouldBe defined

  test("playWithSmartAI mode"):
    controller.setupGame(GameType.SMART)
    controller.updateGame(Position(0,0)) shouldBe true
    controller.board should have size 2
    GameBoard.findPlayer(controller.board, startPlayer) shouldBe defined
    GameBoard.findPlayer(controller.board, startPlayer.other) shouldBe defined

  test("multiplayer mode"):
    controller.setupGame(GameType.MULTIPLAYER)
    controller.updateGame(Position(0,0)) shouldBe true
    controller.board should have size 1
    GameBoard.findPlayer(controller.board, startPlayer) shouldBe defined
    controller.updateGame(Position(0,1)) shouldBe true
    controller.board should have size 2
    GameBoard.findPlayer(controller.board, startPlayer.other) shouldBe defined

  test("is not possible to choose the same cell in multiplayer mode"):
    controller.setupGame(GameType.MULTIPLAYER)
    controller.updateGame(Position(0, 0)) shouldBe true
    controller.updateGame(Position(0, 0)) shouldBe false
    controller.board should have size 1
    GameBoard.findPlayer(controller.board, startPlayer) shouldBe defined
    GameBoard.findPlayer(controller.board, startPlayer.other) should not be defined
    controller.currentPlayer shouldBe startPlayer.other

