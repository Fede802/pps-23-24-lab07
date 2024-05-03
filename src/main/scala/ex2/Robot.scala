package ex2

type Position = (Int, Int)
enum Direction:
  case North, East, South, West
  def turnRight: Direction = this match
    case Direction.North => Direction.East
    case Direction.East => Direction.South
    case Direction.South => Direction.West
    case Direction.West => Direction.North

  def turnLeft: Direction = this match
    case Direction.North => Direction.West
    case Direction.West => Direction.South
    case Direction.South => Direction.East
    case Direction.East => Direction.North

trait Robot:
  def position: Position
  def direction: Direction
  def turn(dir: Direction): Unit
  def act(): Unit

class SimpleRobot(var position: Position, var direction: Direction) extends Robot:
  def turn(dir: Direction): Unit = direction = dir
  def act(): Unit = position = direction match
    case Direction.North => (position._1, position._2 + 1)
    case Direction.East => (position._1 + 1, position._2)
    case Direction.South => (position._1, position._2 - 1)
    case Direction.West => (position._1 - 1, position._2)
  override def toString: String = s"robot at $position facing $direction"

class DumbRobot(val robot: Robot) extends Robot:
  export robot.{position, direction, act}
  override def turn(dir: Direction): Unit = {}
  override def toString: String = s"${robot.toString} (Dump)"

class LoggingRobot(val robot: Robot) extends Robot:
  export robot.{position, direction, turn}
  override def act(): Unit =
    robot.act()
    println(robot.toString)

class RobotWithBattery(val robot: Robot, private var _batteryUsage: Int = 0, private var _batteryLevel: Int = 0) extends Robot:
  export robot.{position, direction}

  private def charged: Boolean =
    _batteryLevel >= _batteryUsage

  private def discharge(): Unit =
    _batteryLevel -= _batteryUsage

  def batteryUsage: Int = _batteryUsage

  def batteryLevel: Int = _batteryLevel

  override def turn(dir: Direction): Unit =
    if charged then
      robot.turn(dir)
      discharge()

  override def act(): Unit =
    if charged then
      robot.act()
      discharge()

class RobotCanFail(val robot: Robot, val failProbability: Double = 0, val seed: Int = 1234) extends Robot:
  export robot.{position, direction}

  import scala.util.Random
  private val random = Random(seed)

  private def available: Boolean =
    random.nextDouble()*100 > failProbability

  override def turn(dir: Direction): Unit =
    if available then
      robot.turn(dir)

  override def act(): Unit =
    if available then
      robot.act()

class RobotRepeated(val robot: Robot, val numberOfRepetition: Int = 0) extends Robot:
  export robot.{position, direction}

  override def turn(dir: Direction): Unit =
    for
      _ <- 0 until numberOfRepetition
    do robot.turn(dir)

  override def act(): Unit =
    for
      _ <- 0 until numberOfRepetition
    do robot.act()
