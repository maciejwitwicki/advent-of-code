package mwi.advent.aoc2021.adv12


import mwi.advent.aoc2021.adv12.Node

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

object Part1 {


  var foundPaths = ArrayBuffer.empty[Seq[String]]
  var graph: Map[String, Node] = Map.empty


  def solve(input: Array[String]): Unit = {

    this.graph =  GraphBuilder.build(input)
    
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
      } else if (node.big || (!node.big && !path.contains(nodeName))) {
        goNextStep(node.neighbours, path :+ node.name)
      }
    })
  }

}
