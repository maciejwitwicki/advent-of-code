package aoc2020.adv12

object Part1 {

  var facing = 'E'

  var locHor = 0
  var locVer = 0

  def solve(input: Array[String]) = {

    for(line <- input) {

      val command = line.charAt(0)
      val value = line.substring(1).toInt

      command match {
        case 'R' => facing = rotateRight(value)
        case 'L' => facing = rotateLeft(value)
        case 'N' => locVer = locVer + value
        case 'S' => locVer = locVer - value
        case 'E' => locHor = locHor + value
        case 'W' => locHor = locHor - value
        case 'F' => moveForward(value)
      }

    }
    println(s"ver: $locVer hor: $locHor")
    println(s"res: ${Math.abs(locVer) + Math.abs(locHor)}")
  }

  private def moveForward(value: Int) = {
    facing match {
      case 'N' => locVer = locVer + value
      case 'S' => locVer = locVer - value
      case 'E' => locHor = locHor + value
      case 'W' => locHor = locHor - value
    }
  }

  private def rotateRight(degrees: Int) = {
      val newFacing = (facing, degrees) match {
        case ('E', 90) => 'S'
        case ('E', 180) => 'W'
        case ('E', 270) => 'N'
        case ('S', 90) => 'W'
        case ('S', 180) => 'N'
        case ('S', 270) => 'E'
        case ('W', 90) => 'N'
        case ('W', 180) => 'E'
        case ('W', 270) => 'S'
        case ('N', 90) => 'E'
        case ('N', 180) => 'S'
        case ('N', 270) => 'W'
      }
      newFacing
    }
  private def rotateLeft(degrees: Int) = {
    val newFacing = (facing, degrees) match {
      case ('E', 90) => 'N'
      case ('E', 180) => 'W'
      case ('E', 270) => 'S'
      case ('S', 90) => 'E'
      case ('S', 180) => 'N'
      case ('S', 270) => 'W'
      case ('W', 90) => 'S'
      case ('W', 180) => 'E'
      case ('W', 270) => 'N'
      case ('N', 90) => 'W'
      case ('N', 180) => 'S'
      case ('N', 270) => 'E'
    }
    newFacing
  }
}


