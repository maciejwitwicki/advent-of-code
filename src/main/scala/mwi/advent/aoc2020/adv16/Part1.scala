package mwi.advent.aoc2020.adv16

import scala.collection.mutable

object Part1 {


  val fields = mutable.HashMap.empty[String, Array[(Int, Int)]]

  var myTicket = Array.empty[Int]

  var nearbyTickets: Array[Array[Int]] = Array.empty

  def solve(input: Array[String]) = {

    var it = 0

    while (input(it).nonEmpty) {
      addField(input(it))
      it = it + 1
    }

    it = it + 2

    myTicket = input(it).split(',').map(_.toInt)

    it = it + 3

    while (it < input.length) {
      val oneTicket: Array[Int] = input(it).split(',').map(_.toInt)
      nearbyTickets = nearbyTickets.appended(oneTicket)
      it = it + 1
    }

    println("Input is ready")

    // get invalid values from tickets

    val invalidValues = nearbyTickets.flatMap(ticket => {
      val wrongValues = validate(ticket)
      wrongValues
    }
    )

    val sum = invalidValues.sum

    println(s"Result $sum")


  }

  private def validate(ticket: Array[Int]): Array[Int] = {
    val set: Set[(Int, Int)] = fields.values.flatten.toSet
    val allRules = set.map(tuple => Range.inclusive(tuple._1, tuple._2))

    ticket.filter(value => {
      val matched = allRules.count(r => r.contains(value))
      matched == 0
    })
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
}
