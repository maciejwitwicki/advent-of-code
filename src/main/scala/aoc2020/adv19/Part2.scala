package aoc2020.adv19

import scala.annotation.tailrec
import scala.collection.mutable
object Part2 {

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

    var entries = Array.empty[String]
    var matches = 0
    var matched = List.empty[String]
    it = it + 1
    while (it < input.length) {
      entries = entries :+ input(it)
      if (cache(0).contains(input(it))){
        matches = matches + 1
        matched = matched :+ input(it)
      }
      it = it + 1
    }

    println(s"count entries that matches the rule 0: $matches")
    matched.foreach(println)

    // all correct entries must start with 42, because 0: 8 11,    8: 42 | 42 8
    // all correct entries will end with 31, because: 0: 8 11,    11: 42 31 | 42 11 31
    println(s"----- > entries that start with 42 and end with 31 ----")

    val finalEntries = entries.filter(validateRule1)
      finalEntries.foreach(print8bitWords)
      println(s"there is ${finalEntries.length} of them")

    val setOfEntries: Set[String] = finalEntries.toSet
    val lentghts: Set[Int] = setOfEntries.map(_.length)
    val maxLenght = lentghts.max
    println(s"maximum lenght is $maxLenght which is ${maxLenght / 8} words")


    // remove first and last words, they're now irrelevant

    val op1 = setOfEntries.toList.map(entry => entry.drop(8).dropRight(8))


    // do per lenght filtering according to the text diagram scratch.txt
    val op2 = op1.filter(entry => {

      val bzz: Int = entry.length / 8

      bzz match {
        case x if (x == 1) => verify(entry, 1, 0)
        case x if (x == 2) => verify(entry, 2, 0)
        case x if (x == 3) => verify(entry, 2, 1) || verify(entry, 3, 0)
        case x if (x == 4) => verify(entry, 3, 1) || verify(entry, 4, 0)
        case x if (x == 5) => verify(entry, 3, 2) || verify(entry, 4, 1) || verify(entry, 5, 0)
        case x if (x == 6) => verify(entry, 4, 2) || verify(entry, 5, 1) || verify(entry, 6, 0)
        case x if (x == 7) => verify(entry, 4, 3) || verify(entry, 5, 2) || verify(entry, 6, 1) || verify(entry, 7, 0)
        case x if (x == 8) => verify(entry, 5, 3) || verify(entry, 6, 2) || verify(entry, 7, 1) || verify(entry, 8, 0)
        case x => throw new RuntimeException(s"Unexpected value $x")
      }

    })

    op2.foreach(print8bitWords)
    println(s"op2 contains ${op2.size} entries")
  }

  private def verify(entry: String, fortyTwos: Int, thirtyOnes: Int) = {
    val words = entry.sliding(8, 8).toList

    if (words.length != fortyTwos + thirtyOnes) throw new RuntimeException(s"${fortyTwos + thirtyOnes} words expected but entry have ${words.length}!")

    val wordsOf42 = cache(42)
    val wordsOf31 = cache(31)
    var valid = true
    var it = 0

    while(valid && it < fortyTwos) {
     valid = wordsOf42.contains(words(it))
      it = it + 1
    }

    while (valid && it < words.size) {
      valid = wordsOf31.contains(words(it))
      it = it + 1
    }

    valid
  }

  private def print8bitWords(str: String) = {
    println(str.sliding(8, 8).mkString(" "))
  }

  private def validateRule1(entry: String) = {
    val wordsOf42 = cache(42)
    val wordsOf31 = cache(31)
    wordsOf42.exists(word => entry.startsWith(word)) && wordsOf31.exists(word => entry.endsWith(word))
  }


  private def decode(i: Int): Array[String] = {

    if (cache.contains(i))
      cache.get(i).get.toArray else {

      val rule = rulesMap(i).replace("\"", "")

      if (rule == "a" || rule == "b") {
        val bin = rule//.replace("a", "0").replace("b", "1")
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

