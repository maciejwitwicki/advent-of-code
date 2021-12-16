
package mwi.advent.aoc2021.adv16

import mwi.advent.util.Loc

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

object Part1 {
  def solve(input: Array[String]): Unit = {
    val allPackets = input.map(line => {
      val bits = line.toArray.map(ch => s"$ch").map(s => {
        val bit = BigInt(s, 16).toString(2).toInt
        f"$bit%04d"
      })

      bits.mkString
    }).flatMap(line => parsePackets(line))

    allPackets.foreach(println)

    val versum = allPackets.map(p => {
      val v10 = BigInt(p.version, 2).toInt
      v10
    }).sum

    println(s"ver sum $versum")

  }

  private def parsePackets(bitLine: String): List[Packet] = {

    //println(s"bits: $bitsStr")

    val (packetVersion, rest) = bitLine.splitAt(3)
    val (packetType, data) = rest.splitAt(3)

    val p = Packet(packetVersion, packetType, data)


    p.packetType match {
      case "100" =>

        val dataGroups = ArrayBuffer.empty[String]
        var isLast = false
        var remainingData = p.data
        while (!isLast) {

          val (indicator, rest) = remainingData.splitAt(1)

          val (newGroup, newRemainingData) = rest.splitAt(4)

          dataGroups.append(newGroup)
          remainingData = newRemainingData

          if (indicator == "0") isLast = true
        }

        if (hasMoreThanZeroes(remainingData)) {
          List(p) ++ parsePackets(remainingData)
        } else {
          List(p)
        }

      // opeartor
      case default =>

        val (lengthId, rest) = p.data.splitAt(1)

        lengthId match {
          case "0" =>
            val (totalLength, rest2) = rest.splitAt(15)

            val length = BigInt(totalLength, 2).toInt
            val (subpackets, remainingStuff) = rest2.splitAt(length)
            val expectedSubpackets = parsePackets(subpackets)

            val remainingStufPackets = if (hasMoreThanZeroes(remainingStuff)) {
              parsePackets(remainingStuff)
            } else List.empty

            List(p) ++ expectedSubpackets ++ remainingStufPackets
          //println(s"subpackets by size: $subpackets")

          case "1" =>
            val (numberOfSubPackets, rest2) = rest.splitAt(11)
            val number = BigInt(numberOfSubPackets, 2).toInt
            //val subpackets = rest2.grouped(11).take(number)

            val moarPackets = if (number > 0) {
              parsePackets(rest2)
            } else List.empty

            List(p) ++ moarPackets
          //println(s"subpacktes by number: ${subpackets.mkString(",")}")
        }
    }

  }

  private def hasMoreThanZeroes(remainingData: String): Boolean = {
    remainingData.replace("0", "").length > 0
  }

  private case class Packet(version: String, packetType: String, data: String)

}
