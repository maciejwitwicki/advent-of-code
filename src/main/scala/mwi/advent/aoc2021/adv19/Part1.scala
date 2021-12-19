package mwi.advent.aoc2021.adv19

import mwi.advent.aoc2021.adv19.Part1.translations
import mwi.advent.aoc2021.adv19.impl.{BeaconFinder, Parser}
import mwi.advent.util.{Loc, Loc3d, NumberExtractor}

import java.math.BigInteger
import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}

object Part1 extends NumberExtractor {
  private implicit val ec: ExecutionContext = ExecutionContext.global
  
  type Locs = mutable.ArrayBuffer[Loc3d]
  private[adv19] case class Orientation(facing: String, up: String)
  private[adv19] case class Translation(pos: Loc3d, orientation: List[List[String]])

  private final val commonBeacons = 12
  private var scannerInput: mutable.ArrayBuffer[Locs] = mutable.ArrayBuffer.empty

  private var translations: mutable.HashMap[String, Translation] = mutable.HashMap.empty

  val cases = DataGenerator.generate()

  def solve(input: Array[String]): Unit = {

    scannerInput = Parser.parseScanerInput(input)

    val combinations = scannerInput.indices.sliding(2, 1)

    combinations.map(c => {
      val i = c(0)
      val j = c(1)
      
      val operationFuture = Future.traverse(cases)(data => {
        BeaconFinder.findFuture(commonBeacons, scannerInput, i, j, data)
      })
      
      Await.ready(operationFuture, Duration.Inf)
      
    })
    
    val operationFuture = Future.traverse(combinations)(c => {
      val i = c(0)
      val j = c(1)
      val thread = Thread.currentThread().getName
      println(s"$thread -> Searching common beacons between set $i and $j")
      BeaconFinder
        .findFuture(commonBeacons, scannerInput, i, j)
        .map(result => {
          result.foreach(t => {
            val thread = Thread.currentThread().getName
            println(s"$thread -> found translation from $i to $j: $t")
            translations.put(s"$i->$j", t)
          })
        })
    })

    Await.ready(operationFuture, Duration.Inf)

  }

}
