package mwi.advent.aoc2015.adv03

import mwi.advent.util.Loc

object Part2 {

  var grid = scala.collection.mutable.HashMap.empty[Loc, Int]

  def solve(input: Array[String]): Unit = {

    val movements = input(0).toArray

    var santaLoc = Loc(0, 0)
    var roboLoc = Loc(0, 0)

    grid.put(Loc(0, 0), 1);

    var santasTurn = true

    movements.foreach(m => {

      if (santasTurn) {
        santaLoc = move(santaLoc, m)
        update(santaLoc)
      } else {
        roboLoc = move(roboLoc, m)
        update(roboLoc)
      }
      santasTurn = !santasTurn

    }
    )

    grid.foreach(e => println(s"${e._1} -> ${e._2}"))

    val result = grid.size

    println(s"result $result")

  }

  private def move(loc: Loc, movement: Char): Loc = {
    movement match {
      case '<' => loc.copy(x = loc.x - 1)
      case '>' => loc.copy(x = loc.x + 1)
      case 'v' => loc.copy(y = loc.y + 1)
      case '^' => loc.copy(y = loc.y - 1)
    }
  }

  private def update(loc: Loc) = {
    grid.updateWith(loc)(valueOpt => valueOpt.map(v => v + 1).orElse(Some(1)))
  }
}
