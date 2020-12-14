package aoc2020.adv13

import scala.io.Source

object Advent_13 extends App {

  val inputFileName = this.getClass.getSimpleName.replace("_", "-").replace("$", "").toLowerCase

  var inputFile: Array[String] = Source.fromResource(s"aoc2020/$inputFileName.txt").getLines().toArray

  val s1 =
    """F10
      |7,13,x,x,59,x,31,19
      |""".stripMargin


  val s2 =
    """F10
      |17,x,13,19
      |""".stripMargin



  val s3 =
    """F10
      |67,7,59,61
      |""".stripMargin



  val s4 =
    """F10
      |67,x,7,59,61
      |""".stripMargin



  val s5 =
    """F10
      |67,7,x,59,61
      |""".stripMargin



  val s6 =
    """F10
      |1789,37,47,1889
      |""".stripMargin


   val input1 = Source.fromString(s1).getLines().toArray
   val input2 = Source.fromString(s2).getLines().toArray
   val input3 = Source.fromString(s3).getLines().toArray
   val input4 = Source.fromString(s4).getLines().toArray
   val input5 = Source.fromString(s5).getLines().toArray
   val input6 = Source.fromString(s6).getLines().toArray

   //Part1.solve(inputFile)
   Part2.solve(inputFile)

}
