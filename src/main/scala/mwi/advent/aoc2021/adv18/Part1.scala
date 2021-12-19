
package mwi.advent.aoc2021.adv18

import mwi.advent.util.{Loc, NumberExtractor}

import java.math.BigInteger
import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

object Part1 extends NumberExtractor {

  def solve(input: Array[String]): Unit = {


   val res = input.foldLeft("")((acc, line) => {
      if (acc.isBlank) {
        line
      } else {
        val sum = add(acc, line)
        val res = parseLine( sum )
        println(s"\n    $acc\n  + $line\n  = $res")
        res
      }
    })

    val node: Node = parseContent(res)

    println(s"res: $node")

  }

  private def parseLine(line: String) = {

    val operations = mutable.Stack('e')
    var tmp = line

    while (operations.nonEmpty) {
      val op = operations.pop()
      op match  {
        case 'e' =>
          val res = explode(tmp)
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
      println(s"$op: $tmp")
    }

    tmp
  }

  private def add(str1: String, str2: String): String = {
    s"[$str1,$str2]"
  }


  private def explode(str: String): Option[String] = {
    var opened = 0
    var index = 0
    var found: Option[String] = None
    while (found.isEmpty && index < str.length) {

      str(index) match {
        case '[' => opened += 1
        case ']' => opened -= 1
        case default =>
      }

      if (opened == 4 && str(index) == '[') {
        found = tryToExplode(str, index)
      }

      index += 1
    }

    found

  }

  // [8,0] or [8,[1,2]]
  private def tryToExplode(str: String, index: Int): Option[String] = {

    val (l, m, r) = extractMiddleNode(str, index)

    val midNode = parseContent(m).asInstanceOf[PairNode]

    // explode leftmost pair if available then try rightmost pair
    if (midNode.left.isPair) {

      val explodingPair = midNode.left.asInstanceOf[PairNode]

      val secondNode = midNode.right

      val explodingValueLeft = explodingPair.left.asInstanceOf[ValueNode].value
      val explodingValueRight = explodingPair.right.asInstanceOf[ValueNode].value

      val leftOutput = addToRightmostValue(l, explodingValueLeft)
      val rightOutput = addToLeftmostValue("," + secondNode + "]" + r, explodingValueRight)

      Some(leftOutput + "[0" + rightOutput)

    } else if (midNode.right.isPair) {

      val explodingPair = midNode.right.asInstanceOf[PairNode]

      val secondNode = midNode.left

      val explodingValueLeft = explodingPair.left.asInstanceOf[ValueNode].value
      val explodingValueRight = explodingPair.right.asInstanceOf[ValueNode].value
      val leftOutput = addToRightmostValue(l + "[" + secondNode + ",", explodingValueLeft)
      val rightOutput = addToLeftmostValue(r, explodingValueRight)

      Some(leftOutput + "0]" + rightOutput)

    } else None

  }

  private def addToRightmostValue(str: String, explodedValue: Int) = {
    val rightmostValue = extractNumbers(str).map(Integer.parseInt).toList.lastOption
    rightmostValue.map(value => {
      val intString = value.toString
      val index = str.lastIndexOf(intString)
      val (prefix, toReplace) = str.splitAt(index)
      val newValue = value + explodedValue
      prefix + toReplace.replace(intString, newValue.toString)
    }).getOrElse(str)
  }

  private def addToLeftmostValue(str: String, explodedValue: Int) = {
    val leftmostValue = extractNumbers(str).map(Integer.parseInt).toList.headOption
    leftmostValue.map(value => {
      val intString = value.toString
      val index = str.indexOf(intString)
      val (toReplace, postfix) = str.splitAt(index + 1)
      val newValue = value + explodedValue
      toReplace.replace(intString, newValue.toString) + postfix
    }).getOrElse(str)
  }

  private def extractMiddleNode(str: String, midIndex: Int) = {

    val (left, rest) = str.splitAt(midIndex)

    var rightIndex = 1
    var found = false
    var opened = 1
    while (opened != 0) {

      rest(rightIndex) match {
        case '[' => opened += 1
        case ']' => opened -= 1
        case default =>
      }
      rightIndex += 1
    }

    val (mid, right) = rest.splitAt(rightIndex)

    (left, mid, right)

  }

  private def split(str: String) = {
    val intOption = extractNumbers(str)
      .map(Integer.parseInt)
      .toList
      .find(i => i > 9)

    intOption.map(i => {

      val div: Double = i.toDouble / 2

      val leftInt = Math.floor(div).toInt
      val rightInt = Math.ceil(div).toInt

      val intString = i.toString
      val index = str.indexOf(intString)
      val (left, rest) = str.splitAt(index)

      val (toReplace, right) = rest.splitAt(intString.length)

      left +  s"[$leftInt,$rightInt]" + right
    })

  }


  /**
   *
   * @param content - string enclosed with [ ]
   */
  private def parseContent(content: String): Node = {

    val strip = content.substring(1, content.length - 1)

    if (strip(0) == '[') {
      val (left, rest) = extractSegment(strip)

      PairNode(
        parseContent(left),
        rest.toIntOption.
          map(x => ValueNode(x))
          .getOrElse(parseContent(rest))
      )

    } else {
      val nextComa = strip.indexOf(',')
      val (value, rest) = strip.splitAt(nextComa)
      val restWithoutComa = rest.drop(1)

      val node = ValueNode(value.toInt)

      if (restWithoutComa.isEmpty) {
        node
      } else {

        PairNode(
          node,
          restWithoutComa
            .toIntOption
            .map(x => ValueNode(x))
            .getOrElse(parseContent(restWithoutComa))
        )
      }
    }
  }

  private def extractSegment(input: String): (String, String) = {

    var openBrackets = 1
    var index = 1
    while (openBrackets != 0) {
      input(index) match {
        case '[' => openBrackets += 1
        case ']' => openBrackets -= 1
        case default =>
      }

      index += 1
    }

    val (left, right) = input.splitAt(index)

    if (right.startsWith(",")) {
      (left, right.drop(1))
    } else {
      (left, right)
    }

  }

  private sealed abstract class Node {
    def isValue: Boolean

    def isPair: Boolean
  }

  private case class PairNode(left: Node, right: Node) extends Node {
    override def isPair: Boolean = true

    override def isValue: Boolean = false

    override def toString: String = s"[$left,$right]"
  }

  private case class ValueNode(value: Int) extends Node {
    override def isPair: Boolean = false

    override def isValue: Boolean = true

    override def toString: String = s"$value"
  }

}
