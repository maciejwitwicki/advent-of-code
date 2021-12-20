package mwi.advent.aoc2021

package object adv18 {
  private[adv18] sealed abstract class Node {
    def isValue: Boolean

    def isPair: Boolean

    def getMagnitude: Long
  }

  private[adv18] case class PairNode(left: Node, right: Node) extends Node {
    override def isPair: Boolean = true

    override def isValue: Boolean = false

    override def toString: String = s"[$left,$right]"

    override def getMagnitude: Long = {
      3 * left.getMagnitude + 2 * right.getMagnitude
    }
  }

  private[adv18] case class ValueNode(value: Int) extends Node {
    override def isPair: Boolean = false

    override def isValue: Boolean = true

    override def getMagnitude: Long = value.longValue

    override def toString: String = s"$value"
  }
}
