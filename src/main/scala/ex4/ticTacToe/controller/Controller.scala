package ex4.ticTacToe.controller

import ex4.commons.GameCommons.{GameCell, GameType, Player, Position}
import ex4.connectThree.model.Model
import ex4.connectThree.model.Model.*
import ex4.ticTacToe.model.GameBoard

trait Controller:
  def setupGame(gameType: GameType): Unit
  def updateGame(position: Position): Unit
  def currentPlayer: Player
  def won: Boolean
  def gameInfo: String

object Controller:

  def apply(): Controller = ControllerImpl()

  private case class ControllerImpl() extends Controller:

    private val switchPlayer = () => {_currentPlayer = _currentPlayer.other; board}
    private var board = GameBoard()
    private var autoUpdate = switchPlayer
    private var _currentPlayer = Player.X

    override def setupGame(gameType: GameType): Unit =
      board = GameBoard()
        gameType match
          case GameType.RANDOM => autoUpdate = () => GameBoard.randomAI(board, _currentPlayer.other)
          case GameType.SMART => autoUpdate = () => GameBoard.smartAI(board, _currentPlayer.other)
          case GameType.MULTIPLAYER => autoUpdate = switchPlayer

    override def updateGame(position: Position): Unit =
      board = board :+ GameCell(position, _currentPlayer)
      board = autoUpdate()

    override def currentPlayer: Player = _currentPlayer

    override def won: Boolean = board.won

    override def gameInfo: String = board.toString
