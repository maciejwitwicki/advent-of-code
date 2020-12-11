package aoc2020.adv11

import scala.io.Source

object Advent_11 extends App {

  val inputFileName = this.getClass.getSimpleName.replace("_", "-").replace("$", "").toLowerCase

  var inputFile: Array[String] = Source.fromResource(s"aoc2020/$inputFileName.txt").getLines().toArray

  val s1 =
    """L.LL.LL.LL
      |LLLLLLL.LL
      |L.L.L..L..
      |LLLL.LL.LL
      |L.LL.LL.LL
      |L.LLLLL.LL
      |..L.L.....
      |LLLLLLLLLL
      |L.LLLLLL.L
      |L.LLLLL.LL
      |""".stripMargin

  val s2 =
    """.......#.
      |...#.....
      |.#.......
      |.........
      |..#L....#
      |....#....
      |.........
      |#........
      |...#.....
      |""".stripMargin

  val s3 =
    """.............
      |.L.L.#.#.#.#.
      |.............
      |""".stripMargin

  val s4 =
    """.##.##.
      |#.#.#.#
      |##...##
      |...L...
      |##...##
      |#.#.#.#
      |.##.##.
      |""".stripMargin

   val input1 = Source.fromString(s1).getLines().toArray
   val input2 = Source.fromString(s2).getLines().toArray
   val input3 = Source.fromString(s3).getLines().toArray
   val input4 = Source.fromString(s4).getLines().toArray

   //Part1.solve(inputFile)
   Part2.solve(inputFile)

}
