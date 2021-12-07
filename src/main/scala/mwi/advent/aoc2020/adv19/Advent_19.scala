package mwi.advent.aoc2020.adv19

import scala.io.Source

object Advent_19 extends App {

  val inputFileName = this.getClass.getSimpleName.replace("_", "-").replace("$", "").toLowerCase

  var inputFile: Array[String] = Source.fromResource(s"mwi.aoc.aoc2020/$inputFileName.txt").getLines().toArray

  val s1 =
    """0: 1 2
      |1: "a"
      |2: 1 3 | 3 1
      |3: "b"
      |""".stripMargin

  val s2 =
    """0: 4 1 5
      |1: 2 3 | 3 2
      |2: 4 4 | 5 5
      |3: 4 5 | 5 4
      |4: "a"
      |5: "b"
      |""".stripMargin

  val s3 =
    """0: 4 1 5
      |1: 2 3 | 3 2
      |2: 4 4 | 5 5
      |3: 4 5 | 5 4
      |4: "a"
      |5: "b"
      |
      |ababbb
      |bababa
      |abbbab
      |aaabbb
      |aaaabbb
      |""".stripMargin

  val s4 =
    """42: 9 14 | 10 1
      |9: 14 27 | 1 26
      |10: 23 14 | 28 1
      |1: "a"
      |11: 42 31
      |5: 1 14 | 15 1
      |19: 14 1 | 14 14
      |12: 24 14 | 19 1
      |16: 15 1 | 14 14
      |31: 14 17 | 1 13
      |6: 14 14 | 1 14
      |2: 1 24 | 14 4
      |0: 8 11
      |13: 14 3 | 1 12
      |15: 1 | 14
      |17: 14 2 | 1 7
      |23: 25 1 | 22 14
      |28: 16 1
      |4: 1 1
      |20: 14 14 | 1 15
      |3: 5 14 | 16 1
      |27: 1 6 | 14 18
      |14: "b"
      |21: 14 1 | 1 14
      |25: 1 1 | 1 14
      |22: 14 14
      |8: 42
      |26: 14 22 | 1 20
      |18: 15 15
      |7: 14 5 | 1 21
      |24: 14 1
      |
      |abbbbbabbbaaaababbaabbbbabababbbabbbbbbabaaaa
      |bbabbbbaabaabba
      |babbbbaabbbbbabbbbbbaabaaabaaa
      |aaabbbbbbaaaabaababaabababbabaaabbababababaaa
      |bbbbbbbaaaabbbbaaabbabaaa
      |bbbababbbbaaaaaaaabbababaaababaabab
      |ababaaaaaabaaab
      |ababaaaaabbbaba
      |baabbaaaabbaaaababbaababb
      |abbbbabbbbaaaababbbbbbaaaababb
      |aaaaabbaabaaaaababaa
      |aaaabbaaaabbaaa
      |aaaabbaabbaaaaaaabbbabbbaaabbaabaaa
      |babaaabbbaaabaababbaabababaaab
      |aabbbbbaabbbaaaaaabbbbbababaaaaabbaaabba
      |""".stripMargin

   val input1 = Source.fromString(s1).getLines().toArray
   val input2 = Source.fromString(s2).getLines().toArray
   val input3 = Source.fromString(s3).getLines().toArray
   val input4 = Source.fromString(s4).getLines().toArray


   //Part1.solve(input3)
   Part2.solve(inputFile) // 370 to high, 353 to high// 341 correct
}
