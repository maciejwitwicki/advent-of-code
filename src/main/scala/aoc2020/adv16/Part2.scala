package aoc2020.adv16

import scala.collection.mutable

object Part2 {

  val fields = mutable.HashMap.empty[String, Array[(Int, Int)]]

  var myTicket = Array.empty[Int]

  var nearbyTickets: Array[Array[Int]] = Array.empty


  var fieldPossibilities = mutable.HashMap.empty[String, Set[Int]] // field name -> possible positions

  def solve(input: Array[String]) = {

    var it = 0

    while (input(it).nonEmpty) {
      addField(input(it))
      it = it + 1
    }

    it = it + 2

    myTicket = input(it).split(',').map(_.toInt)

    createFieldPosibilites()

    it = it + 3

    while (it < input.length) {
      val oneTicket: Array[Int] = input(it).split(',').map(_.toInt)
      nearbyTickets = nearbyTickets.appended(oneTicket)
      it = it + 1
    }

    println("Input is ready")

    // filter out invalid tickets

    val validTickets = nearbyTickets.filter(validate) :+ myTicket

    validTickets.foreach(t => println(s"${t.mkString(",")}"))

    var fieldIndex = 0
    for (field <- fields) {
      val (name, ranges) = field

      for (fieldIndex <- 0 until fields.size) {

        var couldBe = false
        var cantBe = false

        var ticketIterator = 0
        while (ticketIterator < validTickets.length && !cantBe) {

          val value = validTickets(ticketIterator)(fieldIndex)
          val valueValid = ranges.exists(r => value >= r._1 && value <= r._2)
          if (valueValid) couldBe = true else cantBe = true
          ticketIterator = ticketIterator + 1
        }

        if (!cantBe && couldBe) {
          val prevSet: Set[Int] = fieldPossibilities.get(name).get
          fieldPossibilities.update(name, prevSet + fieldIndex)
        }

      }
    }


    val result: mutable.Map[String, Set[Int]] = fieldPossibilities
    println(s"\nPrinting posibilities")

    result.foreach(r => println(s"${r._1} -> ${r._2.mkString(", ")}"))

    val sorted = result.map(r => Entry(r._1, r._2)).toList.sortBy(e => e.positions.size)


    var used: Set[Int] = Set.empty[Int]

    var iter = 0
    var clearedResult = List.empty[Entry]

    while (iter < sorted.size) {
      val current = sorted(iter)
      if (current.positions.size == 1) {
        used = used + current.positions.head
        clearedResult = clearedResult :+ current

      } else {
        val diff = current.positions.diff(used)

        clearedResult = clearedResult :+ current.copy(positions = diff)

        used = used ++ diff
      }

      iter = iter + 1
    }

    var finalduperresult = clearedResult.sortBy(e => e.positions.head)

    println(s"\nPrinting final result")
    finalduperresult.foreach(e => println(s"${e.name} -> ${e.positions.mkString(", ")}"))

    val departures = finalduperresult.filter(e => e.name.startsWith("departure"))
    println(s"\nPrinting  departures")
    departures.foreach(e => println(s"${e.name} -> ${e.positions.mkString(", ")}"))

    val ids = departures.flatMap(_.positions)

    val xxx = ids.map(myTicket(_)).foldLeft(1L)(_ * _)
    println(xxx)
  }

  private def createFieldPosibilites() = {
    fieldPossibilities = fields.map( entry => {
      entry._1 -> Set.empty[Int]
    })
  }

  private def validate(ticket: Array[Int]): Boolean = {
    val set: Set[(Int, Int)] = fields.values.flatten.toSet
    val allRules = set.map(tuple => Range.inclusive(tuple._1, tuple._2))

    val invalidValues = ticket.filter(value => {
      val matched = allRules.count(r => r.contains(value))
      matched == 0
    })

    invalidValues.isEmpty

  }


  private def addField(line: String) = {
    val x = line.split(": ")
    val name = x(0)
    val ranges1 = x(1).split(" or ")
    val ranges = ranges1.map(r => {
      val botAndTop = r.split('-')
      botAndTop(0).toInt -> botAndTop(1).toInt
    })

    fields.put(name, ranges)
  }

  case class Entry(name: String, positions: Set[Int])
}

