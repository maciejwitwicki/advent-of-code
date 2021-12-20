package mwi.advent.aoc2021.adv19.impl

import mwi.advent.util.{Loc, Loc3d}
import mwi.advent.aoc2021.adv19.Part1.Locs

import scala.collection.mutable

private[adv19] object Parser {

  def parseScanerInput(input: Array[String]): mutable.ArrayBuffer[Locs] = {
    val scannerInput: mutable.ArrayBuffer[Locs] = mutable.ArrayBuffer.empty[Locs]

    var tmp = mutable.ArrayBuffer.empty[Loc3d]
    for (line <- input) {
      if (line.startsWith("---")) {
        if (tmp.nonEmpty) {
          scannerInput.append(mutable.HashSet.empty.addAll(tmp))
        }
        tmp = mutable.ArrayBuffer.empty[Loc3d]
      } else if (!line.isBlank) {
        val coords = line.split(",")
        tmp.append(Loc3d(coords(0).toInt, coords(1).toInt, coords(2).toInt))
      }
    }

    // last scaner input needs to be added too
    if (tmp.nonEmpty) {
      scannerInput.append(mutable.HashSet.empty.addAll(tmp))
    }

    scannerInput

  }
}
