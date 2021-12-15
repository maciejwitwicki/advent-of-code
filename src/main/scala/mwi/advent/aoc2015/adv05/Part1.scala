package mwi.advent.aoc2015.adv05


import mwi.advent.util.Loc

import java.security.MessageDigest

object Part1 {

  private val vovels = List('a', 'e', 'i', 'o', 'u')
  private val forbidden = List("ab", "cd", "pq", "xy")
  
  def solve(input: Array[String]): Unit = {
  
    var nice = 0
    input.foreach(line => {
      
      var vovelCount = 0
      var duplicates = 0
      var containsForbiden = false
      
      for (i <- 0 until line.length) {
        val ch = line(i)
        
        if (i < line.length - 1) {
          val nextCh = line(i + 1)
          if (ch == nextCh) {
            duplicates += 1
          }
          if (forbidden.contains(s"$ch$nextCh")) containsForbiden = true
        }
        
        if (vovels.contains(ch)) vovelCount += 1
      }
      if (vovelCount >= 3 && duplicates > 0 && !containsForbiden) nice += 1
    })
    
    println(s"Found $nice nice strings")
    
  }


}
