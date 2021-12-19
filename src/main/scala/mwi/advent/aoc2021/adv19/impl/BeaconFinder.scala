package mwi.advent.aoc2021.adv19.impl

import mwi.advent.aoc2021.adv19.Part1.{Locs, Orientation, Translation}
import mwi.advent.util.{Loc, Loc3d}

import scala.collection.mutable
import scala.concurrent.{ExecutionContext, Future}

object BeaconFinder {

  private val minCoord = -100
  private val maxCoord = 100

  private implicit val ec: ExecutionContext = ExecutionContext.global

  def findFuture(commonBeacons: Int, scans: mutable.ArrayBuffer[Locs], i: Int, j: Int): Future[Option[Translation]] = {
    Future {
      find(commonBeacons, scans, i, j)
    }
  }

  private def find(commonBeacons: Int, scans: mutable.ArrayBuffer[Locs], i: Int, j: Int): Option[Translation] = {

    val scan1 = scans(i)
    val scan2 = scans(j)

    var found       = false
    var translation = Translation(Loc3d(0, 0, 0), Orientation("null", "null"))

    var z = minCoord
    while (!found && z <= maxCoord) {

      var y = minCoord
      while (!found && y <= maxCoord) {

        var x = minCoord
        while (!found && x < maxCoord) {
          val translatedPos = Loc3d(x, y, z)
          val translatedSet = Translator.translate(scan2, translatedPos)

          val thread = Thread.currentThread().getName
          if (x == 0 && y == 0) println(s"$thread -> searching $i -> $j translated to $x $y $z")

          val (success, orientation) = findForOrientations(scan1, translatedSet, commonBeacons)
          if (success) {
            translation = Translation(translatedPos, orientation)
            found = true
          }

          x += 1
        }

        y += 1
      }

      z += 1
    }
    if (found) Some(translation) else None
  }

  private def findForOrientations(base: Locs, translated: Set[Loc3d], requiredCommonBeaconsFound: Int): (Boolean, Orientation) = {

    var success     = false
    var index       = 0
    var orientation = orientations(index)
    val thread      = Thread.currentThread().getName
    println(s"[$thread]looking for orientation $orientation")
    while (!success && index < orientations.size) {
      val orientated = Translator.orientate(translated, orientation)

      val found = findCommonBeacons(base, orientated, requiredCommonBeaconsFound)
      if (found) {
        success = true
      }

    }

    (success, orientation)

  }

  private def findCommonBeacons(base: Locs, translated: Set[Loc3d], requiredCommonBeaconsFound: Int): Boolean = {

    var found = 0
    var index = 0

    while (found < requiredCommonBeaconsFound && index < base.size) {

      if (translated.contains(base(index))) {
        found += 1
      }
      index += 1
    }

    found == requiredCommonBeaconsFound
  }

}
