package mwi.advent.aoc2021.adv19.impl

import mwi.advent.aoc2021.adv19.Part1.{FoundOrientation, Locs, Orientation, Translation}
import mwi.advent.util.{Loc, Loc3d}

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer
import scala.concurrent.{ExecutionContext, Future}

object BeaconFinder {

  private val minCoord                                      = -1000
  private val maxCoord                                      = 1000
  private final val orientations: ArrayBuffer[List[String]] = buildOrientations

  private implicit val ec: ExecutionContext = ExecutionPula.ec

  def findFuture(commonBeacons: Int, scans: mutable.ArrayBuffer[Locs], i: Int, j: Int, translation: Loc3d): Future[Option[FoundOrientation]] = {
    Future {
      find(commonBeacons, scans, i, j, translation)
    }
  }

  def getOrientations: List[List[String]] = {
    orientations.toList
  }

  private def find(commonBeacons: Int, scans: mutable.ArrayBuffer[Locs], i: Int, j: Int, translation: Loc3d): Option[FoundOrientation] = {

    val scan1 = scans(i)
    val scan2 = scans(j)

    var found = false

    val thread = Thread.currentThread().getName
    if (translation.x == 0 && translation.y == 0) println(s"$thread -> searching $i -> $j translated to ${translation}")

    val translatedSet          = Translator.translate(scan2, translation)
    val (success, orientation) = findForOrientations(scan1, translatedSet, commonBeacons)

    if (success) {
      Some(FoundOrientation(translation, orientation))
    } else None
  }

  private def findForOrientations(base: Locs, translated: Set[Loc3d], requiredCommonBeaconsFound: Int): (Boolean, List[String]) = {

    var success = false

    var or    = List("")
    var index = 0
    while (!success && index < orientations.length) {
      val z = orientations
      or = orientations(index)
      val orientated = Translator.orientate(translated, or)
      success = findCommonBeacons(base, orientated, requiredCommonBeaconsFound)
      index += 1
    }

    (success, or)

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

  private def buildOrientations = {

    val chars = ArrayBuffer("x", "y", "z", "-x", "-y", "-z")

    val combinations = ArrayBuffer.empty[List[String]]

    for (ch1 <- chars) {

      for (ch2 <- chars) {

        for (ch3 <- chars) {

          val list = List(ch1, ch2, ch3)

          if (list.map(x => x.replace("-", "")).distinct.size == 3) {
            combinations.append(list)
          }
        }
      }
    }

    combinations
  }

}
