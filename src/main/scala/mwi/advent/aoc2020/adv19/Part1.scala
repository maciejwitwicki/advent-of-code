package mwi.advent.aoc2020.adv19

import scala.annotation.tailrec
import scala.collection.mutable

object Part1 {

  val cache = mutable.HashMap.empty[Int, List[String]]

  val rulesMap = mutable.HashMap.empty[Int, String]

  def solve(input: Array[String]) = {

    var it = 0
    while (it < input.length && input(it).nonEmpty) {

      val ruleStr = input(it).split(": ")
      val ruleId = ruleStr(0).toInt
      val rule = ruleStr(1)

      val prevRule = rulesMap.get(ruleId)
      if (prevRule.nonEmpty) println(s"WARNING, overridden rule $ruleId")
      rulesMap.put(ruleId, rule)

      it = it + 1
    }

    println("rules:")
    rulesMap.foreach(println)

    decode(0)

    cache.foreach(e => println(e))


    // read entries to verify

    var matches = 0
    it = it + 1
    while (it < input.length) {
      if (cache(0).contains(input(it))) matches = matches + 1
      it = it + 1
    }

    println(s"entries that matches the rule 0: $matches")

  }

  private def decode(i: Int): Array[String] = {

    if (cache.contains(i))
      cache.get(i).get.toArray else {

      val rule = rulesMap(i).replace("\"", "")

      if (rule == "a" || rule == "b") {
        val bin = rule.replace("a", "0").replace("b", "1")
        cache.put(i, List(bin))
        Array(bin)
      } else {
        val variations: Array[String] = rule.split(" \\| ")

        val res: Array[String] = variations.flatMap(v => {

          var elements = Array.empty[Array[String]]

          v.split(" ")
            .foreach((element: String) => {
              val isText = element.matches("[ab]+")
              if (isText) elements = elements :+  Array(element)
              else {
                val newId = Integer.parseInt(element)
                val subRule: Array[String] = {
                  cache.get(newId).map(_.toArray).getOrElse(decode(newId))
                }
                elements = elements :+ subRule

              }
            })

          val z: Array[String] = append(elements.head, elements, 1)
          z
        })


        cache.put(i, res.toList)
        res
      }
    }
  }

  @tailrec
  private def append(wordsSoFar: Array[String], elements: Array[Array[String]], depth: Int): Array[String] = {
    if (depth < elements.length) {
      val currentElement = elements(depth)
      val newWordsSoFar = wordsSoFar.flatMap(prefix => currentElement.map(current =>{
        val r = prefix + current
        r
      } ))
      append(newWordsSoFar, elements, depth + 1)
    } else wordsSoFar
  }

}
