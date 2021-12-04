package aoc2021.adv04

import scala.collection.mutable.ArrayBuffer

object Part2 {
  def solve(input: Array[String]): Unit = {

    val (numbersString, boardsString) = input.splitAt(2)

    val numbers = parseNumbers(numbersString)
    val bingo = parseBoards(boardsString)

    println(numbers.mkString(","))
    println(bingo)


    var numberIndex = 0
    while (!bingo.boards.isEmpty && numberIndex < numbers.length) {
      val number = numbers(numberIndex)
      println(s"======= $number ===========")
      bingo.mark(number)
      println(bingo)
      if (!bingo.boards.isEmpty) {
        numberIndex += 1
      } else {
        val unmarkedNumbersSum = bingo.winningBoards.last.getUnmarkedSum

        println(s"number $number was last, score = ${unmarkedNumbersSum * number}, last winning board: \n ${bingo.winningBoards.last}")

      }
    }


  }

  private def parseNumbers(input: Array[String]) = {
    input(0)
      .split(",")
      .map(Integer.parseInt)
  }

  private def parseBoards(input: Array[String]) = {
    val boards = input.grouped(6)
      .map(group => {
        createBoard(group)
      })
      .toArray

    Bingo(boards)

  }

  private def createBoard(input: Array[String]) = {
    val rows = input.take(5)
      .map(c => c.trim.split("\\s+")
        .map(Integer.parseInt)
        .map(i => Element(i, marked = false))
      )
      .map(elements => Row(ArrayBuffer.from(elements)))

    Board(rows)

  }

  case class Bingo(var boards: Array[Board], var winningBoards: Array[Board] = Array.empty) {

    def mark(number: Int) = {
      this.boards = boards.map(b => b.mark(number))

      boards.foreach(b => {
        if (b.hasRowOrCol) {
          winningBoards = winningBoards.appended(b)
        }
      })

      boards = boards.filter(b => !b.won)

    }

    override def toString: String = {
      s"playing boards:\n ${boards.mkString("\n")}, winning boards:\n ${winningBoards.mkString("\n")}"
    }
  }


  case class Board(rows: Array[Row], var won: Boolean = false) {

    def getUnmarkedSum: Int = {
      rows.flatMap(r => r.elements)
        .filter(e => !e.marked)
        .map(e => e.value)
        .sum
    }

    def hasRowOrCol: Boolean = {
      val itWon = rows.exists(r => {
        val allElementsInRowMarked = r.elements.forall(e => e.marked)
        allElementsInRowMarked
      }) || anyColumnMatches()

      won = itWon

      itWon
    }

    def mark(number: Int): Board = {
      val newRows = rows.map(row => row.mark(number))
      Board(newRows)
    }

    private def anyColumnMatches(): Boolean = {
      val columnCount = rows(0).elements.length


      var matched = false
      for (col <- 0 until columnCount) {
        var colMatch = true
        for (row <- rows) {
          if (!row.elements(col).marked) colMatch = false
        }
        if (colMatch) matched = true
      }
      matched
    }


    override def toString: String = {

      rows.map(r => {
        r.elements.mkString(" ")

      }).mkString("\n") + "\n"


    }
  }

  case class Row(elements: ArrayBuffer[Element]) {

    def mark(number: Int): Row = {

      val newElements = elements.map(e => {
        if (e.value == number) {
          e.copy(marked = true)
        } else e

      })

      Row(newElements)
    }

  }

  case class Element(value: Int, marked: Boolean) {
    override def toString: String = {

      if (marked) {
        s"*$value*"
      } else value.toString

    }
  }

}
