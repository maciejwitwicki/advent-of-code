package aoc2020.adv21

import scala.collection.mutable


object Part1 {

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

    println(s"\nIngredients that were not duplicated")
    val duplicatedIngredients = map2.values.flatten.toSet
    val notDuplicated = allIngredients.diff(duplicatedIngredients)

    notDuplicated.foreach(println)

    println(s"\nCount them")
    var count = 0
    for (recipe <- recipesAlone) {
      count = count + recipe.count(ingredient => notDuplicated.contains(ingredient))

    }
    println(count)
  }








}

case class Food(name: String, possibilities: Map[String, Double]) {
  def biggestPossibility = possibilities.values.max
}