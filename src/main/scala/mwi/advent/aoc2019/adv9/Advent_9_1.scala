package mwi.advent.aoc2019.adv9

import scala.io.Source

object Advent_9_1 extends App {


  var reader = Source.fromResource("mwi/advent/aoc2019/advent-9.txt").bufferedReader()
  val inputFile = reader.readLine()

  val input1 = "109,1,204,-1,1001,100,1,100,1008,100,16,101,1006,101,0,99"
  val input2 = "1102,34915192,34915192,7,4,7,99,0"
  val input3 = "104,1125899906842624,99"
  val input4 = "109,1,203,10,99"

   Solution.solve(inputFile)

}

object Solution {

 def solve(input: String) = {
   val comp = new IntcodeComputer(input)
   comp.calc()

 }
}
