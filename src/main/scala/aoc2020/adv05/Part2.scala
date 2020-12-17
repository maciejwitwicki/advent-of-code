package aoc2020.adv05

object Part2 {

  def solve(input: Array[String]) = {

    val x = Integer.parseInt("100",2)
    println(x)

    val all = input.map(line => {
      val row = line.substring(0, 7).replaceAll("F", "0").replaceAll("B", "1")
      val col = line.substring(7).replaceAll("L", "0").replaceAll("R", "1")

      val rowInt = Integer.parseInt(row, 2)
      val colInt = Integer.parseInt(col, 2)


      val r = rowInt * 8 + colInt

      r
    }).sorted

    println(all.mkString("\n"))


  }

}


