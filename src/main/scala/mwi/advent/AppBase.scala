package mwi.advent

import mwi.advent.AppBase.{DayInfo, NewLine, PackageNamePrefix, UrlPrefix}
import scalaj.http.Http

import java.io.{File, PrintWriter}
import java.nio.file.{Files, Paths}
import scala.io.Source
import scala.util.{Try, Using}

class AppBase(year: Int) extends App {

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
    val url = s"$UrlPrefix/$year/day/$day/input"
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
    val absolutePath = Paths.get("src", "main", "resources", PackageNamePrefix + year)
      .toAbsolutePath

    val dir = Files.createDirectories(absolutePath)

    var exits = dir.toAbsolutePath.toFile.exists()

    val newFile = new File(absolutePath.toFile, fileName)
    Using(new PrintWriter(newFile)) {
      writer => lines.foreach(writer.println)
    }
    lines
  }

  private def getFilePath(fileName: String): String = {
    s"$PackageNamePrefix$year/$fileName"
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

object AppBase {
  val NewLine = "\n"
  val UrlPrefix = "https://adventofcode.com"
  val PackageNamePrefix = "mwi/advent/aoc"

  case class DayInfo(day: Int, fileName: String)
}
