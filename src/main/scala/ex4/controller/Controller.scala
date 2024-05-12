package ex4.controller

import ex4.model.{ConnectThree, Player}
import ex4.model.ConnectThree.{Board, Disk, firstAvailableRow, printBoards, randomAI, smartAI, won}
import ex4.view.menu.GameType

trait Controller:
  def setupGame(gameType: GameType): Unit
  def updateGame(column: Int): Unit
  def firstAvailableRow(column: Int): Option[Int]
  def currentPlayer(): Player
  def find(x: Int, y: Int): Option[Player]


class ControllerImpl extends Controller{

  private var autoUpdate = () => ()
  private var board: Board = Seq[Disk]()
  private var _currentPlayer = Player.X

  def setupGame(gameType: GameType): Unit =
    board = Seq()
    gameType match
      case GameType.RANDOM => {println("Random game"); autoUpdate = () => randomAI(board, _currentPlayer)}
      case GameType.SMART => {println("Smart game"); autoUpdate = () => smartAI(board, _currentPlayer)}
      case GameType.MULTIPLAYER => {println("Multiplayer game"); autoUpdate = () => ()}

  def updateGame(column: Int): Unit = {
    val row = ConnectThree.firstAvailableRow(board, column).get
    board = board :+ Disk(column, row, _currentPlayer)
    _currentPlayer = _currentPlayer.other()
    autoUpdate()
  }

  def firstAvailableRow(column: Int): Option[Int] = ConnectThree.firstAvailableRow(board, column)

  def currentPlayer(): Player = _currentPlayer

  override def find(x: Int, y: Int): Option[Player] = ConnectThree.find(board,x, y)
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

}
