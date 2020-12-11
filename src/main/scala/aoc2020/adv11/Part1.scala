package aoc2020.adv11

object Part1 {

  var rowsLength = -1
  var colsLength = -1
  var grid = Array.empty[Array[Char]]

  def solve(input: Array[String]) = {
    rowsLength = input.length
    colsLength = input(0).length
    grid = Array.ofDim[Char](rowsLength, colsLength)

    for (y <- 0 until rowsLength) {
      for (x <- 0 until colsLength) {
        grid(y)(x) = input(y).charAt(x)
      }
    }

    // grid is created, now iterate

    var iterator = 0
    var gridNotChanged = true
    var nextGrid = deepCopy(grid)

    while (gridNotChanged && iterator < 100) {
      gridNotChanged = false

      println(s"iteration $iterator")
      grid.foreach(line => {
        println(line.mkString)

      })
      println

      for (y <- 0 until rowsLength; x <- 0 until colsLength) {

        val value = grid(y)(x)

        value match {
          case '#' =>
            if (shouldFleeFromSeat(y, x)) {
              nextGrid(y)(x) = 'L'
              gridNotChanged = true
            }
          case 'L' =>
            if (shouldOccupyTheSeat(y, x)) {
              nextGrid(y)(x) = '#'
              gridNotChanged = true
            }
          case '.' => // do nothing
        }
      }
      grid = deepCopy(nextGrid)
      iterator = iterator + 1
    }
    println(s"finished after $iterator iterations")

    val res = grid.flatMap(line => line).count(c => c == '#')
    println(s"occupied = $res")

  }

  private def shouldFleeFromSeat(y: Int, x: Int) = {
    countOccupiedNeighbors(y, x) >= 4
  }

  private def shouldOccupyTheSeat(y: Int, x: Int) = {
    countOccupiedNeighbors(y, x) == 0
  }

  private def countOccupiedNeighbors(y: Int, x: Int) = {
    var occupiedNeighbors = 0
    for (i <- y - 1 to y + 1; j <- x - 1 to x + 1) {

      // don't check self
      if (!(i == y && j == x)) {

        // don't fall out of bounds
        if (i > -1 && j > -1 && i < rowsLength && j < colsLength) {

          val value = grid(i)(j)
          value match {
            case '#' => occupiedNeighbors = occupiedNeighbors + 1
            case _ =>
          }

        }
      }
    }

    occupiedNeighbors
  }

  private def deepCopy(src: Array[Array[Char]]): Array[Array[Char]] = {
    val dest = Array.ofDim[Char](rowsLength, colsLength)

    for (y <- 0 until rowsLength; x <- 0 until colsLength) {
      dest(y)(x) = src(y)(x)
    }

    dest
  }
}


