package mwi.advent.aoc2021.adv18

import mwi.advent.aoc2021.adv18.impl.{Exploder, Parser}
import mwi.advent.util.{Loc, AdventHelpers}

import java.math.BigInteger
import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

object Part2 extends AdventHelpers {

  def solve(input: Array[String]): Unit = {

    var pairs: Set[(String, String)] = Set.empty

    for (i <- input) {
      for (j <- input) {
        if (i != j) {
          pairs = pairs + ((i, j))
        }
      }
    }

    pairs.foreach(println)

    val result = pairs
      .map(p => {
        val sum     = add(p._1, p._2)
        val reduced = reduceSnailfishNumber(sum)
        Parser.parseSnailfishStringToObject(reduced).getMagnitude
      })
      .max

    println(s"max magnitude: $result")

//    val res = input.foldLeft("")((acc, line) => {
//      if (acc.isBlank) {
//        line
//      } else {
//        val sum = add(acc, line)
//        val res = reduceSnailfishNumber(sum)
//        // println(s"\n    $acc\n  + $line\n  = $res")
//        res
//      }
//    })

    // val node: Node = Parser.parseContent(res)

    // println(s"res: $node")

    // println(s"magnitude: ${node.getMagnitude}")

  }

  private def reduceSnailfishNumber(line: String) = {

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
