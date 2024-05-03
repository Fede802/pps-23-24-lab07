package ex2

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class RobotTest extends AnyFlatSpec with Matchers:

  "A SimpleRobot" should "turn correctly" in:
    val robot = new SimpleRobot((0, 0), Direction.North)

    robot.turn(Direction.East)
    robot.direction should be(Direction.East)

    robot.turn(Direction.South)
    robot.direction should be(Direction.South)

    robot.turn(Direction.West)
    robot.direction should be(Direction.West)

    robot.turn(Direction.North)
    robot.direction should be(Direction.North)

  it should "act correctly" in:
    val robot = new SimpleRobot((0, 0), Direction.North)

    robot.act()
    robot.position should be((0, 1))

    robot.turn(Direction.East)
    robot.act()
    robot.position should be((1, 1))

    robot.turn(Direction.South)
    robot.act()
    robot.position should be((1, 0))

    robot.turn(Direction.West)
    robot.act()
    robot.position should be((0, 0))

  "A RobotWithBattery" should "turn correctly" in :
    val robot = new RobotWithBattery(new SimpleRobot((0, 0), Direction.North), 10 , 100)

    for
      _ <- 1 to 10
    do
      robot.turn(Direction.East)
      robot.direction should be(Direction.East)

    robot.turn(Direction.South)
    robot.direction should be(Direction.East)

  it should "act correctly" in :
    val robot = new RobotWithBattery(new SimpleRobot((0, 0), Direction.North), 10 , 100)

    for
      x <- 1 to 10
    do
      robot.act()
      robot.position should be((0, x))

    robot.act()
    robot.position should be((0, 10))

  "A RobotCanFail" should "turn correctly" in :
    val reliableRobot = new RobotCanFail(new SimpleRobot((0, 0), Direction.North), 0)
    val unreliableRobot = new RobotCanFail(new SimpleRobot((0, 0), Direction.North), 100)

    reliableRobot.turn(Direction.East)
    reliableRobot.direction should be(Direction.East)

    unreliableRobot.turn(Direction.East)
    unreliableRobot.direction should be(Direction.North)

  it should "act correctly" in :
    val reliableRobot = new RobotCanFail(new SimpleRobot((0, 0), Direction.North), 0)
    val unreliableRobot = new RobotCanFail(new SimpleRobot((0, 0), Direction.North), 100)


    reliableRobot.act()
    reliableRobot.position should be((0, 1))

    unreliableRobot.act()
    unreliableRobot.position should be((0, 0))

  "A RobotRepeated" should "turn correctly" in :
    val robot = new RobotRepeated(new SimpleRobot((0, 0), Direction.North), 2)

    robot.turn(Direction.East)
    robot.direction should be(Direction.East)



  it should "act correctly" in :
    val robot = new RobotRepeated(new SimpleRobot((0, 0), Direction.North), 2)

    robot.act()
    robot.position should be((0, 2))

