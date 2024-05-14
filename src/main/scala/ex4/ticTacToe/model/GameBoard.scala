package ex4.ticTacToe.model

import ex4.commons.GameCommons
import ex4.commons.GameCommons.{Board, GameCell, Player, Position}

import scala.annotation.targetName
import scala.util.Random

trait GameBoard:
  @targetName("Add")
  def :+(gameCell: GameCell): GameBoard
  def size: Int
  def find(position: Position): Option[Player]
  def available(position: Position): Boolean
  def won: Boolean

object GameBoard:
  Random.setSeed(1234)

  type Game = Seq[GameBoard]
  
  val bound = 2

  def apply(): GameBoard = GameBoardImpl()

  def findPlayer(board: GameBoard, p: Player): Option[Player] =
    val playerPlaced =
      for
        position <- GameBoard.generatePositions()
        player <- board.find(position)
        if player == p
      yield player
    playerPlaced.headOption
    
  def randomAI(gameBoard: GameBoard, player: Player): GameBoard =
    generateMoves(gameBoard, player, Random.shuffle(0 to bound)).headOption
      .map(gc => gameBoard :+ gc)
      .getOrElse(gameBoard)

  def generatePositions(generator: Seq[Int] = 0 to bound): Seq[Position] =
    for
      x <- generator
      y <- generator
    yield Position(x, y)

  private def generateMoves(gameBoard: GameBoard, player: Player, generator: Seq[Int] = 0 to bound): Seq[GameCell] =
    for
      position <- generatePositions(generator)
      if gameBoard.available(position)
    yield GameCell(position, player)

  private def evaluate(gameBoard: GameBoard, player: Player, evalFunction: GameBoard => Int)(startEval: Int, compare: (Int, Int) => Boolean): (Int, GameBoard) =
    var bestMove = gameBoard
    var bestEval = startEval
    for
      newMove <- generateMoves(gameBoard, player)
      newBoard = gameBoard :+ newMove
    do
      val eval = evalFunction(newBoard)
      if compare(eval, bestEval) then { bestEval = eval; bestMove = newBoard }
    (bestEval, bestMove)

  private def minimax(gameBoard: GameBoard, maxPlayer: Boolean, player: Player, depth: Int): (Int, GameBoard) = (gameBoard, depth) match
    case (board, _) if board.won =>
      if maxPlayer then (-1, board) else (1, board)
    case (_, 0) => (0, gameBoard)
    case _ =>
      val evalFunction = (newBoard: GameBoard) =>
        minimax(newBoard, !maxPlayer, player.other, depth - 1)._1
      val evaluationSetup = evaluate(gameBoard, player, evalFunction)
      if maxPlayer then evaluationSetup(Int.MinValue, _ > _)
      else evaluationSetup(Int.MaxValue, _ < _)
      
  def doAllPossibleMove(gameBoard: GameBoard, player: Player): Seq[GameBoard] =
    generateMoves(gameBoard, player).map(gameBoard :+ _)
    
  def computeAnyGame(player: Player, moves: Int): LazyList[Game] = moves match
    case 0 => LazyList(Seq(GameBoard()))
    case _ =>
      for
        game <- computeAnyGame(player.other, moves - 1)
        lastBoard = game.headOption
        if lastBoard.isDefined && !lastBoard.get.won
        newBoard <- doAllPossibleMove(lastBoard.get, player)
      yield newBoard +: game
    
  def smartAI(gameBoard: GameBoard, player: Player): GameBoard =
    minimax(gameBoard, true, player, 4)._2

  private case class GameBoardImpl(private val board: Board = Seq[GameCell]()) extends GameBoard:

    @targetName("Add")
    override def :+(gameCell: GameCell): GameBoard =
      GameBoardImpl(board :+ gameCell)

    override def size: Int =
      board.size
    
    override def find(position: Position): Option[Player] =
      val player = for
        move <- board
        if move.position == position
      yield move.player
      player.headOption

    override def available(position: Position): Boolean =
      find(position: Position).isEmpty

    override def won: Boolean =
      val r = for
        position <- generatePositions()
        player = find(position)
        if player.isDefined
        if existWinningCombination(position.x, position.y, player.get)
      yield true
      r.contains(true)

    private def existWinningCombination(x: Int, y: Int, player: Player): Boolean =
      find(Position(x - 1, y)).contains(player) && find(Position(x + 1, y)).contains(player)
        || find(Position(x, y - 1)).contains(player) && find(Position(x, y + 1)).contains(player)
        || find(Position(x - 1, y - 1)).contains(player) && find(Position(x + 1, y + 1)).contains(player)
        || find(Position(x - 1, y + 1)).contains(player) && find(Position(x + 1, y - 1)).contains(player)

    override def toString: String =
      var info = ""
      for
        y <- bound to 0 by -1
        x <- 0 to bound
      do
        if x != 0 then info += " | "
        info += find(Position(x, y)).map(_.toString).getOrElse(" ")
        if x == bound then 
          info += "\n"; 
          if y != 0 then  
            for i <- 0 to bound do info += "-- "
            info += "\n"
      info
