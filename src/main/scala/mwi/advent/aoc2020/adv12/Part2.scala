package mwi.advent.aoc2020.adv12

object Part2 {

  var locX = 0
  var locY = 0

  var wpLocX = 10 // 10 east
  var wpLocY = 1 // 1 north

  def solve(input: Array[String]) = {

    for (line <- input) {

      val command = line.charAt(0)
      val value = line.substring(1).toInt

      command match {
        case 'R' => rotateRight(value)
        case 'L' => rotateRight(-1 * value)
        case 'N' => wpLocY = wpLocY + value
        case 'S' => wpLocY = wpLocY - value
        case 'E' => wpLocX = wpLocX + value
        case 'W' => wpLocX = wpLocX - value
        case 'F' => moveWithVector(value)
      }

    }
    println(s"ver: $locY hor: $locX")
    println(s"res: ${Math.abs(locY) + Math.abs(locX)}")
  }

  private def moveWithVector(value: Int) = {
    locX = locX + (value * wpLocX)
    locY = locY + (value * wpLocY)
  }

  private def rotateRight(degrees: Int) = {
    val rad = math.toRadians(degrees)
    val newWpLocX = (wpLocX * Math.cos(rad) + wpLocY * Math.sin(rad))
    val newWpLocY = (-1 * wpLocX * Math.sin(rad) + wpLocY * Math.cos(rad))
    wpLocX = Math.round(newWpLocX).intValue()
    wpLocY = Math.round(newWpLocY).intValue()
  }
}
