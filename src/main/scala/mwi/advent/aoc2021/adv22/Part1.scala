package mwi.advent.aoc2021.adv22

import mwi.advent.aoc2021.adv19.impl.Parser
import mwi.advent.util.{Loc, Loc3d, AdventHelpers}

import java.math.BigInteger
import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}

object Part1 extends AdventHelpers {
  private implicit val ec: ExecutionContext = ExecutionContext.global

  private val grid = mutable.HashMap.empty[Loc3d, Boolean]

  def solve(input: Array[String]): Unit = {

    val instructions = parseInput(input)

    instructions.foreach(i => {
      executeInstruction(i)
      println(s" === instruction: $i === ")
      // grid.foreach(e => println(e._1))
    })

    grid.foreach(e => println(e._1))

    val res = grid.size
    println(s"$res")

  }

  private def executeInstruction(instruction: Part1.Instruction) = {

    val zets = limit(buildRange(instruction.minZ, instruction.maxZ + 1))
    val yys  = limit(buildRange(instruction.minY, instruction.maxY + 1))
    val xes  = limit(buildRange(instruction.minX, instruction.maxX + 1))
    for (z <- zets) {
      for (y <- yys) {
        for (x <- xes) {

          val loc = Loc3d(x, y, z)
          if (instruction.enabled) {
            grid.put(loc, instruction.enabled)
          } else grid.remove(loc)

        }
      }
    }

  }

  private def limit(value: ArrayBuffer[Int]) = {

    val afterMinus50 = value.dropWhile(i => i < -50)

    val beforePlus50 = afterMinus50.takeWhile(i => i <= 50)

    beforePlus50

  }

  private def parseInput(input: Array[String]) = {
    input.map(line => {
      val on      = line(1) == 'n'
      val numbers = extractNumbers(line).map(_.toInt).toList

      Instruction(numbers.head, numbers(1), numbers(2), numbers(3), numbers(4), numbers(5), on)

    })
  }

  private case class Instruction(minX: Int, maxX: Int, minY: Int, maxY: Int, minZ: Int, maxZ: Int, enabled: Boolean)
}
