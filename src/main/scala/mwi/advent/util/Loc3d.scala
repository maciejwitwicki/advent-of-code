package mwi.advent.util

case class Loc3d(x: Int, y: Int, z: Int) {

  override def toString: String = {
    s"$x,$y,$z"
  }

  def `-`(other: Loc3d): Loc3d = {
    Loc3d(x - other.x, y - other.y, z - other.z)
  }

  def `+`(other: Loc3d): Loc3d = {
    Loc3d(x + other.x, y + other.y, z + other.z)
  }

  def direction(facing: Int): Loc3d = {
    facing match {
      case 0       => this
      case 1       => Loc3d(x, -y, -z)
      case 2       => Loc3d(x, -z, y)
      case 3       => Loc3d(-y, -z, x)
      case 4       => Loc3d(y, -z, -x)
      case 5       => Loc3d(-x, -z, -y)
      case default => throw new IllegalArgumentException("Unknown direction")
    }
  }

  def rotation(rotating: Int): Loc3d = {
    rotating match {
      case 0       => this
      case 1       => Loc3d(-y, x, z)
      case 2       => Loc3d(-x, -y, z)
      case 3       => Loc3d(y, -x, z)
      case default => throw new IllegalArgumentException("Unkdnow rotation")
    }
  }

}
