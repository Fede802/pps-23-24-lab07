package ex4

import java.util.OptionalInt
import scala.util.Random

// Optional!
object ConnectThree extends App:
  val bound = 3
  enum Player:
    case X, O
    def other: Player = this match
      case X => O
      case _ => X

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
    val freeY = for
      y <- 0 to bound
      if !board.exists(disk => disk.x == x && disk.y == y)
    yield y
    freeY.headOption

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

//  def smartAI(board: Board, player: Player): Board =
//    val columns = Random.shuffle(0 to bound).toList
//    val moves =
//      for
//        x <- columns
//        y = firstAvailableRow(board, x)
//        if y.isDefined
//        newBoard = board :+ Disk(x, y.get, player)
//        if !won(newBoard)
//      yield newBoard
//    moves.headOption.getOrElse(board)

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

  // Exercise 1: implement find such that..
  println("EX 1: ")
  println(find(List(Disk(0, 0, X)), 0, 0)) // Some(X)
  println(find(List(Disk(0, 0, X), Disk(0, 1, O), Disk(0, 2, X)), 0, 1)) // Some(O)
  println(find(List(Disk(0, 0, X), Disk(0, 1, O), Disk(0, 2, X)), 1, 1)) // None

  // Exercise 2: implement firstAvailableRow such that..
  println("EX 2: ")
  println(firstAvailableRow(List(), 0)) // Some(0)
  println(firstAvailableRow(List(Disk(0, 0, X)), 0)) // Some(1)
  println(firstAvailableRow(List(Disk(0, 0, X), Disk(0, 1, X)), 0)) // Some(2)
  println(firstAvailableRow(List(Disk(0, 0, X), Disk(0, 1, X), Disk(0, 2, X)), 0)) // Some(3)
  println(firstAvailableRow(List(Disk(0, 0, X), Disk(0, 1, X), Disk(0, 2, X), Disk(0, 3, X)), 0)) // None
  // Exercise 3: implement placeAnyDisk such that..
  printBoards(placeAnyDisk(List(), X))
  // .... .... .... ....
  // .... .... .... ....
  // .... .... .... ....
  // ...X ..X. .X.. X...
  printBoards(placeAnyDisk(List(Disk(bound, 0, O)), X))
  // .... .... .... ....
  // .... .... .... ....
  // ...X .... .... ....
  // ...O ..XO .X.O X..O
  println("EX 4: ")
// Exercise 3 (ADVANCED!): implement computeAnyGame such that..
  var nGames = 0;
  computeAnyGame(O, 4).foreach { g =>
    printBoards(g)
    println()
    nGames = nGames + 1
  }
  println(nGames)
// .... .... .... .... O...
// .... .... .... X... X...
// .... .... O... O... O...
// .... X... X... X... X...
//
//
//
//  .... .... .... .... ...O
//  .... .... .... ...X ...X
//  .... .... ...O ...O ...O
//  .... ...X ...X ...X ...X
//


//
//// Exercise 4 (VERY ADVANCED!) -- modify the above one so as to stop each game when someone won!!
