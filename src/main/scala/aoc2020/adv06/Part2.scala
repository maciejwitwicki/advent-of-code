package aoc2020.adv06

import com.sun.javaws.exceptions.InvalidArgumentException

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer
import scala.util.Try
import scala.util.matching.Regex

object Part2 {

  def solve(input: Array[String]) = {


    val allGroups = ArrayBuffer.empty[Group]

    var tmpGroupMap = mutable.HashMap.empty[Char, Int]
    var tmpGroupSize =  0

    for (line <- input) {

      if (line.nonEmpty) {
        tmpGroupSize = tmpGroupSize + 1
        val answersOfSinglePerson = line.toCharArray.toSet
        answersOfSinglePerson.foreach(answer => {
          val currentCount = tmpGroupMap.getOrElse(answer, 0)
          tmpGroupMap.update(answer, currentCount + 1)
        })
      } else {
        allGroups.append( Group(tmpGroupSize, tmpGroupMap) )
        tmpGroupMap = mutable.HashMap.empty[Char, Int]
        tmpGroupSize = 0
      }

    }

    if (tmpGroupMap.nonEmpty) {
      allGroups.append(Group(tmpGroupSize, tmpGroupMap))
    }

    allGroups.foreach(g => println(s"Group size ${g.size}  ${g.answers.mkString(",")} \n"))

    val res = allGroups.map(gr => {
      gr.answers.count((entry) => entry._2 == gr.size)
    }).sum

    println(res)

  }

  case class Group(size: Int, answers: mutable.HashMap[Char, Int])

}


