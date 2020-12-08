package aoc2020.adv07

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

object Part1 {

  case class BagParentFits(fits: Int, parent: String)

  val pattern = """^(\d+)(.*)""".r
  val dependencies = mutable.HashMap.empty[String, List[BagParentFits]]

    def solve(input: Array[String]) = {


    input.foreach(line => {

      if (line.nonEmpty){
      val x1 = line.replace("bags", "").replace("bag", "").replace(".", "").split("contain")
      val parent = x1(0).trim
      val children = x1(1).split(",")

      children.foreach(chUntrimmed => {
        val ch = chUntrimmed.trim


        if (!ch.contains("no other")) {

          val (fits, child) = pattern.findFirstMatchIn(ch) match {
            case Some(i) => (i.group(1).toInt, i.group(2).trim)
            case _ => throw new IllegalArgumentException("regex not found")
          }

          val parents: List[BagParentFits] = dependencies.getOrElse(child, List.empty)
          dependencies(child) = parents :+ BagParentFits(fits, parent)
        }

      })
    }

    })


    println(dependencies.mkString("\n"))

    // let's traverse the tree

    var foundParents = Set.empty[String]
    var  safetyCount = 0
    var tmpParents = findParentsFor("shiny gold")

      while (tmpParents.nonEmpty && safetyCount < 1000) {
        foundParents = foundParents ++ tmpParents
        tmpParents = findParentsWithCheck(tmpParents)
        safetyCount = safetyCount + 1
        if (safetyCount > 900) {
          println("\n\n\n\n\n\n\n\n safety count reached \n\n\n\n\n\n")
        }
      }

      println(foundParents)
      println(foundParents.size)

  }
  private def findParentsWithCheck(nodes: Set[String]) = {
    var res = Set.empty[String]
    nodes.foreach(p => {
        res = res ++ findParentsFor(p)
    })
    res
  }


  private def findParentsFor(node: String) = {
    val value: Seq[BagParentFits] = dependencies.getOrElse(node, List.empty)
    val set: Set[BagParentFits] = value.toSet
    set.map(_.parent)
  }


}


