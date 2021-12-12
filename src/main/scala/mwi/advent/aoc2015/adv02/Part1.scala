package mwi.advent.aoc2015.adv02

object Part1 {

  def solve(input: Array[String]): Unit = {

    val result = input.map(line => {
      val dims = line.split("x").map(Integer.parseInt)

      val l = dims(0)
      val w = dims(1)
      val h = dims(2)

      val areas = List(l * w, w * h, h * l)

      val boxTotal = areas.map(side => side * 2).sum
      val withExtra = boxTotal + areas.min

      println(withExtra)
      withExtra
    })
      .sum

    println(s"res: $result")
  }
}
