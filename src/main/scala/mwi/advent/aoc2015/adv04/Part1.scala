package mwi.advent.aoc2015.adv04

import com.roundeights.hasher.Implicits.*
import mwi.advent.util.Loc

import java.security.MessageDigest

object Part1 {

  def solve(input: Array[String]): Unit = {
    val secret = input(0)

    val md = MessageDigest.getInstance("MD5")

    var found = false

    var x = 0

    while (!found) {
      val input = s"$secret$x"

      val output = input.md5

      if (output.startsWith("000000")) {
        found = true
        println(s"found! $input ($x)")
      }

      if (x % 100_000 == 0) println(s"$input -> $output")

      x = x + 1
    }


  }


}
