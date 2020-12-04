package aoc2020.adv04

import com.sun.javaws.exceptions.InvalidArgumentException

import scala.util.Try
import scala.util.matching.Regex

object Part2 {

  val requiredFields = Set("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid")

  def solve(input: Array[String]) = {

    var fullInputs = List.empty[String]

    var tmpEntry = ""

    for (line <- input)
      if (line.nonEmpty) {
        tmpEntry = tmpEntry + " " + line
      } else {
        fullInputs = fullInputs :+ tmpEntry
        tmpEntry = ""
      }

    if (tmpEntry.nonEmpty)
      fullInputs = fullInputs :+ tmpEntry

    println(fullInputs)
    println(s"tmp entry: $tmpEntry")

    val result = fullInputs.map(i => {
      val valid = allFieldsValid(i)
      if (valid) {
        val sorted = i.split(' ').sorted.mkString(" ")
        println(s"$valid - $sorted")
      }
      valid
    }).count(z => z)

    println(s"Result of task 2 $result")

  }

  private def allFieldsValid(str: String) =
    if (allRequiredFieldsPresent(str))
      allFiedsWithProperValues(str)
    else false


  private def allRequiredFieldsPresent(str: String) = {
    var ok = true

    for (f <- requiredFields) {
      if (!str.contains(s"$f:")) ok = false
    }

    ok
  }

  private def allFiedsWithProperValues(str: String) = {
    var ok = true

    val fields = str.trim.split(' ')

    fields.foreach(f => {

      val x = f.split(':')
      val field = x(0)
      val value = x(1)

      val fieldResult = field match {
        case "byr" => validateByr(value)
        case "iyr" => validateIyr(value)
        case "eyr" => validateEyr(value)
        case "hgt" => {
          val x = validateHgt(value)
//          println(s"Height validation $value - $x")
          x
        }
        case "hcl" => validateHcl(value)
        case "ecl" => validateEcl(value)
        case "pid" => validatePid(value)
        case "cid" => true
        case x => throw new InvalidArgumentException(Array(s"invalid field: $x"))
      }
      if (fieldResult == false)
        ok = false

    })

    ok
  }

  private def validateByr(y: String) =
    Try({
      val year = y.toInt
      year >= 1920 && year <= 2002
    }).fold(err => false, result => result)

  private def validateIyr(y: String) =
    Try({
      val year = y.toInt
      year >= 2010 && year <= 2020
    }).fold(err => false, result => result)

  private def validateEyr(y: String) =
    Try({
      val year = y.toInt
      year >= 2020 && year <= 2030
    }).fold(err => false, result => result)


  private def validateHgt(h: String) = {
    if (h.contains("cm")) {
      Try({
        val height = h.substring(0, h.indexOf("cm")).toInt
        height >= 150 && height <= 193
      }).fold(err => false, result => result)
    }
    else if (h.contains("in")) {
      Try({
        val height = h.substring(0, h.indexOf("in")).toInt
        height >= 59 && height <= 76
      }).fold(err => false, result => result)
    }
    else false

  }

  private def validateHcl(c: String) = {
    val hex = raw"[0-9a-f]{6}".r
    if (c.length == 7 && c.startsWith("#")) {
      val maybeString = hex.findFirstIn(c.substring(1))
      maybeString.map(_ => true).getOrElse(false)
    }
    else false
  }
  private def validatePid(c: String) = {
    val hex = raw"[0-9]{6}".r
    if (c.length == 9) {
      val maybeString = hex.findFirstIn(c)
      maybeString.map(_ => true).getOrElse(false)
    }
    else false
  }
  private def validateEcl(c: String) = {
    val validColor = Set("amb", "blu", "brn", "gry", "grn", "hzl", "oth")
    validColor.contains(c)
  }

}


