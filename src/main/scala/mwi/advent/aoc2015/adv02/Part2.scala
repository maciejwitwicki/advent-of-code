package mwi.advent.aoc2015.adv02

object Part2 {

  def solve(input: Array[String]): Unit = {

    val result = input.map(line => {
      val dims = line.split("x").map(Integer.parseInt)

      val l = dims(0)
      val w = dims(1)
      val h = dims(2)

      val twoShorterDims = List(l, w, h).sorted.take(2)
      
      val ribbonToWrap = twoShorterDims.map(d => d * 2).sum
      val ribbonForBow = l * w * h
      val total = ribbonForBow + ribbonToWrap

      println(total)
      total
    })
      .sum

    println(s"res: $result")
  }
}
