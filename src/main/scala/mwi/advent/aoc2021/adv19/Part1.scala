package mwi.advent.aoc2021.adv19

import mwi.advent.aoc2021.adv19.impl.{ExecutionPula, Parser}
import mwi.advent.util.{Loc, Loc3d, NumberExtractor}

import java.math.BigInteger
import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}

object Part1 extends NumberExtractor {
  private implicit val ec: ExecutionContext = ExecutionPula.ec

  type Locs = mutable.HashSet[Loc3d]

  private[adv19] case class Translation(pos: Loc3d, orientation: List[List[String]])

  private final val commonBeacons = 6

  private var scannerInput: mutable.ArrayBuffer[Locs] = mutable.ArrayBuffer.empty

  def solve(input: Array[String]): Unit = {
    scannerInput = Parser.parseScanerInput(input)

    val foundScanners                          = mutable.HashSet.empty[Loc3d]
    val scannedBeacons: mutable.HashSet[Loc3d] = mutable.HashSet.empty.addAll(scannerInput.head)
    val scansLeft                              = scannerInput.drop(1)

    while (scansLeft.nonEmpty) {
      val current = mutable.HashSet.empty.addAll(scansLeft.remove(0))

      findTransformation(scannedBeacons, current) match {
        case Some(transformation) =>
          scannedBeacons.addAll(transformation.beacons)
          foundScanners.add(transformation.scaner)
        case None => scansLeft.append(current)
      }
    }

    val beacons = scannedBeacons.size

    println(s"result: $beacons")
    foundScanners.foreach(println)

    var maxDist = 0
    for (i <- foundScanners) {
      for (j <- foundScanners) {
        if (i != j) {
          val distance = Math.abs(i.x - j.x) + Math.abs(i.y - j.y) + Math.abs(i.z - j.z)
          maxDist = Math.max(maxDist, distance)
        }
      }
    }
    println(s"max distance is $maxDist")

  }

  private def findTransformation(baseSet: Locs, tested: Locs): Option[Transformation] = {

    val base = baseSet.toList

    var res: Option[Transformation] = None
    var found                       = false

    var direction = 0
    while (!found && direction < 6) {

      var rotation = 0
      while (!found && rotation < 4) {

        val testedAfterRotations = tested.map(loc => loc.direction(direction).rotation(rotation)).toList

        var index = 0
        while (!found && index < base.size) {

          val baseItem = base(index)

          var testedIndex = 0
          while (!found && testedIndex < testedAfterRotations.size) {

            val testedItem = testedAfterRotations(testedIndex)

            val diff  = baseItem - testedItem
            val moved = testedAfterRotations.map(loc => loc + diff)

            if (moved.intersect(base).size >= 12) {
              found = true
              res = Some(Transformation(diff, moved.toSet))
            }

            testedIndex += 1
          }
          index += 1
        }

        rotation += 1
      }
      direction += 1
    }

    res
  }

  private case class Transformation(scaner: Loc3d, beacons: Set[Loc3d])
}
