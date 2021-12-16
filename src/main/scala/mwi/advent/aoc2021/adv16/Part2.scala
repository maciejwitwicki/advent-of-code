
package mwi.advent.aoc2021.adv16

import mwi.advent.util.Loc

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

object Part2 {
  def solve(input: Array[String]): Unit = {

    input.foreach(line => {
      val bits = line.toArray.map(ch => s"$ch").map(s => {
        val bit = BigInt(s, 16).toString(2).toInt
        f"$bit%04d"
      })

      val packetBits = bits.mkString
      val (packet, bitsLeft) = parse(packetBits)

      println(s"$line -> ${processOperations(packet).value}")
    })

  }

  private def parse(bits: String): (Packet, String) = {
    val (packetVersion, rest) = bits.splitAt(3)
    val (packetType, data) = rest.splitAt(3)

    val packetTypeStr = BigInt(packetType, 2) match {
      case 0 => "SUM"
      case 1 => "MULTIPLY"
      case 2 => "MIN"
      case 3 => "MAX"
      case 4 => "VALUE"
      case 5 => "GT"
      case 6 => "LT"
      case 7 => "EQ"
    }

    val p = Packet(packetVersion, packetType, data, 0L, packetTypeStr, List.empty)

    if (packetTypeStr == "VALUE") {
      setValueInValuePacket(p)
    } else {
      handleOperatorPacket(p)
    }
  }

  private def handleOperatorPacket(packet: Packet): (Packet, String) = {

    val (lengthId, rest) = packet.data.splitAt(1)

    val subPackets = ArrayBuffer.empty[Packet]
    var remainingData = ""

    if (lengthId == "0") {
      val (totalLength, rest2) = rest.splitAt(15)

      val length = BigInt(totalLength, 2).toInt
      val (subpacketsBits, afterSubpacketsBits) = rest2.splitAt(length)

      val (subPacket, tail) = parse(subpacketsBits)
      subPackets.append(subPacket)
      var subpacketsRemainingBits = tail
      while (subpacketsRemainingBits.length != 0) {
        val (s, t) = parse(subpacketsRemainingBits)
        subPackets.append(s)
        subpacketsRemainingBits = t
      }

      assert(trimZeroes(subpacketsRemainingBits).size == 0, "subpackets bits not cleared!")

      (packet.copy(childPackets = subPackets.toList), trimZeroes(afterSubpacketsBits))

    } else {
      assert(lengthId == "1", s"Unexpected length id $lengthId")

      val (numberOfSubPackets, rest2) = rest.splitAt(11)
      val expectedNumberOfSubpackets = BigInt(numberOfSubPackets, 2).toInt

      assert(expectedNumberOfSubpackets > 0, "No expected subpackets!")

      var remainingBits = rest2

      while (subPackets.length < expectedNumberOfSubpackets) {
        val (s, t) = parse(remainingBits)
        subPackets.append(s)
        remainingBits = t
      }

      (packet.copy(childPackets = subPackets.toList), trimZeroes(remainingBits))


    }
  }

  private def processOperations(packet: Packet): Packet = {
    val updatedChildren = packet.childPackets.map(processOperations)

    var value = 0L
    val subVals = updatedChildren.map(_.value)
    value = packet.packetType match {
      case "000" => subVals.sum
      case "001" => subVals.foldLeft(1L)(_ * _)
      case "010" => subVals.min
      case "011" => subVals.max
      case "100" => packet.value // it's value packet so leave it as is
      case "101" => //gt
        assert(subVals.size == 2, "unexpected subs size")
        if (subVals(0) > subVals(1)) 1L else 0L
      case "110" => //lt
        assert(subVals.size == 2, "unexpected subs size")
        if (subVals(0) < subVals(1)) 1L else 0L
      case "111" => // eq
        assert(subVals.size == 2, "unexpected subs size")
        if (subVals(0) == subVals(1)) 1L else 0L
    }

    packet.copy(value = value, childPackets = updatedChildren)
  }


  private def setValueInValuePacket(packet: Part2.Packet): (Packet, String) = {
    val dataGroups = ArrayBuffer.empty[String]
    var isLast = false
    var remainingData = packet.data
    while (!isLast) {
      val (indicator, rest) = remainingData.splitAt(1)
      val (newGroup, newRemainingData) = rest.splitAt(4)
      dataGroups.append(newGroup)
      remainingData = newRemainingData
      if (indicator == "0") isLast = true
    }

    val value = BigInt(dataGroups.mkString, 2).toLong
    val updatedPacket = packet.copy(value = value)

    (updatedPacket, remainingData)
  }

  private def hasMoreThanZeroes(remainingData: String): Boolean = {
    remainingData.replace("0", "").length > 0
  }

  private def trimZeroes(str: String): String = {
    if (hasMoreThanZeroes(str)) {
      str
    } else ""
  }

  private case class Packet(version: String, packetType: String, data: String, value: Long, typeStr: String, childPackets: List[Packet])

}
