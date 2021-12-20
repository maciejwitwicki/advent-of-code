package mwi.advent.aoc2021.adv18.impl

import mwi.advent.aoc2021.adv18.{Node, PairNode, ValueNode}

private[adv18] object Parser {

  /** @param content
    *   - string enclosed with [ ]
    */
  def parseSnailfishStringToObject(content: String): Node = {

    val strip = content.substring(1, content.length - 1)

    if (strip(0) == '[') {
      val (left, rest) = extractSegment(strip)

      PairNode(
        parseSnailfishStringToObject(left),
        rest.toIntOption
          .map(x => ValueNode(x))
          .getOrElse(parseSnailfishStringToObject(rest))
      )

    } else {
      val nextComa        = strip.indexOf(',')
      val (value, rest)   = strip.splitAt(nextComa)
      val restWithoutComa = rest.drop(1)

      val node = ValueNode(value.toInt)

      if (restWithoutComa.isEmpty) {
        node
      } else {

        PairNode(
          node,
          restWithoutComa.toIntOption
            .map(x => ValueNode(x))
            .getOrElse(parseSnailfishStringToObject(restWithoutComa))
        )
      }
    }
  }

  private def extractSegment(input: String): (String, String) = {

    var openBrackets = 1
    var index        = 1
    while (openBrackets != 0) {
      input(index) match {
        case '['     => openBrackets += 1
        case ']'     => openBrackets -= 1
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

}
