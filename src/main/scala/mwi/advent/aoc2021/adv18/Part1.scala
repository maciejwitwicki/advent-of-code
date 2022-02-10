package mwi.advent.aoc2021.adv18

import mwi.advent.aoc2021.adv18.impl.Exploder
import mwi.advent.aoc2021.adv18.impl.Parser
import mwi.advent.util.{Loc, AdventHelpers}

import java.math.BigInteger
import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

object Part1 extends AdventHelpers {

  def solve(input: Array[String]): Unit = {

    val res = input.foldLeft("")((acc, line) => {
      if (acc.isBlank) {
        line
      } else {
        val sum = add(acc, line)
        val res = parseLine(sum)
        // println(s"\n    $acc\n  + $line\n  = $res")
        res
      }
    })

    val node: Node = Parser.parseSnailfishStringToObject(res)

    println(s"res: $node")

    println(s"magnitude: ${node.getMagnitude}")

  }

  private def parseLine(line: String) = {

    val operations = mutable.Stack('e')
    var tmp        = line

    while (operations.nonEmpty) {
      val op = operations.pop()
      op match {
        case 'e' =>
          val res = Exploder.explode(tmp)
          if (res.isDefined) {
            operations.push('e')
            tmp = res.get
          } else {
            operations.push('s')
          }
        case 's' =>
          val res = split(tmp)
          if (res.isDefined) {
            operations.push('e')
            tmp = res.get
          }
      }
      // println(s"$op: $tmp")
    }

    tmp
  }

  private def add(str1: String, str2: String): String = {
    s"[$str1,$str2]"
  }

  private def split(str: String) = {
    val intOption = extractNumbers(str)
      .map(Integer.parseInt)
      .toList
      .find(i => i > 9)

    intOption.map(i => {

      val div: Double = i.toDouble / 2

      val leftInt  = Math.floor(div).toInt
      val rightInt = Math.ceil(div).toInt

      val intString    = i.toString
      val index        = str.indexOf(intString)
      val (left, rest) = str.splitAt(index)

      val (toReplace, right) = rest.splitAt(intString.length)

      left + s"[$leftInt,$rightInt]" + right
    })

  }

}
