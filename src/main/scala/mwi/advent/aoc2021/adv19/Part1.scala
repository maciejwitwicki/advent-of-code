package mwi.advent.aoc2021.adv19

import mwi.advent.aoc2021.adv19.impl.{BeaconFinder, DataGenerator, ExecutionPula, Parser}
import mwi.advent.util.{Loc, Loc3d, NumberExtractor}

import java.math.BigInteger
import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}

object Part1 extends NumberExtractor {
  private implicit val ec: ExecutionContext = ExecutionPula.ec

  type Locs = mutable.ArrayBuffer[Loc3d]

  private[adv19] case class Orientation(facing: String, up: String)

  private[adv19] case class Translation(pos: Loc3d, orientation: List[List[String]])

  private[adv19] case class FoundOrientation(pos: Loc3d, orientation: List[String])

  private final val commonBeacons = 6

  private var scannerInput: mutable.ArrayBuffer[Locs] = mutable.ArrayBuffer.empty

  def solve(input: Array[String]): Unit = {
    scannerInput = Parser.parseScanerInput(input)
    val scaners = ArrayBuffer.empty[Scaner]

    for (i <- 0 until scannerInput.length) {
      val scaner = scannerInput(i)
      val distances = mutable.HashSet.empty[Distance]

      for (i <- scaner) {
        for (j <- scaner) {
          if (i != j) {
            distances.add(Distance(calcDistance(i, j), Set(i, j)))
          }
        }
      }
      scaners.append(Scaner(i, distances))
    }

    println(s"scanners $scaners")


  }

  private def calcDistance(p1: Loc3d, p2: Loc3d): Double = {
    Math.sqrt(
      Math.pow(p1.x + p2.x, 2) +
        Math.pow(p1.y + p2.y, 2) +
        Math.pow(p1.z + p2.z, 2)
    )
  }

  private case class Scaner(id: Int, distances: mutable.HashSet[Distance])

  private case class Distance(value: Double, points: Set[Loc3d])
}
