package mwi.advent.aoc2021.adv24

import scala.collection.mutable

class Alu(input: String) {

  private var inputIndex = 0
  private var memory     = mutable.HashMap.empty[String, Long]

  def calculate(line: String) = {

    val operation = line.split(" ")

    val command  = operation(0)
    val variable = operation(1)

    var other = parseOtherValue(operation)

    command match {
      case "inp" => read(variable)
      case "mul" => mult(variable, other)
      case "eql" => eql(variable, other)
      case "div" => div(variable, other)
      case "mod" => mod(variable, other)
      case "add" => add(variable, other)
    }

  }

  def printMemory(): Unit = {
    memory.foreach(println)
  }

  def getValueOf(variable: String): Long = {
    memory.getOrElse(variable, 0L)
  }

  private def parseOtherValue(strings: Array[String]): Long = {
    if (strings.length == 3) {
      val keyOrValue = strings(2)
      keyOrValue.toLongOption
        .orElse(memory.get(keyOrValue))
        .getOrElse(0L)
    } else 0L

  }

  private def add(variable: String, b: Long) = {
    val a = memory.getOrElse(variable, 0L)
    memory.put(variable, a + b)
  }

  private def mod(variable: String, of: Long) = {
    val a: Long = memory.getOrElse(variable, 0)
    val result  = a % of
    memory.put(variable, result)
  }

  private def div(variable: String, by: Long) = {
    val a: Long        = memory.getOrElse(variable, 0L)
    val result: Double = a / by
    if (result > 0) Math.floor(result) else Math.ceil(result)
    memory.put(variable, result.toLong)
  }

  private def eql(variable: String, b: Long) = {
    val a = memory.getOrElse(variable, 0L)

    if (a == b) {
      memory.put(variable, 1)
    } else {
      memory.put(variable, 0)
    }

  }

  private def mult(variable: String, value: Long) = {
    val prevValue: Long = memory.getOrElse(variable, 0)
    memory.put(variable, prevValue * value)
  }

  private def read(variable: String) = {
    val int = ("" + this.input(inputIndex)).toInt
    memory.put(variable, int)
    inputIndex += 1
  }

}
