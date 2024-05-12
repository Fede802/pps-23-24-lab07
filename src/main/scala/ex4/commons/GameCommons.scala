package ex4.commons

object GameCommons:
  
  case class Position(x: Int, y: Int)
  
  case class GameCell(position: Position, player: Player)
  
  type Board = Seq[GameCell]
  
  enum GameType:
    case RANDOM, SMART, MULTIPLAYER

  enum Player:
    case X, O
    def other: Player = this match
      case X => O
      case _ => X


