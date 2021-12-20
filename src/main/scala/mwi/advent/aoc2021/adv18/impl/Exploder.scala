package mwi.advent.aoc2021.adv18.impl
import mwi.advent.aoc2021.adv18.{Node, PairNode, ValueNode}

import mwi.advent.aoc2021.adv18.Part1.extractNumbers
import mwi.advent.aoc2021.adv18.impl.Parser.parseSnailfishStringToObject

object Exploder {

  def explode(str: String): Option[String] = {
    var opened                = 0
    var index                 = 0
    var found: Option[String] = None
    while (found.isEmpty && index < str.length) {

      str(index) match {
        case '['     => opened += 1
        case ']'     => opened -= 1
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

    val midNode = parseSnailfishStringToObject(m).asInstanceOf[PairNode]

    // explode leftmost pair if available then try rightmost pair
    if (midNode.left.isPair) {

      val explodingPair = midNode.left.asInstanceOf[PairNode]

      val secondNode = midNode.right

      val explodingValueLeft  = explodingPair.left.asInstanceOf[ValueNode].value
      val explodingValueRight = explodingPair.right.asInstanceOf[ValueNode].value

      val leftOutput  = addToRightmostValue(l, explodingValueLeft)
      val rightOutput = addToLeftmostValue("," + secondNode + "]" + r, explodingValueRight)

      Some(leftOutput + "[0" + rightOutput)

    } else if (midNode.right.isPair) {

      val explodingPair = midNode.right.asInstanceOf[PairNode]

      val secondNode = midNode.left

      val explodingValueLeft  = explodingPair.left.asInstanceOf[ValueNode].value
      val explodingValueRight = explodingPair.right.asInstanceOf[ValueNode].value
      val leftOutput          = addToRightmostValue(l + "[" + secondNode + ",", explodingValueLeft)
      val rightOutput         = addToLeftmostValue(r, explodingValueRight)

      Some(leftOutput + "0]" + rightOutput)

    } else None

  }

  private def addToRightmostValue(str: String, explodedValue: Int) = {
    val rightmostValue = extractNumbers(str).map(Integer.parseInt).toList.lastOption
    rightmostValue
      .map(value => {
        val intString           = value.toString
        val index               = str.lastIndexOf(intString)
        val (prefix, toReplace) = str.splitAt(index)
        val newValue            = value + explodedValue
        prefix + toReplace.replace(intString, newValue.toString)
      })
      .getOrElse(str)
  }

  private def addToLeftmostValue(str: String, explodedValue: Int) = {
    val leftmostValue = extractNumbers(str).map(Integer.parseInt).toList.headOption
    leftmostValue
      .map(value => {
        val intString = value.toString
        val index     = str.indexOf(intString)
        val toReplace = str.take(index + intString.length)
        val postfix   = str.drop(toReplace.length)
        val newValue  = value + explodedValue
        toReplace.replace(intString, newValue.toString) + postfix
      })
      .getOrElse(str)
  }

  private def extractMiddleNode(str: String, midIndex: Int) = {

    val (left, rest) = str.splitAt(midIndex)

    var rightIndex = 1
    var found      = false
    var opened     = 1
    while (opened != 0) {

      rest(rightIndex) match {
        case '['     => opened += 1
        case ']'     => opened -= 1
        case default =>
      }
      rightIndex += 1
    }

    val (mid, right) = rest.splitAt(rightIndex)

    (left, mid, right)

  }

}
