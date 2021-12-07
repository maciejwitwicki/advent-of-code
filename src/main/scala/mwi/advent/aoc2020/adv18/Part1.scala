package mwi.advent.aoc2020.adv18

import scala.collection.mutable

object Part1 {

  def solve(input: Array[String]) = {

    val results = input.map(line => {

      val trimmed = line.replace(" ", "")

      calculate(trimmed)


    })

    results.foreach(println)
    println(s"Result = ${results.sum}")

  }

  private def calculate(line: String): BigDecimal = {
    var tmpResult = BigDecimal(0)
    var operation = "S" // S as Starting operation ;)
    var number = BigDecimal(0)

    def doOperation(n: BigDecimal) = {
      tmpResult = operation match {
        case "+" => tmpResult + number
        case "*" => tmpResult * number
        case "S" => operation = "X"; number // allow first unknown operation, next time throw exception
        case _   => throw new RuntimeException("illegal operation")
      }
    }

    var it = 0
    while(it < line.length) {

      val element = line(it)

      element match {
        case '(' => {
          val expression = extract(line.substring(it))
          number = calculate(expression)
          doOperation(number)
          it = it + expression.length + 2 // count opening and closing parentheses in
        }
        case '+' => operation = "+"; it = it + 1
        case '*' => operation = "*"; it = it + 1
        case x   => {
          number = BigDecimal(x.toString)
          doOperation(number)
          it = it + 1
        }
      }
    }

    tmpResult
  }

  private def extract(text: String) = {

    var it = 1
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

    text.drop(1).take(it - 2)
  }
}
