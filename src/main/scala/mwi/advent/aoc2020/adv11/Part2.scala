package mwi.advent.aoc2020.adv11

object Part2 {

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
    countOccupiedNeighbors(y, x) >= 5
  }

  private def shouldOccupyTheSeat(y: Int, x: Int) = {
    countOccupiedNeighbors(y, x) == 0
  }

  private def countOccupiedNeighbors(y: Int, x: Int) = {
    var occupiedNeighbors = 0
    var found = false

    def find(a: Int, b: Int) = {
      val value = grid(a)(b)
      value match {
        case '#' =>
          occupiedNeighbors = occupiedNeighbors + 1
          found = true
        case 'L' => found = true
        case _ =>
      }
    }

    // search to the left
    var i = x - 1
    while(!found && i >= 0) {
      find(y, i)
      i = i - 1
    }

    // search to the right
    found = false
    i = x + 1
    while(!found && i < colsLength) {
      find(y, i)
      i = i + 1
    }

    // search to the top
    i = y - 1
    found = false
    while(!found && i >= 0) {
      find(i, x)
      i = i - 1
    }

    // search to the bottom
    found = false
    i = y + 1
    while(!found && i < rowsLength) {
      find(i, x)
      i = i + 1
    }

    // search to the top left
    i = y - 1
    var j = x - 1
    found = false
    while(!found && (i >= 0 && j >= 0)) {
      find(i, j)
      i = i - 1
      j = j - 1
    }

    // search to the top right
    i = y - 1
    j = x + 1
    found = false
    while(!found && (i > -1 && j < colsLength)) {
      find(i, j)
      i = i - 1
      j = j + 1
    }

    // search to the bot left
    i = y + 1
    j = x - 1
    found = false
    while(!found && (i < rowsLength && j > -1)) {
      find(i, j)
      i = i + 1
      j = j - 1
    }

    // search to the bot right
    i = y + 1
    j = x + 1
    found = false
    while(!found && (i < rowsLength && j < colsLength)) {
      find(i, j)
      i = i + 1
      j = j + 1
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
