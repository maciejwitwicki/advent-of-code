package aoc2020.adv06

import scala.collection.mutable.ArrayBuffer

object Part1 {

  def solve(input: Array[String]) = {


    val allGroups = ArrayBuffer.empty[Set[Char]]

    var tmpGroup = Set.empty[Char]
    for (line <- input) {

      if (line.nonEmpty) {
        tmpGroup = tmpGroup ++ line.toCharArray.toSet
      } else {
        allGroups.append(tmpGroup)
        tmpGroup = Set.empty[Char]
      }

    }

    if (tmpGroup.nonEmpty) {
      allGroups.append(tmpGroup)
      tmpGroup = Set.empty[Char]
    }

    val resStr = allGroups.foreach(g => println(g.mkString(",")) + "\n")

    val res = allGroups.map(g => g.size).sum

    println(s"count: $res")

  }


}


