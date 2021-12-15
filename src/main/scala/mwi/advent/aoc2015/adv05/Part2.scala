package mwi.advent.aoc2015.adv05

import mwi.advent.util.Loc

import java.security.MessageDigest
import scala.collection.mutable

object Part2 {

  private val vovels = List('a', 'e', 'i', 'o', 'u')
  private val forbidden = List("ab", "cd", "pq", "xy")

  def solve(input: Array[String]): Unit = {

    var nice = 0
    input.foreach(line => {

      val pairCount = mutable.HashMap.empty[String, Int]

      line.sliding(2, 1).foreach(window => {
        pairCount.updateWith(window)(valueOpt => {
          val prevValue = valueOpt.getOrElse(0)
          Some(prevValue + 1)
        })
      })

      var hasDuplicate = false
      if (pairCount.values.count(i => i > 1) > 0) hasDuplicate = true

      var hasRepeation = false

      line
        .sliding(3, 1).foreach(window => {
        if (window(0) == window(2)) hasRepeation = true
      })

      if (hasRepeation && hasDuplicate) nice += 1

    })

    println(s"Found $nice nice strings")

  }


}
