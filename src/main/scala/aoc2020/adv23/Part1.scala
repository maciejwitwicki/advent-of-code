package aoc2020.adv23


object Part1 {

  def solve(input: Array[String]) = {
    var cups: Array[Int] = input(0).toCharArray.map(c => c.toString).map(Integer.parseInt)
    val lowestLabel = cups.min
    val highestLabel = cups.max
    var it = 0
    var currentIndex = 0

    while (it < 100) {
      val currentCup = cups(currentIndex)

      val p1Idx = (currentIndex + 1) % cups.length
      val p2Idx = (currentIndex + 2) % cups.length
      val p3Idx = (currentIndex + 3) % cups.length
      val pickUp = Array(cups(p1Idx), cups(p2Idx), cups(p3Idx))
      val pickUpIndexes = Array(p1Idx, p2Idx, p3Idx)

      // cups without picked cups
      var newCups = Array.empty[Int]
      var tmpIt = 0
      while(tmpIt < cups.length) {
        if (!pickUpIndexes.contains(tmpIt)) newCups = newCups :+ cups(tmpIt)
        tmpIt = tmpIt + 1
      }


      // find destination label
      var destinationLabel = currentCup - 1
      if (destinationLabel < lowestLabel) destinationLabel = highestLabel

      while (pickUp.contains(destinationLabel)) {
        destinationLabel = destinationLabel - 1
        if (destinationLabel < lowestLabel) destinationLabel = highestLabel
      }

      // destination idx
      val destIdx = (newCups.indexOf(destinationLabel) + 1)
      val (dstPrefix, dstSuffix) = newCups.splitAt(destIdx)

      val result = dstPrefix ++ pickUp ++ dstSuffix

      println(result.mkString)
      cups = result

      currentIndex = (cups.indexOf(currentCup) + 1) % cups.length

      it = it + 1
    }

    println("Iterations finished, calculating result")

    val (resultPrefix, resultPostfix) = cups.splitAt(cups.indexOf(1))
    val result = resultPostfix.drop(1) ++ resultPrefix

    println(result.mkString)

  }
}
