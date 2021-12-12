package mwi.advent.aoc2021.adv12

import mwi.advent.aoc2021.adv12.Node


import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

object Part2 {


  var foundPaths = ArrayBuffer.empty[Seq[String]]
  var graph: Map[String, Node] = Map.empty


  def solve(input: Array[String]) = {
    
    this.graph = GraphBuilder.build(input)
    
    val start = graph("start")
    val pathSoFar: List[String] = List("start")

    goNextStep(start.neighbours, pathSoFar)

    foundPaths.foreach(p => println(p.mkString(",")))

    println(s"found ${foundPaths.length} paths")

  }


  private def goNextStep(neighbours: ArrayBuffer[String], path: List[String]): Unit = {

    neighbours.foreach(nodeName => {

      val node = graph.get(nodeName).get

      if (nodeName == "end") {
        foundPaths.append(path :+ "end")
      } else if (node.big) {
        goNextStep(node.neighbours, path :+ node.name)
      } else if (!node.big) {
        val lowercases = path.drop(1) // no "start"
          .filter(n => n.toLowerCase.equals(n))

        val groupped = lowercases // only lowercases
          .groupBy(s => s)

        val duplicates = groupped // group by letter
          .values
          .count(e => e.length > 1)

        if (duplicates >= 1) {
          if (!path.contains(nodeName)) {
            goNextStep(node.neighbours, path :+ node.name)
          }
        } else if (!(path.count(x => x.equals(nodeName)) == 2)) {
          goNextStep(node.neighbours, path :+ node.name)
        }
      }

    })
  }

}
