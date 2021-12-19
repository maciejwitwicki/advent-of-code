package mwi.advent.aoc2021.adv19.impl

import mwi.advent.aoc2021.adv19.Part1.Translation
import mwi.advent.util.Loc3d

import java.util.concurrent.ConcurrentHashMap
import scala.collection.mutable
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.util.Random

object DataGenerator {
  private implicit val ec: ExecutionContext = ExecutionContext.global

  private val minCoord = -1000
  private val maxCoord = 1000

  def generate(): mutable.HashSet[Loc3d] = {

    val batches: Iterator[Future[mutable.HashSet[Loc3d]]] = (0 until maxCoord * 2)
      .map(i => {
        i - maxCoord
      })
      .grouped(100)
      .map(b => create2(b))

    val f: Future[Iterator[mutable.HashSet[Loc3d]]] = Future.sequence(batches)

    Await
      .result(f, Duration.Inf)
      .foldLeft(mutable.HashSet.empty[Loc3d])((acc, el) => acc.addAll(el))

  }

  private def create2(batch: Seq[Int]): Future[mutable.HashSet[Loc3d]] = {
    Future {
      val thread = Thread.currentThread().getName
      println(s"$thread -> generating batch ${batch.head} - ${batch.last}")
      val result = mutable.HashSet.empty[Loc3d]
      batch.foreach(z => {
        var y = minCoord
        while (y <= maxCoord) {

          var x = minCoord
          while (x < maxCoord) {

            result.add(Loc3d(x, y, z))

            x += 1
          }

          y += 1
        }
      })

      result
    }
  }

}
