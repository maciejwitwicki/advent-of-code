package mwi.advent.aoc2021.adv13

import mwi.advent.aoc2021.adv12.Node

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

object GridFolder {

  case class Loc(x: Int, y: Int)

  case class Instruction(axis: Char, pos: Int)

  val grid = mutable.HashMap.empty[Loc, Boolean]

  def solve(input: Array[String], singleFold: Boolean): Unit = {

    val lastLoc = input.findLast(line => line.contains(",")).get
    val lastLocIndex = input.indexOf(lastLoc) + 1

    val all = input.splitAt(lastLocIndex)

    val points  = all(0)
    val instructions = all(1).drop(1)

    parseGrid(points)

    val toFold = if (singleFold) instructions.take(1) else instructions

    toFold.foreach(instructionStr => {
      val instruction = parseInstructionLine(instructionStr)
      println(s"instr: $instruction")

      val toMove = if (instruction.axis == 'x') grid.filter(e => e._1.x > instruction.pos)
      else grid.filter(e => e._1.y > instruction.pos)

      toMove.foreach(l => {
        val oldLoc = l._1

        grid.remove(oldLoc)

        if (instruction.axis == 'y') {
          val newY = instruction.pos - (oldLoc.y - instruction.pos)
          grid.put(Loc(oldLoc.x, newY), true)
        }

        if (instruction.axis == 'x') {
          val newX = instruction.pos - (oldLoc.x - instruction.pos)
          grid.put(Loc(newX, oldLoc.y), true)
        }

      })

    })

    println(s"folding results:")
    grid.foreach(e => println(s"${e._1}"))
    val res = grid.size
    println(s"number of points: $res")

    printGrid()
  }

  private def parseGrid(points: Array[String]) = {
    points.foreach(loc => {
      val pos = loc.split(",").map(Integer.parseInt)

      grid.put(Loc(pos(0), pos(1)), true)

    })
  }

  private def printGrid() = {


    val maxX = grid.map(e => e._1.x).max
    val maxY = grid.map(e => e._1.y).max

    for (y <- 0 to maxY) {
      var line = ""
      for (x <- 0 to maxX) {
        val aasdf = grid.getOrElse(Loc(x,y), false)
        val p = if (aasdf) '#' else '.'
        line = line + p
      }
      println(line)
    }
  }

  private def parseInstructionLine(line: String) = {
    val equalLoc = line.indexOf('=')

    val axis = line(equalLoc - 1)
    val pos = Integer.parseInt(line.substring(equalLoc + 1))
    Instruction(axis, pos)
  }

}
