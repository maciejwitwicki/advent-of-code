package mwi.advent.aoc2021.adv10

import scala.collection.mutable
import scala.collection.mutable.{ArrayBuffer, ArrayStack}

object Part2 {

  def solve(input: Array[String]): Unit = {
   val scores = getIncompleteLinesOnly(input)
     .map(line => {

       val stack = mutable.Stack.empty[Char]
       for (c <- line) {
           c match {
             case '{' | '[' | '(' | '<' => stack.push(c)
             case '}' => if (stack.top == '{') stack.pop()
             case ']' => if (stack.top == '[') stack.pop()
             case ')' => if (stack.top == '(') stack.pop()
             case '>' => if (stack.top == '<') stack.pop()
           }
         }

       val remainding = stack.map {
         case '{' => '}'
         case '[' => ']'
         case '(' => ')'
         case '<' => '>'
       }
       remainding.mkString("")
       }
     )
     .map(remainder => {
       remainder.foldLeft(0L)((acc, c) => {
         val points = c match {
           case '}' => 3L
           case ']' => 2L
           case ')' => 1L
           case '>' => 4L
         }

         val res: Long = acc * 5 + points
         res
       })
     })

    val sortd = scores.sorted
    sortd.foreach(println)
    val middleIndex = scores.length / 2

    println(s"middle score: ${sortd(middleIndex)}")


  }



  private def getIncompleteLinesOnly(input: Array[String]) = {
    input.filterNot(line => {

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

      incorrectLine.isBroken()

    })
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
