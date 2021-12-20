package mwi.advent.aoc2021.adv19

import mwi.advent.aoc2021.adv19.impl.{BeaconFinder, DataGenerator, ExecutionPula, Parser, Translator}
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
    val scaners                                                   = buildScanersDistances
    val commonDistances: mutable.Map[Set[Int], Set[MatchedLines]] = calculateCommonDistances(scaners)

    println(s"common distances $commonDistances")

    commonDistances.foreach((e: (Set[Int], Set[MatchedLines])) => {

      val key   = e._1
      val lines = e._2
      println(s"analyzing scaner ${key.head} -> ${key.tail.head} with ${lines.size} lines")

      lines.foreach(lines => {
        findTransformation(lines.scaner1, lines.scaner2)
      })
    })
  }

  private def findTransformation(sc1: Line, sc2: Line) = {
    val d1 = calcDistance(sc1.start, sc1.end)
    val d2 = calcDistance(sc2.start, sc2.end)

    // translate one end of sc2 to start same as sc1
    var diffX = sc2.start.x - sc1.start.x
    var diffY = sc2.start.y - sc1.start.y
    var diffZ = sc2.start.z - sc1.start.z

    val newSc2End = Loc3d(sc2.end.x - diffX, sc2.end.y - diffY, sc2.end.z - diffZ)
    val newDist   = calcDistance(Loc3d(0, 0, 0), newSc2End)

    // translate other end of sc2 to start same as sc1
    diffX = sc2.end.x - sc1.start.x
    diffY = sc2.end.y - sc1.start.y
    diffZ = sc2.end.z - sc1.start.z

    val newSc2Start = Loc3d(sc2.start.x - diffX, sc2.start.y - diffY, sc2.start.z - diffZ)
    val newDist2    = calcDistance(Loc3d(0, 0, 0), newSc2Start)

    val orients = BeaconFinder.getOrientations

    orients.foreach(or => {
      val orientated = Translator.orientate(Set(newSc2End), or)

      if (orientated.equals(sc1.end)) {
        println(s"Found orienation and its $or")
      }
    })

    orients.foreach(or => {
      val orientated = Translator.orientate(Set(newSc2Start), or)

      if (orientated.equals(sc1.end)) {
        println(s"Found orienation and its $or")
      }
    })

  }

  private def buildScanersDistances = {
    val scaners = ArrayBuffer.empty[Scaner]

    for (i <- 0 until scannerInput.length) {
      val scaner    = scannerInput(i)
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
    scaners
  }

  private def calculateCommonDistances(scaners: ArrayBuffer[Scaner]) = {
    val commonDistances = mutable.HashMap.empty[Set[Int], Set[MatchedLines]]
    val precision       = 0.11
    for (i <- 0 until scaners.length) {

      for (j <- 0 until scaners.length) {

        val alreadyMeasured = commonDistances.keys.count(key => key.contains(i) && key.contains(j)) > 0

        if (i != j && !alreadyMeasured) {

          val scanerIdistances = scaners(i).distances
          val scanerJdistances = scaners(j).distances

          val difs         = ArrayBuffer.empty[Double]
          val matchedLines = mutable.HashSet.empty[MatchedLines]
          for (a <- scanerIdistances) {
            for (b <- scanerJdistances) {

              val aDist = a.value
              val bDist = b.value

              if (aDist == bDist) {
                matchedLines.add(MatchedLines(Point.of(a.points), Point.of(b.points)))
              }
            }
          }

          val sortedDifs = difs.sorted

          val commonCount = sortedDifs.count(p => p < precision)

          commonDistances.put(Set(i, j), matchedLines.toSet)

        }
      }
    }
    commonDistances
  }

  private def calcDistance(p1: Loc3d, p2: Loc3d): Double = {
    Math.sqrt(Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2) + Math.pow(p1.z - p2.z, 2))
  }

  private case class Scaner(id: Int, distances: mutable.HashSet[Distance])

  private case class Distance(value: Double, points: Set[Loc3d])

  private case class MatchedLines(scaner1: Line, scaner2: Line)

  private case class Line(start: Loc3d, end: Loc3d) {}

  private object Point {
    def of(set: Set[Loc3d]): Line = {
      val ar = set.toArray
      Line(ar(0), ar(1))
    }
  }

}
