package mwi.advent.aoc2021.adv19.impl

import mwi.advent.aoc2021.adv19.Part1.Translation
import mwi.advent.util.Loc3d

import scala.collection.mutable

case object DataGenerator {

  private val minCoord = -1000
  private val maxCoord = 1000
  private final val orientations = List("x", "y", "z", "-x", "-y", "-z").combinations(3).toList

  def generate() = {

    val translations = mutable.ArrayBuffer.empty[Translation]

    var z = minCoord
    while (z <= maxCoord) {

      var y = minCoord
      while (y <= maxCoord) {

        var x = minCoord
        while (x < maxCoord) {

         
            translations.append(Translation(Loc3d(x, y, z), orientations))
       


          x += 1
        }

        y += 1
      }

      z += 1
    }

    translations

  }

}
