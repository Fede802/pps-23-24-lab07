package ex4.connectThree

import ex4.connectThree.model.Model.{Disk, Player, find, firstAvailableRow}
import org.scalatest.matchers.should.Matchers.*

class ConnectThreeTest extends org.scalatest.funsuite.AnyFunSuite:

  test("find"):
    find(List(Disk(0, 0, Player.X)), 0, 0) shouldBe Some(Player.X)
    find(List(Disk(0, 0, Player.X), Disk(0, 1, Player.O), Disk(0, 2, Player.X)), 0, 1) shouldBe Some(Player.O)
    find(List(Disk(0, 0, Player.X), Disk(0, 1, Player.O), Disk(0, 2, Player.X)), 1, 1) shouldBe None
    
  test("firstAvailableRow"):
    firstAvailableRow(List(), 0) shouldBe Some(0)
    firstAvailableRow(List(Disk(0, 0, Player.X)), 0) shouldBe Some(1)
    firstAvailableRow(List(Disk(0, 0, Player.X), Disk(0, 1, Player.X)), 0) shouldBe Some(2)
    firstAvailableRow(List(Disk(0, 0, Player.X), Disk(0, 1, Player.X), Disk(0, 2, Player.X)), 0) shouldBe Some(3)
    firstAvailableRow(List(Disk(0, 0, Player.X), Disk(0, 1, Player.X), Disk(0, 2, Player.X), Disk(0, 3, Player.X)), 0) shouldBe None
  
    