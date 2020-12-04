package adv11

import scala.collection.mutable
import scala.io.Source

object Advent_11_1 extends App {

  var fileInputArray: Array[String] = Source.fromResource("advent-11.txt").getLines().toArray
  var fileInput = fileInputArray(0)

  Solution.solve(fileInput)
}

case class Point(x: Int, y: Int)

case class Panel(initialColour: Int = 0) {
  var colour: Int = initialColour
  var paintingCount = 0

  def paint(colour: Int) = {
    this.colour = colour
    paintingCount += 1
    println(s"Paiting panel $colour, it's $paintingCount layer of paint")
  }
}

object Solution {

  def solve(input: String) = {
    val comp = new IntcodeComputer(input)

    val grid = mutable.Map.empty[Point, Panel]

    val robot = PaintingRobot(comp)

    var currentLocation = Point(0,0)
    val INITIAL_COLOR = 1
    grid.put(currentLocation, Panel(INITIAL_COLOR))

    var run = true

    while (run) {
      println(s"Robot is in location $currentLocation")
      val currentPanel = grid.getOrElse(currentLocation, Panel())
      val (newColor, newLocation, halt) = robot.step( currentLocation, currentPanel.colour)

      if (!halt) {
        currentPanel.paint(newColor)
        grid.put(currentLocation, currentPanel)
        currentLocation = newLocation
      } else run = false
    }
    println(s"painted panels: ${grid.size}")
    printGrid(grid)
  }

  def printGrid(grid: mutable.Map[Point, Panel]) = {
    var minX, minY, maxX, maxY = 0

    grid.foreach((x) => {
      val point = x._1
      val panel = x._2
      minX = Math.min(minX, point.x)
      minY = Math.min(minY, point.y)
      maxX = Math.max(maxX, point.x)
      maxY = Math.max(maxY, point.y)
    })

    val offsetX = Math.abs(minX)
    val offsetY = Math.abs(minY)
    val width = offsetX + Math.abs(maxX) + 1
    val height = offsetY + Math.abs(maxY) + 1

    val arr: Array[Array[Char]] = Array.ofDim(height, width)

    grid.foreach((x => {
      val point = x._1
      val panel = x._2
      if (panel.colour == 1 ) {
        val arrY = point.y + offsetY
        val arrX = point.x + offsetX
        if (arrY > height || arrX > width ) {
          println(s"some fakup for $arrX $arrY when width: $width height: $height")
        } else arr(arrY)(arrX) = '█'
      }
    }))

    arr.foreach(a => {
      println(s"${a.mkString}")
    })

  }
}

case class PaintingRobot(program: IntcodeComputer) {

  var direction: Char = 'U'

  def step (location: Point, colourInLocation: Int): (Int, Point, Boolean) = {


    val colorToPaint: Option[BigDecimal] = program.calc(colourInLocation)

    val rotation = program.calc()

    val newLocation = rotation.map(r => {
      changeDirection(r.toInt)
      changeLocation(location)
    })

    val shouldHalt = colorToPaint.isEmpty || newLocation.isEmpty

    (colorToPaint.getOrElse(BigDecimal(-1)).intValue, newLocation.getOrElse(Point(0,0)), shouldHalt)
  }

  private def changeLocation(prevLocation: Point) =  {
    direction match {
      case 'U' => prevLocation.copy(y = prevLocation.y + 1)
      case 'R' => prevLocation.copy(x = prevLocation.x + 1)
      case 'D' => prevLocation.copy(y = prevLocation.y - 1)
      case 'L' => prevLocation.copy(x = prevLocation.x - 1)
    }
  }

  // 0 = rotate left, 1 = rotate right 90 degrees
  private def changeDirection(rotation: Int) = {
    direction match {
      case 'U' => if (rotation == 1) direction = 'R' else direction = 'L'
      case 'R' => if (rotation == 1) direction = 'D' else direction = 'U'
      case 'D' => if (rotation == 1) direction = 'L' else direction = 'R'
      case 'L' => if (rotation == 1) direction = 'U' else direction = 'D'
    }
  }

}