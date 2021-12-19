package mwi.advent.aoc2021.adv19.impl

import mwi.advent.aoc2021.adv19.Part1.{Locs, Orientation}
import mwi.advent.util.Loc3d

object Translator {
  def translate(locs: Locs, by: Loc3d): Set[Loc3d] = {
    locs.map(l => Loc3d(x = l.x + by.x, y = l.y + by.y, z = l.z + by.z)).toSet
  }

  def orientate(locs: Set[Loc3d], orientation: List[String]): Set[Loc3d] = {
      locs.map(l => {
        val newX = get(l, orientation(0))
        val newY = get(l, orientation(1))
        val newZ = get(l, orientation(2))
        Loc3d(newX, newY, newZ)
      })
  }

  private def get(l: Loc3d, orientation: String): Int = {
    orientation match {
      case "x" => l.x
      case "-x" => l.x * -1
      case "y" => l.y
      case "-y" => l.y * -1
      case "z" => l.z
      case "-z" => l.z * -1
    }
  }

}
