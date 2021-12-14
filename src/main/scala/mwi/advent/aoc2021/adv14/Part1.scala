package mwi.advent.aoc2021.adv14

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

object Part1 {

  val instr = mutable.HashMap.empty[String, String]

  def solve(input: Array[String]): Unit = {
    val template = input.head

    val instructions = input.drop(2).foreach(line => {
      val x = line.split(" -> ")
      instr.put(x(0), x(1))
    })


    val steps = 10

    var output = template

    for (i <- 0 until steps) {

      val stepResult = output.sliding(2, 1)
        .fold(output.head.toString)((acc, wnd) => {
          val wndArr = wnd.toArray
          val newChar = instr(wnd)
          s"$acc$newChar${wndArr(1)}"
        })

      output = stepResult

      println(s"$i: $output")
    }

    println(s"result after $steps steps is $output")

    val stringes: Array[String] = output.toArray.map(c => c.toString)
    val mapes: Map[String, Array[String]] = stringes.groupBy(x => x)

    val sizes = mapes.values.map(l => l.length)

    val min = sizes.toList.min
    val max = sizes.toList.max

    println(s"$max - $min = ${max - min}")


  }
}
