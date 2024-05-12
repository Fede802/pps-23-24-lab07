package ex4.controller

import ex4.model.Model
import ex4.model.Model.{Board, Disk, GameType, Player, firstAvailableRow, printBoards, randomAI, smartAI, won}


object Controller:

  private val switchPlayer = () => {_currentPlayer = _currentPlayer.other; board}
  private var board: Board = Seq[Disk]()
  private var autoUpdate = switchPlayer
  private var _currentPlayer = Player.X

  def setupGame(gameType: GameType): Unit =
    board = Seq()
    gameType match
      case GameType.RANDOM => {println("Random game"); autoUpdate = () => randomAI(board, _currentPlayer.other)}
      case GameType.SMART => {println("Smart game"); autoUpdate = () => smartAI(board, _currentPlayer.other)}
      case GameType.MULTIPLAYER => {println("Multiplayer game"); autoUpdate = switchPlayer}

  def updateGame(column: Int): Unit = {
    val row = Model.firstAvailableRow(board, column).get
    board = board :+ Disk(column, row, _currentPlayer)
    board = autoUpdate()
  }

  def firstAvailableRow(column: Int): Option[Int] = Model.firstAvailableRow(board, column)

  def currentPlayer(): Player = _currentPlayer

  def find(x: Int, y: Int): Option[Player] = Model.find(board,x, y)

  def won(): Boolean = Model.won(board)

  def boardInfo: String = Model.boardInfo(Seq(board))
//  def autoplay(): Unit =
//    var board: Board = Seq()
//    var turn = true
//    var player = Player.X
//    while (!won(board))
//      println("START TURN")
//      if turn
//      then {
//        printBoards(Seq(board)); board = smartAI(board, player)
//      }
//      else {
//        printBoards(Seq(board)); board = smartAI(board, player.other)
//      }
//      turn = !turn
//      printBoards(Seq(board))
//      println("END TURN")
//
////  autoplay()
//
//
//  def play(): Unit =
//    var board: Board = Seq()
//    var turn = true
//    while (!won(board))
//      println("START TURN")
//      if turn
//      then {
//        printBoards(Seq(board)); board = smartAI(board, Player.X)
//      }
//      else
//        printBoards(Seq(board))
//        var validMove = false
//        var x = -1
//        var y = -1
//        while (!validMove)
//          println("Enter x:")
//          x = scala.io.StdIn.readLine().toInt - 1
//          val opy = firstAvailableRow(board, x)
//          if opy.isDefined
//          then {
//            validMove = true; y = opy.get
//          }
//          else println("Invalid move")
//        board = board :+ Disk(x, y, Player.O)
//      turn = !turn
//      printBoards(Seq(board))
//      println("END TURN")

//  play()

//}
