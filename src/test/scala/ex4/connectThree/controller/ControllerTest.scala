package ex4.connectThree.controller

import ex4.commons.GameCommons.{GameType, Player}
import org.scalatest.matchers.should.Matchers.*

class ControllerTest extends org.scalatest.funsuite.AnyFunSuite:

  private val startPlayer = Controller.currentPlayer

  test("playWithRandomAI"):
    Controller.setupGame(GameType.RANDOM)
    Controller.updateGame(0)
    Controller.board should have size 2
    Controller.board.find(_.player == startPlayer) shouldBe defined
    Controller.board.find(_.player == startPlayer.other) shouldBe defined

  test("playWithSmartAI"):
    Controller.setupGame(GameType.SMART)
    Controller.updateGame(0)
    Controller.board should have size 2
    Controller.board.find(_.player == startPlayer) shouldBe defined
    Controller.board.find(_.player == startPlayer.other) shouldBe defined

  test("multiplayer"):
    Controller.setupGame(GameType.MULTIPLAYER)
    Controller.updateGame(0)
    Controller.board should have size 1
    Controller.board.find(_.player == startPlayer) shouldBe defined
    Controller.updateGame(0)
    Controller.board should have size 2
    Controller.board.find(_.player == startPlayer.other) shouldBe defined
