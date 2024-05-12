package ex4.connectThree.view



import ex4.commons.GameCommons.GameType
import ex4.connectThree.controller.Controller
import ex4.connectThree.model.Model

import scala.annotation.tailrec
import scala.io.StdIn

object View:

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
      case "1" => Controller.setupGame(GameType.RANDOM)
      case "2" => Controller.setupGame(GameType.SMART)
      case "3" => Controller.setupGame(GameType.MULTIPLAYER)
      case "4" => {println("Bye!"); System.exit(0)}
      case _ => {println("Wrong Input, retry"); waitUserMenuSelection()}

  private def playGame(): Unit =
    while(!Controller.won())
      println(s"Player ${Controller.currentPlayer()} turn")
      println(Controller.boardInfo)
      Controller.updateGame(selectColumn())
    println(s"Game Ended")
    println(Controller.boardInfo)

  private def selectColumn(): Int =
    var col = -1
    while(Controller.available(col).isEmpty)
      println(s"Insert column [0 to ${Model.bound}]")
      val in = StdIn.readLine()
      if in.toIntOption.isDefined then col = in.toInt
    col
