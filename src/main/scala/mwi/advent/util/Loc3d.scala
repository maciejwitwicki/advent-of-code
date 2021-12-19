package mwi.advent.util

case class Loc3d(x: Int, y: Int, z: Int) {

  def `-`(other: Loc3d): Loc3d = {
    Loc3d(x - other.x, y - other.y, z - other.z)
  }
}
