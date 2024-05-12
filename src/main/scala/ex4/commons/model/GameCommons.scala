package ex4.commons

object GameCommons {
  
  case class Cell(x: Int, y: Int, player: Player)
  
  type Board = Seq[Cell]
  
  enum GameType:
    case RANDOM, SMART, MULTIPLAYER

  enum Player:
    case X, O

    def other: Player = this match
      case X => O
      case _ => X

}
