package ex4.model

import scala.util.Random

object Model:
  val bound = 3

  enum GameType:
    case RANDOM, SMART, MULTIPLAYER

  enum Player:
    case X, O
    def other: Player = this match
      case X => O
      case _ => X

  trait ConnectThree:
    def find(x: Int, y: Int): Option[Player]
    def firstAvailableRow(x: Int): Option[Int]
    def placeAnyDisk(player: Player): Seq[Board]
    def won: Boolean
    def randomAI(player: Player): Board
    def smartAI(player: Player): Board
    def generateMoves(player: Player): Seq[Board]
    def minimax(maxPlayer: Boolean, player: Player, depth: Int): (Int, Board)
    def printBoards(): Unit
    def boardInfo: String

  case class Disk(x: Int, y: Int, player: Player)
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
  type Board = Seq[Disk]
  type Game = Seq[Board]

  import Player.*

  def find(board: Board, x: Int, y: Int): Option[Player] =
    val player = for
      disk <- board
      if disk.x == x && disk.y == y
    yield disk.player
    player.headOption

  def firstAvailableRow(board: Board, x: Int): Option[Int] =
    var freeY = Option.empty[Int]
    if x >= 0 && x <= bound
      then freeY =
        (for
          y <- 0 to bound
          if !board.exists(disk => disk.x == x && disk.y == y)
        yield y).headOption
    freeY

  def placeAnyDisk(board: Board, player: Player): Seq[Board] =
    for
      x <- 0 to bound
      y = firstAvailableRow(board, x)
      if y.isDefined
    yield board :+ Disk(x, y.get, player)

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
      yield Disk(x, y.get, player)
    moves.headOption.map(m => board :+ m).getOrElse(board)

  def generateMoves(board: Board, player: Player): Seq[Board] =
    for
      x <- 0 to bound
      y = firstAvailableRow(board, x)
      if y.isDefined
    yield board :+ Disk(x, y.get, player)

  def minimax(board: Board, maxPlayer: Boolean, player: Player, depth: Int): (Int, Board) = (board, depth) match
    case (board, _) if won(board) => if maxPlayer then (-1, board) else (1, board)
    case (_, 0) => (0, board)
    case _ =>
      var bestMove = board
      if maxPlayer then
        var maxEval = Int.MinValue
//        println(s"depth: ${depth} MAX startEval")
        for
          newBoard <- generateMoves(board, player)
        do
          printBoards(Seq(newBoard))
          val eval = minimax(newBoard, false, player.other, depth - 1)
//          println(s"returned ${eval._1}")
          val newEval = Math.max(maxEval, eval._1)
            if newEval > maxEval then
                maxEval = newEval
                bestMove = newBoard

//        println(s"depth: ${depth} MAX maxEval: ${maxEval}")
        printBoards(Seq(bestMove))
//        println(s"depth: ${depth} MAX endEval")
        (maxEval, bestMove)
      else
        var minEval = Int.MaxValue
//        println(s"depth: ${depth} MIN startEval")
        for
          newBoard <- generateMoves(board, player)
        do
          printBoards(Seq(newBoard))
          val eval = minimax(newBoard, true, player.other, depth - 1)
          val newEval = Math.min(minEval, eval._1)
            if newEval < minEval then
                minEval = newEval
                bestMove = newBoard
//        println(s"depth: ${depth} MIN minEval: ${minEval}")
        printBoards(Seq(bestMove))
//        println(s"depth: ${depth} MIN endEval")
        (minEval, bestMove)

  def smartAI(board: Board, player: Player): Board =
    val (_, newBoard) = minimax(board, true, player, 4)
    newBoard

  def printBoards(game: Seq[Board]): Unit =
    for
      y <- bound to 0 by -1
      board <- game.reverse
      x <- 0 to bound
    do
      print(find(board, x, y).map(_.toString).getOrElse("."))
      if x == bound then
        print(" ")
        if board == game.head then println()

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


