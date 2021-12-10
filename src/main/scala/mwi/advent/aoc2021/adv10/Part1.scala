package mwi.advent.aoc2021.adv10

import scala.collection.mutable
import scala.collection.mutable.{ArrayBuffer, ArrayStack}

object Part1 {

  def solve(input: Array[String]): Unit = {


    var x = input.map(line => {

      var incorrectLine = IncorrectLine(0, 0, 0, 0)
      val stack = mutable.Stack.empty[Char]
      for (c <- line) {

        if (!incorrectLine.isBroken()) {
          c match {
            case '{' | '[' | '(' | '<' => stack.push(c)
            case '}' => if (stack.top == '{') stack.pop() else incorrectLine = IncorrectLine(1, 0, 0, 0)
            case ']' => if (stack.top == '[') stack.pop() else incorrectLine = IncorrectLine(0, 1, 0, 0)
            case ')' => if (stack.top == '(') stack.pop() else incorrectLine = IncorrectLine(0, 0, 1, 0)
            case '>' => if (stack.top == '<') stack.pop() else incorrectLine = IncorrectLine(0, 0, 0, 1)
          }
        }
      }

      incorrectLine

    })

    x.foreach(println)


    val res = x.foldLeft(new IncorrectLine(0, 0, 0, 0))((acc, line) => {
      acc.copy(
        curly = acc.curly + line.curly,
        square = acc.square + line.square,
        round = acc.round + line.round,
        sharp = acc.sharp + line.sharp
      )
    })

    println(s"Res: $res: ${res.score()}")

  }

  case class IncorrectLine(curly: Int, square: Int, round: Int, sharp: Int) {
    def isBroken(): Boolean = {
      curly + square + round + sharp > 0
    }
    def score(): Int = {
      round * 3 + square * 57 + curly * 1197 + sharp * 25137
    }
  }

}
