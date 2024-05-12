package ex4.ticTacToe.controller

import ex4.commons.GameCommons.{GameType, Player}
import ex4.connectThree.model.Model
import ex4.connectThree.model.Model.*
import ex4.ticTacToe.model.GameBoard

trait Controller:
  def setupGame(gameType: GameType): Unit
  def updateGame(x: Int, y: Int): Boolean
  def available(x: Int, y: Int): Boolean
  def currentPlayer: Player
  def playerIn(x: Int, y: Int): Option[Player]
  def won: Boolean
  def gameInfo: String

object Controller:
  def apply(): Controller = new ControllerImpl()
  private class ControllerImpl extends Controller:
    private val switchPlayer = () => {_currentPlayer = _currentPlayer.other; board}
    private var board = GameBoard()
    private var autoUpdate = switchPlayer
    private var _currentPlayer = Player.X

    override def setupGame(gameType: GameType): Unit =
      board = GameBoard()
        gameType match
          case GameType.RANDOM => {println("Random game"); autoUpdate = () => GameBoard.randomAI(board, _currentPlayer.other)}
          case GameType.SMART => {println("Smart game"); autoUpdate = () => GameBoard.smartAI(board, _currentPlayer.other)}
          case GameType.MULTIPLAYER => {println("Multiplayer game"); autoUpdate = switchPlayer}

    override def updateGame(x: Int, y:Int): Boolean =
      val available = board.available(x, y)
      if available then
        board.add(x, y, _currentPlayer)
        board = autoUpdate()
      available
    override def available(x: Int, y: Int): Boolean = board.available(x, y)
    override def currentPlayer: Player = _currentPlayer

    override def playerIn(x: Int, y: Int): Option[Player] = board.find(x, y)

    override def won: Boolean = board.won

    override def gameInfo: String = board.toString




