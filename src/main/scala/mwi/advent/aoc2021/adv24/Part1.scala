package mwi.advent.aoc2021.adv24

import mwi.advent.aoc2021.adv19.impl.Parser
import mwi.advent.util.{AdventHelpers, Loc, Loc3d}

import java.math.BigInteger
import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}

// unfinished
object Part1 extends AdventHelpers {

  private implicit val ec: ExecutionContext = ExecutionContext.global

  val results = mutable.HashSet.empty[BigInt]

  def solve(input: Array[String]): Unit = {

    var number = BigInt("99999499999949")

    val chunks = ArrayBuffer.empty[(BigInt, BigInt)]

    // val chunks = List((BigInt("99999909999990"), BigInt("99999899999990")))

    while (number > 0) {

      val next = number - 10_000_000
      chunks.addOne(number, next)

      number = next - 1
    }

    println(s"has ${chunks.size} of chunks, starting from ${chunks.head._1} ending with ${chunks.last._2}")

    val futures = Future.traverse(chunks)(ch => {
      longRunningOperation(ch, input)
    })

    Await.result(futures, Duration.Inf)

    println(s"found Results:")
    results.foreach(println)

  }

  private def longRunningOperation(chunk: (BigInt, BigInt), instructions: Array[String]) = {
    Future {

      val start  = chunk._1
      val end    = chunk._2
      val thread = Thread.currentThread().getName
      val name   = s"$thread [$start -> $end]"
      println(s"$name Starting search for chunk")
      var found  = false
      var it     = 0
      var number = start
      while (!found && number >= end) {
        found = calculateForInputNumber(start.toString(), instructions)

        if (!found) {
          number = decrement(number)
        }

        it += 1

        if (found) {
          results.add(number)
        }

        if (it % 500_000 == 0) println(s"$name iteration $it, number: $number")
      }
      println(s"$name finished chunk with status $found and last number $number")
    }
  }

  private def decrement(n: BigInt) = {
    var newN = BigInt(0)
    var decr = 1
    while (newN.toString().contains('0')) {

      newN = n - decr

      decr += 1
    }

    newN

  }

  private def calculateForInputNumber(number: String, instructions: Array[String]): Boolean = {
    val alu = Alu(number)
    instructions.foreach(line => alu.calculate(line))
    alu.getValueOf("z") == 1L
  }

}
