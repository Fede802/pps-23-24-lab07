package ex4.ticTacToe.view

import ex4.commons.GameCommons.{GameType, Position}
import ex4.ticTacToe.controller.Controller
import ex4.ticTacToe.model.GameBoard

import scala.annotation.tailrec
import scala.io.StdIn

object View:

  val controller = Controller()

  def showMenu(): Unit =
    println("1. Play With RandomAI")
    println("2. Play With SmartAI")
    println("3. Multiplayer")
    println("4. Quit")
    println("Enter a selection: ")
    waitUserMenuSelection()
    playGame()

  @tailrec
  private def waitUserMenuSelection(): Unit =
    StdIn.readLine() match
      case "1" => controller.setupGame(GameType.RANDOM)
      case "2" => controller.setupGame(GameType.SMART)
      case "3" => controller.setupGame(GameType.MULTIPLAYER)
      case "4" => {println("Bye!"); System.exit(0)}
      case _ => {println("Wrong Input, retry"); waitUserMenuSelection()}

  private def playGame(): Unit =
    while (!controller.won)
      println(s"Player ${controller.currentPlayer} turn")
      println(controller.gameInfo)
      controller.updateGame(selectCell())
    println(s"Game Ended")
    println(controller.gameInfo)

  private def selectCell(): Position =
    var col = -1
    var row = -1
    while(row == -1)
      println(s"Insert row [0 to ${GameBoard.bound}]")
      val in = StdIn.readLine()
      if in.toIntOption.isDefined then row = in.toInt
    while(col == -1)
      println(s"Insert column [0 to ${GameBoard.bound}]")
      val in = StdIn.readLine()
      if in.toIntOption.isDefined then col = in.toInt
    Position(row, col)
