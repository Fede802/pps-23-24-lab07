package ex4.connectThree.controller

import ex4.connectThree.model.Model
import Model.{firstAvailableRow, randomAI, smartAI, won}
import ex4.commons.GameCommons.{Board, GameCell, GameType, Player, Position}

object Controller:

  private val switchPlayer = () => {_currentPlayer = _currentPlayer.other; board}
  private var board: Board = Seq[GameCell]()
  private var autoUpdate = switchPlayer
  private var _currentPlayer = Player.X

  def setupGame(gameType: GameType): Unit =
    board = Seq()
    gameType match
      case GameType.RANDOM => autoUpdate = () => randomAI(board, _currentPlayer.other)
      case GameType.SMART => autoUpdate = () => smartAI(board, _currentPlayer.other)
      case GameType.MULTIPLAYER => autoUpdate = switchPlayer

  def updateGame(column: Int): Unit = {
    val row = Model.firstAvailableRow(board, column).get
    board = board :+ GameCell(Position(column, row), _currentPlayer)
    board = autoUpdate()
  }

  def available(column: Int): Option[Int] = Model.firstAvailableRow(board, column)

  def currentPlayer: Player = _currentPlayer

  def won: Boolean = Model.won(board)

  def boardInfo: String = Model.boardInfo(Seq(board))
