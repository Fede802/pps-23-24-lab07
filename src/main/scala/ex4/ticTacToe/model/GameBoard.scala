package ex4.ticTacToe.model

import ex4.commons.GameCommons
import ex4.commons.GameCommons.{Board, Cell, Player}

import scala.util.Random



trait GameBoard:
  def add(x: Int, y: Int, player: Player): GameBoard
  def find(x: Int, y: Int): Option[Player]
  def available(x: Int, y: Int): Boolean
  def won: Boolean

object GameBoard:
  Random.setSeed(1234)
  val bound = 2

  def apply(): GameBoard = new GameBoardImpl()
  def randomAI(gameBoard: GameBoard, player: Player): GameBoard =
    val columns = Random.shuffle(0 to bound).toList
    val rows = Random.shuffle(0 to bound).toList
    val moves =
      for
        x <- columns
        y <- rows
        if gameBoard.available(x, y)
      yield (x, y, player)
    moves.headOption.map(m => gameBoard.add(m._1, m._2, m._3)).getOrElse(gameBoard)

  def generateMoves(gameBoard: GameBoard, player: Player): Seq[GameBoard] =
    for
      x <- 0 to bound
      y <- 0 to bound
      if gameBoard.available(x, y)
    yield gameBoard.add(x, y, player)

  def minimax(gameBoard: GameBoard, maxPlayer: Boolean, player: Player, depth: Int): (Int, GameBoard) = (gameBoard, depth) match
    case (board, _) if board.won => if maxPlayer then (-1, board) else (1, board)
    case (_, 0) => (0, gameBoard)
    case _ =>
      var bestMove = gameBoard
      if maxPlayer then
        var maxEval = Int.MinValue
        for
          newBoard <- generateMoves(gameBoard, player)
        do
          val eval = minimax(newBoard, false, player.other, depth - 1)
          val newEval = Math.max(maxEval, eval._1)
          if newEval > maxEval then
            maxEval = newEval
            bestMove = newBoard
        (maxEval, bestMove)
      else
        var minEval = Int.MaxValue
        for
          newBoard <- generateMoves(gameBoard, player)
        do
          val eval = minimax(newBoard, true, player.other, depth - 1)
          val newEval = Math.min(minEval, eval._1)
          if newEval < minEval then
            minEval = newEval
            bestMove = newBoard
        (minEval, bestMove)

  def smartAI(gameBoard: GameBoard, player: Player): GameBoard =
    val (_, newBoard) = minimax(gameBoard, true, player, 4)
    newBoard

  private class GameBoardImpl(private val board: Board = Seq[Cell]()) extends GameBoard:
    override def add(x: Int, y:Int, player: Player): GameBoard =
      new GameBoardImpl(board :+ Cell(x, y, player))

    override def find(x: Int, y: Int): Option[Player] =
      val player = for
        move <- board
        if move.x == x && move.y == y
      yield move.player
      player.headOption

    override def available(x: Int, y: Int): Boolean = find(x, y).isEmpty

    override def won: Boolean =
      val r = for
        x <- 0 to bound
        y <- 0 to bound
        player = find(x, y)
        if player.isDefined
        if existWinningCombination(x, y, player.get)
      yield true
      r.contains(true)

    private def existWinningCombination(x: Int, y: Int, player: Player): Boolean =
      find(x-1,y).contains(player) && find(x+1,y).contains(player)
      || find(x,y-1).contains(player) && find(x,y+1).contains(player)
      || find(x-1,y-1).contains(player) && find(x+1,y+1).contains(player)
      || find(x-1,y+1).contains(player) && find(x+1,y-1).contains(player)

    override def toString: String =
      var info = ""
      for
        y <- bound to 0 by -1
        x <- 0 to bound
      do
        info += find(x,y).map(_.toString).getOrElse(" ")
        if x == bound then info += "\n"
      info


