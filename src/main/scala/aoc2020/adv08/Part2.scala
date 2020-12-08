package aoc2020.adv08

import scala.collection.mutable

object Part2 {



  def solve(input: Array[String]) = {

    for (i <- 0 until input.size) {
      val line = input(i)
      Computer.readCommandFromLine(line)._1 match {
        case "nop" =>
          val nopToJmp = line.replace("nop", "jmp")
          val newInput: Array[String] = input.updated(i, nopToJmp)
          new Computer().compute(newInput)
        case "jmp" =>
          val jmpToNop = line.replace("jmp", "nop")
          val newInput: Array[String] = input.updated(i, jmpToNop)
          new Computer().compute(newInput)
        case x =>
      }

    }

  }

}

class Computer {
  val execCounter = mutable.Map.empty[Int, Int]

  var pointer = 0
  var maxExecCounter = 0
  var acc = 0

  def compute(input: Array[String]) = {
    while (maxExecCounter < 2 && pointer < input.length) {
      val line = input(pointer)

      val (command, value) = Computer.readCommandFromLine(line)

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
    val success = input.length == pointer
    if (success) println("============\n\nsuccess\n\n=============")
    println(s"input lenght: ${input.length}, pointer: $pointer, max execution counter: $maxExecCounter")
    println(s"execution finished at line $pointer with accumulator value $acc")
    success

  }

}

object Computer {

  def readCommandFromLine(line: String) = {
    val opLine = line.split(' ')
    val command = opLine(0)
    val value = opLine(1).replace("+", "").toInt
    (command, value)
  }
}
