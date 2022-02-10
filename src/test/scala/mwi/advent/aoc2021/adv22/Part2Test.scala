package mwi.advent.aoc2021.adv22

import mwi.advent.aoc2021.adv22.Part2.Cubid
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class Part2Test extends AnyFlatSpec with Matchers {

  it should "find intersections" in {

    val a = Cubid(-10, 10, -9, 9, -8, 8)
    val b = Cubid(-5, 15, -4, 16, -6, -3)

    a.intersectsWith(b) shouldBe true

    val c = Cubid(-10, 10, -9, 9, -8, 8)
    val d = Cubid(-12, -11, -4, 16, -6, -3)

    c.intersectsWith(d) shouldBe false

    Cubid(-10, 10, -9, 9, -8, 8).intersectsWith(Cubid(-12, -10, -4, 16, -6, -3)) shouldBe true

    Cubid(-10, 10, -9, 9, -8, 8).intersectsWith(Cubid(-12, -9, -20, -8, 8, 20)) shouldBe true

  }

  it should "split into subcubids case 1" in {

    val smaller = Cubid(-4, 4, -4, 4, -4, 4)
    val bigger  = Cubid(-10, 10, -10, 10, -10, 10)

    val diff = bigger.cutIntoPartsRemovingIntersectionWith(smaller)

    diff.size shouldBe 26

    val diffVolume = diff.foldLeft(0L)((acc, cub) => {
      acc + cub.volume
    })

    diffVolume shouldBe bigger.volume - smaller.volume

  }

  it should "split into subcubids case 2" in {

    val smaller = Cubid(-4, 4, -4, 4, -4, 4)
    val bigger  = Cubid(-10, 10, -10, 10, -10, 10)

    val diff = smaller.cutIntoPartsRemovingIntersectionWith(bigger)

    diff.size shouldBe 0

    val diffVolume = diff.foldLeft(0L)((acc, cub) => {
      acc + cub.volume
    })

    diffVolume shouldBe bigger.volume - smaller.volume

  }

}
