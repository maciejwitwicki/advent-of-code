package aoc2020.adv23

import java.lang.System.currentTimeMillis
import scala.collection.mutable

object Part2 {
  val moves: BigDecimal = BigDecimal(10_000_000)
  val PrintStatusAt = 10_000
  val MeasureAndPrint = false
  val MaxValue: Int = 1_000_000
  val nodeByValue = mutable.HashMap.empty[Int, Node]

  def solve(input: Array[String]) = {


    val tmpCups: Array[Int] = input(0).toCharArray.map(c => c.toString).map(Integer.parseInt)
    //val tmpCups = Array(7, 8, 9, 1, 2 ,5 ,4 ,6 ,7 )
    val linkedList = LinkedList()

    for (i <- tmpCups) {
      val node = linkedList.push(i)
      nodeByValue.put(i, node)
    }

    val lowestLabel = 1
    val highestLabel = tmpCups.max

    println(s"starting to insert values from ${highestLabel +1} up to $MaxValue")
    for (x <- highestLabel + 1 to MaxValue) {
      if (x % PrintStatusAt == 0) println(s"Inserting value $x...")
      val node = linkedList.push(x)
      nodeByValue.put(x, node)
    }

    println(s"note by value size is ${nodeByValue.size}")
    println(s"Start iteration")


    var it: BigDecimal = BigDecimal(0)

    mutable.LinkedHashSet
    var time = currentTimeMillis()
    var currentCup: Node = linkedList(0)
    while (it < moves) {

      val p1 = linkedList.getNextCircural(currentCup)
      val p2 = linkedList.getNextCircural(p1)
      val p3 = linkedList.getNextCircural(p2)

      val pickUp = Array(p1.data, p2.data, p3.data)

      time = measure(time, "01 pick up indexes", it)

      // connect current with the first node after the last pickUp element
      currentCup.next = linkedList.getNextCircural(p3)


      time = measure(time, "2 removed pick up cups, was 40, 51, 18", it)

      // find destination label
      var destinationLabel = currentCup.data - 1
      if (destinationLabel < lowestLabel) destinationLabel = MaxValue

      while (pickUp.contains(destinationLabel)) {
        destinationLabel = destinationLabel - 1
        if (destinationLabel < lowestLabel) destinationLabel = MaxValue
      }

      time = measure(time, "3. created destination label", it)

      // get destination node
      val destNode = nodeByValue(destinationLabel)

      time = measure(time, "4 split by desttination, was 18, 15, 12", it)

      // insert pickUp after the destination node

      val prevPointer = linkedList.getNextCircural(destNode)
      destNode.next = p1
      p3.next = prevPointer


      time = measure(time, "5 created result with inserted pick up at destination", it)

     // if (it % PrintStatusAt == 0) printCups(linkedList)

      // set next currentCup
      currentCup = linkedList.getNextCircural(currentCup)

     // if (it % PrintStatusAt == 0) println(s"iteration $it")

      time = measure(time, "6 finished the loop", it)

      if (it > 9_999_990 || it % PrintStatusAt == 0) {

        val node1 = nodeByValue(1)
        val node2 = linkedList.getNextCircural(node1)
        val node3 = linkedList.getNextCircural(node2)
        println(s"ITERATION $it - Nodes after 1: ${node2.data} and ${node3.data}")
      }

      it = it + 1
    }

    println("Iterations finished, calculating result")

    //val (resultPrefix, resultPostfix) = cups.splitAt(cups.indexOf(1))
    //val result = resultPostfix.drop(1) ++ resultPrefix

    val node1 = nodeByValue(1)
    val node2 = node1.next
    val node3 = node2.next

    println(s"Nodes after 1: ${node2.data} and ${node3.data}")

    printCups(linkedList)

  }

  private def printCups(cups: LinkedList) = {

    var result = Array.empty[Int]

    var node = cups.head

    for (x <- 0 until 10) {
      result = result :+ node.data
      node = cups.getNextCircural(node)
    }
    println(s"${result.mkString(", ")}")

  }

  private def measure(from: Long, message: String, it: BigDecimal) = {
    if (MeasureAndPrint && it % PrintStatusAt == 0 ) {
      val to = currentTimeMillis()
      println(s"${to - from} -> $message")
      currentTimeMillis()
    } else 0
  }

}

case class LinkedList() {
  var head: Node = null
  var last: Node = null

  def push(data: Int): Node = {
    head match {
      case null => {
        head = Node(data, null)
        last = head
        head
      }
      case _ => {
        val newNode = Node(data, null)
        last.next = newNode
        last = newNode
        newNode
      }
    }
  }

  def apply(index: Int): Node = {
    var currentNode = head
    var currentIndex = 0;
    while (!currentIndex.equals(index)) {
      currentNode = currentNode.next
      currentIndex += 1;
    }
    currentNode;
  }

  def getDataByIndex(index: Int): Int = {
    apply(index).data
  }

  def getNextCircural(node: Node): Node = {
    if (node.next == null) head else node.next
  }

}

case class Node(var data: Int, var next: Node)