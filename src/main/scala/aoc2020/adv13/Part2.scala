package aoc2020.adv13

object Part2 {



  def solve(input: Array[String]) = {
    val sch = input(1).split(",")

    val schedule = sch.zipWithIndex.filter(e => e._1 != "x").map(e => Bus(e._1.toInt, e._2))

    schedule.foreach(println)

    var time = BigDecimal(0)


    var busToAlign = 1
    var jump = BigDecimal(schedule(0).period)
    var searchedBus = schedule(1)
    while (busToAlign < schedule.size) {

      val timeWithOffset: BigDecimal = time + searchedBus.offset
      val modulo: BigDecimal = timeWithOffset % searchedBus.period
      if (modulo == BigDecimal(0)) {
        jump = jump * searchedBus.period
        println(s"found align at $time for buses up to ${busToAlign + 1}, increasing jump to $jump")
        busToAlign = busToAlign + 1
        if (busToAlign < schedule.size)
          searchedBus = schedule(busToAlign)
      }

      val prevTime: BigDecimal = time
      time = time + jump
      if (jump > time / 4) {
        println(s"it's $prevTime")
      }
    }

  }


}

case class Bus(period: Int, offset: Int)
