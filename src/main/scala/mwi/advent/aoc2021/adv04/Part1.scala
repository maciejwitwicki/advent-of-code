package mwi.advent.aoc2021.adv04

import scala.collection.mutable.ArrayBuffer

object Part1 {
  def solve(input: Array[String]): Unit = {

    val (numbersString, boardsString) = input.splitAt(2)

    val numbers = parseNumbers(numbersString)
    val bingo = parseBoards(boardsString)

    println(numbers.mkString(","))
    println(bingo)


    var numberIndex = 0
    while (bingo.winningBoard == null && numberIndex < numbers.length) {
      val number = numbers(numberIndex)
      println(s"======= $number ===========")
      bingo.mark(number)
      println(bingo)
      if (bingo.winningBoard == null) {
        numberIndex += 1
      } else {
        val unmarkedNumbersSum = bingo.winningBoard.getUnmarkedSum

        println(s"number $number was last, score = ${unmarkedNumbersSum * number}, winning board: \n ${bingo.winningBoard}")

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

  case class Bingo(var boards: Array[Board], var winningBoard: Board = null) {

    def mark(number: Int) = {
      this.boards = boards.map(b => b.mark(number))

      boards.foreach(b => {
        if (b.hasRowOrCol) winningBoard = b
      })
    }

    override def toString: String = {
      boards.mkString("\n")
    }
  }


  case class Board(rows: Array[Row]) {

    def getUnmarkedSum: Int = {
      rows.flatMap(r => r.elements)
        .filter(e => !e.marked)
        .map(e => e.value)
        .sum
    }

    def hasRowOrCol: Boolean = {
      rows.exists(r => {
        val allElementsInRowMarked = r.elements.forall(e => e.marked)
        allElementsInRowMarked
      }) || anyColumnMatches()
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
