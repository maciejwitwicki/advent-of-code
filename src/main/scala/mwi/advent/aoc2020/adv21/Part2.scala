package mwi.advent.aoc2020.adv21

import scala.collection.mutable

object Part2 {
  def solve(input: Array[String]) = {

    var allIngredients = Set.empty[String]
    var recipesAlone = Array.empty[Set[String]]
    var alergensToSets = mutable.HashMap.empty[String, Array[Set[String]]]

    for (line <- input) {

      val x1 = line.dropRight(1).split(" \\(contains ")
      val meals: Set[String] = x1(0).split(" ").toSet
      val allergens = x1(1).split(", ")

      allIngredients = allIngredients ++ meals
      recipesAlone = recipesAlone :+ meals

      allergens.foreach(a => {
        val prevMeals: Array[Set[String]] = alergensToSets.getOrElse(a, Array.empty)
        val newMeals = prevMeals :+ meals
        alergensToSets.put(a, newMeals)

      })

    }

    // print what we got
    alergensToSets.foreach(al => {
      println(s"${al._1} -> ${al._2.map(se => se.mkString(", ")).mkString(" and ")}")
    })

    println(s"\nLooking for ingredients that occur in every recipe")


    val map2 = mutable.HashMap.empty[String, Set[String]]

    alergensToSets.foreach(al => {

      val name = al._1
      val sets = al._2

      var it = 0

      var tmpIntersection = sets(it)
      while (it < sets.length - 1) {
        tmpIntersection = tmpIntersection.intersect(sets(it + 1))

        it = it + 1
      }

      map2.put(name, tmpIntersection)

      println(s"$name -> ${tmpIntersection.mkString(", ")}")

    })

    println(s"\nGeting a map of single alergen - ingredients and multiple one should decrease to 0")

    var (singleIngredients, multipleIngredients) = map2.partition(e => e._2.size == 1)


    var process = true

    var c = 0
    while(process) {
      println(s"Iteration $c"); c= c + 1
      val usedIngredients = singleIngredients.values.flatten.toSet

      val filteredMulti = multipleIngredients.map(entry => {
        val ingredients = entry._2

        val newIngredients = ingredients.diff(usedIngredients)

        entry._1 -> newIngredients
      })

      val (newSingle, newMulti) = filteredMulti.partition(e => e._2.size == 1)

      if (newMulti.isEmpty) process = false
      singleIngredients = singleIngredients ++ newSingle
      multipleIngredients = newMulti
    }

    singleIngredients.foreach(e => println(s"${e._1} -> ${e._2.head}"))

    val res = singleIngredients.toList.sortBy(_._1).map(_._2.head).mkString(",")
    println(res)


  }
}
