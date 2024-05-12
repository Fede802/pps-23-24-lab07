package ex4.connectThree.model

import ex4.commons.GameCommons.{GameCell, Player, Position}
import scala.util.Random

object Model:

  val bound = 3
  
  /**
   * Board:
   * y
   *
   * 3
   * 2
   * 1
   * 0
   *   0 1 2 3 <-- x
   */

  type Board = Seq[GameCell]

  type Game = Seq[Board]
  
  def find(board: Board, x: Int, y: Int): Option[Player] =
    val player = for
      disk <- board
      if disk.position.x == x && disk.position.y == y
    yield disk.player
    player.headOption

  def firstAvailableRow(board: Board, x: Int): Option[Int] =
    var freeY = Option.empty[Int]
    if x >= 0 && x <= bound
      then freeY =
        (for
          y <- 0 to bound
          if find(board, x, y).isEmpty
        yield y).headOption
    freeY

  def placeAnyDisk(board: Board, player: Player): Seq[Board] =
    for
      x <- 0 to bound
      y = firstAvailableRow(board, x)
      if y.isDefined
    yield board :+ GameCell(Position(x, y.get), player)

  def computeAnyGame(player: Player, moves: Int): LazyList[Game] = moves match
     case 0 => LazyList(List())
     case _ =>
        for
          game <- computeAnyGame(player.other, moves - 1)
          lastBoard = game.headOption.getOrElse(List())
          if !won(lastBoard)
          newBoard <- placeAnyDisk(lastBoard, player)
        yield newBoard +: game

  def won(board: Board): Boolean =
    val r = for
        x <- 0 to bound
        y <- 0 to bound
        player = find(board, x, y)
        if player.isDefined
        if existWinningCombination(board, x, y, player.get)
    yield true
    r.contains(true)  //r.headOption.getOrElse(false)

  def existWinningCombination(board: Board, x: Int, y: Int, player: Player): Boolean =
    find(board, x + 1, y).contains(player) && find(board, x + 2, y).contains(player)
    || find(board, x, y + 1).contains(player) && find(board, x, y + 2).contains(player)
    || find(board, x + 1, y + 1).contains(player) && find(board, x + 2, y + 2).contains(player)
    || find(board, x - 1, y + 1).contains(player) && find(board, x - 2, y + 2).contains(player)

  Random.setSeed(1234)
  def randomAI(board: Board, player: Player): Board =
    val columns = Random.shuffle(0 to bound).toList
    val moves =
      for
        x <- columns
        y = firstAvailableRow(board, x)
        if y.isDefined
      yield GameCell(Position(x, y.get), player)
    moves.headOption.map(m => board :+ m).getOrElse(board)

  def generateMoves(board: Board, player: Player): Seq[Board] =
    for
      x <- 0 to bound
      y = firstAvailableRow(board, x)
      if y.isDefined
    yield board :+ GameCell(Position(x, y.get), player)

  private def evaluate(board: Board, player: Player, evalFunction: Board => Int)(startEval: Int,compare: (Int, Int) => Boolean): (Int, Board) =
    var bestMove = board
    var bestEval = startEval
    for newBoard <- generateMoves(board, player) do
      val eval = evalFunction(newBoard)
      if compare(eval, bestEval) then {bestEval = eval; bestMove = newBoard}
    (bestEval, bestMove)

  def minimax(board: Board, maxPlayer: Boolean, player: Player, depth: Int): (Int, Board) = (board, depth) match
    case (board, _) if won(board) => if maxPlayer then (-1, board) else (1, board)
    case (_, 0) => (0, board)
    case _ =>
      val evalFunction = (newBoard: Board) => minimax(newBoard, !maxPlayer, player.other, depth - 1)._1
      val evaluationSetup = evaluate(board, player, evalFunction)
      if maxPlayer then
        evaluationSetup(Int.MinValue, _ > _)
      else
        evaluationSetup(Int.MaxValue, _ < _)

  def smartAI(board: Board, player: Player): Board =
    val (_, newBoard) = minimax(board, true, player, 4)
    newBoard

  def printBoards(game: Seq[Board]): Unit =
    println(boardInfo(game))

  def boardInfo(game: Seq[Board]): String =
    var info = ""
    for
      y <- bound to 0 by -1
      board <- game.reverse
      x <- 0 to bound
    do
      info += find(board, x, y).map(_.toString).getOrElse(".")
      if x == bound then
        info += " "
        if board == game.head then info += "\n"
    info
