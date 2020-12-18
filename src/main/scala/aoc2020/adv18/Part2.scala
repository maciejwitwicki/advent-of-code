package aoc2020.adv18

import scala.collection.mutable

object Part2 {

  def solve(input: Array[String]) = {

    val results = input.map(line => {

      val trimmed = line.replace(" ", "")

      calculate(trimmed)

    })

    results.foreach(println)
    println(s"Result = ${results.sum}")

  }

  private def calculate(line: String): BigDecimal = {

    var tmpLine = line
    var parIndex = tmpLine.indexOf('(')

    while(parIndex > -1) {
      val expression = extractFirstPar(tmpLine, parIndex)
      val result = calculate(expression.drop(1).dropRight(1))
      tmpLine = tmpLine.replace(expression, result.toString)

      parIndex = tmpLine.indexOf('(')
    }

    val expressions = tmpLine.split("\\*")

    val toBeMultiplied = expressions.map(e => {
      if (e.contains("+")) calculateAdd(e) else BigDecimal(e)
    })

    val result = toBeMultiplied.foldLeft(BigDecimal(1))(_ * _)

    result
  }

  private def calculateAdd(text: String) = {
    text.split("\\+").map(BigDecimal(_)).sum
  }

  private def extractFirstPar(text: String, index: Int) = {
    var it = index + 1
    var openParentheses = 1
    while(openParentheses > 0) {
      val char = text(it)

      char match {
        case '(' => openParentheses = openParentheses + 1
        case ')' => openParentheses = openParentheses - 1
        case _ =>
      }

      it = it + 1
    }

    text.substring(index, it)
  }

}

