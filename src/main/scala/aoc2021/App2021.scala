package aoc2021

import aoc2021.App2021._
import scalaj.http.Http

import java.io.{File, PrintWriter}
import java.nio.file.Paths
import scala.io.Source
import scala.util.{Try, Using}

class App2021 extends App {

  def getInput(multilineString: String): Array[String] = {
    multilineString.split(NewLine)
  }

  def getInput(clazz: Class[_]): Array[String] = {
    val info = getFileName(clazz)

    getInputFromFile(info.fileName)
      .recoverWith {
        case error =>
          println(s"Loading resource from the web, because of ERROR: $error")

          getInputFromUrl(info.day)
            .map(input => writeLinesToFile(input, info.fileName))
      }
      .get

  }

  private def getInputFromFile(fileName: String): Try[Array[String]] = {
    val path = getFilePath(fileName)
    Using(Source.fromResource(path))(s => {
      s.getLines().toArray
    })

  }

  private def getInputFromUrl(day: Int): Try[Array[String]] = {
    val url = s"$UrlPrefix/$Year/day/$day/input"
    val sessionCookieFile = Paths.get("session-cookie.txt").toAbsolutePath.toFile

    if (!sessionCookieFile.exists())
      throw new RuntimeException("session-cookie.txt file not found!")

    Using(Source.fromFile(sessionCookieFile)) { source =>
      val cookie = source.getLines().toArray.apply(0)
      Http(url)
        .cookie("session", cookie)
        .asString
        .body
        .split(NewLine)
    }
  }

  private def writeLinesToFile(lines: Array[String], fileName: String): Array[String] = {
    val absolutePath = Paths.get("src", "main", "resources", PackageName)
      .toAbsolutePath
      .toFile
    val newFile = new File(absolutePath, fileName)
    Using(new PrintWriter(newFile)) {
      writer => lines.foreach(writer.println)
    }
    lines
  }

  private def getFilePath(fileName: String): String = {
    s"$PackageName/$fileName"
  }

  private def getFileName(clazz: Class[_]): DayInfo = {
    val split = clazz.getSimpleName
      .replace("$", "")
      .split("_")

    val prefix = split(0).toLowerCase
    val day = split(1).stripMargin('0').toInt // redundant but just in case I decide to name classes differently one day
    val paddedDay = f"$day%02d" // redundant but just in case I decide to name classes differently one day

    DayInfo(day, s"$prefix-$paddedDay.txt")
  }

}

object App2021 {
  val NewLine = "\n"
  val Year = 2021
  val UrlPrefix = "https://adventofcode.com"
  val PackageName = "aoc2021"

  case class DayInfo(day: Int, fileName: String)
}
