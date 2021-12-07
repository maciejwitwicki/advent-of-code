package mwi.advent.aoc2015.adv01

object Part2 {

  def solve(input: Array[String]): Unit = {
    val line = input.head
    var i,pos = 0
    var found = false
    while (!found) {

      pos += (if (line(i) == '(') 1 else -1)

      if (pos == -1) {
        found = true
      } else {
        i += 1
      }
    }


    println(s"element: ${i+1}")
  }
}
