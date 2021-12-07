package mwi.advent.aoc2021.adv02

object Part1 {
  def solve(input: Array[String]) = {

    val pos = input.map(i => {
      val split = i.split(' ')
      Line(split(0), Integer.parseInt(split(1)))
    })
      .foldLeft(Pos(0, 0))((acc, line) => {
        line match {
          case Line("forward", x) => acc.copy(h = acc.h + x)
          case Line("down", x) => acc.copy(depth = acc.depth + x)
          case Line("up", x) => acc.copy(depth = acc.depth - x)
        }
      })

    println(s"pos: $pos, res: ${pos.h * pos.depth}")

  }
  case class Line(command: String, x: Int)

  case class Pos(h: Int, depth: Int)
}
