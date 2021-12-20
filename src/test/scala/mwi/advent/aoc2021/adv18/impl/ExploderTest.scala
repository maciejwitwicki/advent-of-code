package mwi.advent.aoc2021.adv18.impl

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ExploderTest extends AnyFlatSpec with Matchers {

  "Exploder" should "correctly add values to two-digit numbers on the right" in {
    // given
    // when

    val resultOpt = Exploder.explode("[[[[0,[6,7]]]],[10,2]]")

    // then
    resultOpt shouldEqual Some("[[[[6,0]]],[17,2]]")

  }

  it should "correctly add values to two-digit numbers on the left" in {
    // given
    // when

    val resultOpt = Exploder.explode("[[[[10,[6,7]]]],[1,2]]")

    // then
    resultOpt shouldEqual Some("[[[[16,0]]],[8,2]]")

  }

}
