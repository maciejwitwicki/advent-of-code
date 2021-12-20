package mwi.advent.aoc2021.adv19.impl

import java.util.concurrent.Executors
import scala.concurrent.ExecutionContext

object ExecutionPula {
  private val pool = Executors.newFixedThreadPool(20)
  val ec: ExecutionContext = ExecutionContext.fromExecutor(pool)
}
