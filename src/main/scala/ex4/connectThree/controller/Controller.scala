package ex4.connectThree.controller

import ex4.connectThree.model.Model
import Model.{Board, Disk, firstAvailableRow, printBoards, randomAI, smartAI, won}
import ex4.commons.GameCommons.{GameType, Player}


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

  def available(column: Int): Option[Int] = Model.firstAvailableRow(board, column)

  def currentPlayer(): Player = _currentPlayer

  def find(x: Int, y: Int): Option[Player] = Model.find(board,x, y)

  def won(): Boolean = Model.won(board)

  def boardInfo: String = Model.boardInfo(Seq(board))

