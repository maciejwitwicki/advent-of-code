package mwi.advent.aoc2021.adv19.impl

import mwi.advent.aoc2021.adv19.Part1.{Locs, Orientation}
import mwi.advent.util.Loc3d

object Translator {
  def translate(locs: Locs, by: Loc3d): Set[Loc3d] = {
    locs.map(l => Loc3d(x = l.x + by.x, y = l.y + by.y, z = l.z + by.z)).toSet
  }

  def orientate(locs: Set[Loc3d], by: Orientation): Set[Loc3d] = {

    val res = by match {
      case Orientation("x", "y")  => locs
      case Orientation("x", "-y") => locs.map(l => l.copy(y = l.y * -1))
      case Orientation("x", "z")  => locs.map(l => l.copy(y = l.z, z = l.y * -1))
      case Orientation("x", "-z") => locs.map(l => l.copy(y = l.z, z = l.y))

      case Orientation("-x", "y")  => locs.map(l => l.copy(x = l.x * -1))
      case Orientation("-x", "-y") => locs.map(l => l.copy(x = l.x * -1, y = l.y * -1, z = l.y * -1))
      case Orientation("-x", "z")  => locs.map(l => l.copy(x = l.x * -1, y = l.z, z = l.y * -1))
      case Orientation("-x", "-z") => locs.map(l => l.copy(x = l.x * -1, y = l.z, z = l.y))

      case Orientation("y", "z")   => locs.map(l => l.copy(y = l.z, z = l.y * -1))
      case Orientation("y", "-z")  => locs.map(l => l.copy(y = l.z, z = l.y * -1))
      case Orientation("y", "x")   => locs.map(l => l.copy(y = l.z, z = l.y * -1))
      case Orientation("y", "-x")  => locs.map(l => l.copy(y = l.z, z = l.y * -1))
      case Orientation("-y", "z")  => locs.map(l => l.copy(y = l.z, z = l.y * -1))
      case Orientation("-y", "-z") => locs.map(l => l.copy(y = l.z, z = l.y * -1))
      case Orientation("-y", "x")  => locs.map(l => l.copy(y = l.z, z = l.y * -1))
      case Orientation("-y", "-x") => locs.map(l => l.copy(y = l.z, z = l.y * -1))
      case Orientation("z", "y")   => locs.map(l => l.copy(y = l.z, z = l.y * -1))
      case Orientation("z", "-y")  => locs.map(l => l.copy(y = l.z, z = l.y * -1))
      case Orientation("z", "x")   => locs.map(l => l.copy(y = l.z, z = l.y * -1))
      case Orientation("z", "-x")  => locs.map(l => l.copy(y = l.z, z = l.y * -1))
      case Orientation("-z", "y")  => locs.map(l => l.copy(y = l.z, z = l.y * -1))
      case Orientation("-z", "-y") => locs.map(l => l.copy(y = l.z, z = l.y * -1))
      case Orientation("-z", "x")  => locs.map(l => l.copy(y = l.z, z = l.y * -1))
      case Orientation("-z", "-x") => locs.toSet
      case default                 => throw new IllegalArgumentException("not expected orientation")
    }

    res.toSet

  }

}
