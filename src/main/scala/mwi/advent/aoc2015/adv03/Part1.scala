package mwi.advent.aoc2015.adv03

import mwi.advent.util.Loc

object Part1 {

  var grid = scala.collection.mutable.HashMap.empty[Loc, Int]

  def solve(input: Array[String]): Unit = {

    val movements = input(0).toArray

    var x = 0
    var y = 0

    grid.put(Loc(0, 0), 1)

    movements.foreach {
      case '<' => x -= 1; update(x, y)
      case '>' => x += 1; update(x, y)
      case 'v' => y += 1; update(x, y)
      case '^' => y -= 1; update(x, y)
    }

    grid.foreach(e => println(s"${e._1} -> ${e._2}"))

    val result = grid.size

    println(s"result $result")

  }

  private def update(x: Int, y: Int) = {
    grid.updateWith(Loc(x, y))(valueOpt => valueOpt.map(v => v + 1).orElse(Some(1)))
  }
}
