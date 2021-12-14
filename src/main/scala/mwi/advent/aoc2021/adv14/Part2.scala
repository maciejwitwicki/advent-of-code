package mwi.advent.aoc2021.adv14

import java.lang.System.currentTimeMillis
import scala.annotation.tailrec
import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

object Part2 {

  val instr = mutable.HashMap.empty[String, Char]

  def solve(input: Array[String]): Unit = {
    val template = input.head

    input.drop(2).foreach(line => {
      val x = line.split(" -> ")
      instr.put(x(0), x(1).charAt(0))
    })


    val steps = 40
    val counts = mutable.HashMap.empty[Char, Long]

    var nextWordsToExpand: mutable.Map[String, Long] = mutable.HashMap.empty[String, Long]

    template.foreach(ch => {
      updateCountsWithChar(counts, ch, 1L)
    })

    template.sliding(2, 1).foreach(window => {
      val prev = nextWordsToExpand.getOrElse(window, 0L)
      nextWordsToExpand.put(window, prev + 1L);
    })

    for (i <- 0 until steps) {
      val start = currentTimeMillis();
      val newWords = mutable.HashMap.empty[String, Long]

      nextWordsToExpand.foreach(w => {
        val word = w._1
        val count = w._2
        val toInsert = instr(word)
        updateCountsWithChar(counts, toInsert, count)

        val left = s"${word(0)}$toInsert"
        val right = s"$toInsert${word(1)}"

        val prevLeftCount = newWords.getOrElse(left, 0L)
        newWords.put(left, prevLeftCount + count)

        val prevRightCount = newWords.getOrElse(right, 0L)
        newWords.put(right, prevRightCount + count)

      })

      val elapsed = currentTimeMillis() - start;
      val newWordString = newWords.map(e => s"${e._1} ${e._2}").mkString(" - ")
      println(s"after step ${i + 1}, size: ${newWords.size} [$elapsed ms] $newWordString")

      nextWordsToExpand = newWords
    }

    println(s"After $steps steps: ")
    println(s"counts: ")
    counts.foreach(println)
    val min = counts.values.min
    val max = counts.values.max
    println(max - min)
  }


  private def updateCountsWithChar(counts: mutable.HashMap[Char, Long], ch: Char, prevCount: Long) = {
    counts.updateWith(ch)(vOpt => {
      val v: Long = vOpt.getOrElse(0)
      Some(v + prevCount)
    })
  }
}
