package aoc2020.adv08

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

object Part1 {

  val execCounter = mutable.Map.empty[Int, Int]


  var pointer = 0
  var maxExecCounter = 0
  var acc = 0

  def solve(input: Array[String]) = {

    while (maxExecCounter < 2) {
      val op = input(pointer)

      val opLine = op.split(' ')
      val command = opLine(0)
      val value = opLine(1).replace("+", "").toInt

      command match {
        case "nop" => pointer = pointer + 1
        case "jmp" => pointer = pointer + value
        case "acc" =>
          acc = acc + value
          pointer = pointer + 1
        case x     => throw new IllegalArgumentException(s"Unknown operator $x")
      }


      val tmpCounter = execCounter.getOrElse(pointer, 0)
      val newTmpCounter = tmpCounter + 1
      execCounter.update(pointer, newTmpCounter)
      maxExecCounter = Math.max(maxExecCounter, newTmpCounter)

    }

    println(s"execution finished at line $pointer with accumulator value $acc")

  }





}


