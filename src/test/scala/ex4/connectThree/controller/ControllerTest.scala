package ex4.connectThree.controller

import ex4.commons.GameCommons.{GameType, Player}
import ex4.connectThree.model.Model
import org.scalatest.matchers.should.Matchers.*

class ControllerTest extends org.scalatest.funsuite.AnyFunSuite:

  private val startPlayer = Controller.currentPlayer

  test("playWithRandomAI mode"):
    Controller.setupGame(GameType.RANDOM)
    Controller.updateGame(0) shouldBe true
    Controller.board should have size 2
    Controller.board.find(_.player == startPlayer) shouldBe defined
    Controller.board.find(_.player == startPlayer.other) shouldBe defined

  test("playWithSmartAI mode"):
    Controller.setupGame(GameType.SMART)
    Controller.updateGame(0) shouldBe true
    Controller.board should have size 2
    Controller.board.find(_.player == startPlayer) shouldBe defined
    Controller.board.find(_.player == startPlayer.other) shouldBe defined

  test("multiplayer mode"):
    Controller.setupGame(GameType.MULTIPLAYER)
    Controller.updateGame(0) shouldBe true
    Controller.board should have size 1
    Controller.board.find(_.player == startPlayer) shouldBe defined
    Controller.updateGame(0) shouldBe true
    Controller.board should have size 2
    Controller.board.find(_.player == startPlayer.other) shouldBe defined

  test("is not possible to choose a filled column"):
    Controller.setupGame(GameType.MULTIPLAYER)
    var currentPlayer = startPlayer
    for
      x <- 0 to Model.bound
    do
      Controller.updateGame(0) shouldBe true
      currentPlayer = currentPlayer.other
    Controller.updateGame(0) shouldBe false
    Controller.board should have size (0 to Model.bound).size
    Controller.board.find(_.player == startPlayer) shouldBe defined
    Controller.board.find(_.player == startPlayer.other) shouldBe defined
    Controller.currentPlayer shouldBe currentPlayer
