package mwi.advent.aoc2021.adv12

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

object GraphBuilder {

  def build(input: Array[String]): Map[String, Node] = {

    val nodes = mutable.HashMap.empty[String, Node]

    input.foreach(line => {
      val two = line.split("-")
      val node1name = two.head
      val node2name = two(1)

      val node1 = nodes.getOrElseUpdate(node1name, buildNode(node1name))

      val node2 = nodes.getOrElseUpdate(node2name, buildNode(node2name))

      setNeighbours(node1, node2)

    })

    nodes.foreach(e => println(s"${e._1} -> [${e._2.neighbours.mkString(",")}]"))
    nodes.toMap
  }

  private def setNeighbours(initA: Node, initB: Node) = {

    var a = initA
    var b = initB
    
    // input can have mixed order of start and end, so fixing that 
    if (initB.name == "start" || initA.name == "end") {
      a = initB
      b = initA
    }

    a.neighbours.append(b.name)
    if (a.name != "start" && b.name != "end") b.neighbours.append(a.name)
  }

  private def buildNode(name: String): Node = {
    val isBig = name.toUpperCase.equals(name)

    Node(name, ArrayBuffer.empty, isBig)
  }
}
