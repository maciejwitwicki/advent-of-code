package aoc2020.adv07

import scala.collection.mutable

object Part2 {

  case class BagEntry(count: Int, name: String)

  val pattern = """^(\d+)(.*)""".r
  val dependencies = mutable.HashMap.empty[String, List[BagEntry]]

  def solve(input: Array[String]) = {


    input.foreach(line => {

      if (line.nonEmpty){
        val x1 = line.replace("bags", "").replace("bag", "").replace(".", "").split("contain")
        val parent = x1(0).trim
        val children = x1(1).split(",")

        children.foreach(chUntrimmed => {
          val ch = chUntrimmed.trim


          if (!ch.contains("no other")) {
            val (fits, child) = pattern.findFirstMatchIn(ch) match {
              case Some(i) => (i.group(1).toInt, i.group(2).trim)
              case _ => throw new IllegalArgumentException("regex not found")
            }

            val prevChildren = dependencies.getOrElse(parent, List.empty)

            dependencies(parent) = prevChildren :+ BagEntry(fits, child)
          }

        })
      }

    })


    println(dependencies.mkString("\n"))

    // let's traverse the tree

    var foundChildren: List[BagEntry] = dependencies.getOrElse("shiny gold", List.empty[BagEntry])
    var tmpChildren = dependencies.getOrElse("shiny gold", List.empty[BagEntry])

    while (tmpChildren.nonEmpty) {

      val newTmpChildren = tmpChildren.flatMap(child => dependencies.getOrElse(child.name, List.empty[BagEntry]).map(e => e.copy(count = e.count * child.count))  )
      foundChildren = foundChildren ++ newTmpChildren

      tmpChildren = newTmpChildren

    }

    println(s"foundChildren ${foundChildren.mkString(",")}")
    println(s"sum: ${foundChildren.map(_.count).sum}")



//    var foundParents = Set.empty[String]
//    var  safetyCount = 0
//    var tmpParents = findParentsFor("shiny gold")
//
//    while (tmpParents.nonEmpty && safetyCount < 1000) {
//      foundParents = foundParents ++ tmpParents
//      tmpParents = findParentsWithCheck(tmpParents)
//      safetyCount = safetyCount + 1
//      if (safetyCount > 900) {
//        println("\n\n\n\n\n\n\n\n safety count reached \n\n\n\n\n\n")
//      }
//    }
//
//    println(foundParents)
//    println(foundParents.size)

  }


}


