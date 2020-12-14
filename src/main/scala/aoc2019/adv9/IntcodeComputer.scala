package aoc2019.adv9

import scala.io.StdIn

class IntcodeComputer(input: String) {

  private var i = 0
  private val MAX_MEMORY_SIZE = 100000000
  private val memory: Array[BigDecimal] = Array.fill(MAX_MEMORY_SIZE)(0)


  private val program = input.split(",").map(BigDecimal.apply)
  Array.copy(program, 0, memory, 0, program.length)

  def calc(args: Long*): Option[BigDecimal] = {

    var argumentIndex = 0
    var relativeBase = 0

    var programFinished = false // true after execution of command 99
    var outputValue: Option[BigDecimal] = None // result of operator 4 or None if operator is 99

    while (!programFinished) {

      val operator = new Operator(memory(i).toInt, relativeBase)
      val code = operator.getCode

      code match {
        // 1,2,3,4 => add param 2 + param 3 and save it under arr[4]
        case 1 => {
          val params = operator.parseParams(i, 2, memory)
          val saveIndex = operator.parseSaveIndex(i, 3, memory)
          memory(saveIndex.intValue) = params(0) + params(1)
          i += 4
        }

        // 2,2,3,4 => multiply param 2 * param 3 and save it under arr[4]
        case 2 => {
          val params = operator.parseParams(i, 2, memory)
          val saveIndex = operator.parseSaveIndex(i, 3, memory)
          memory(saveIndex) = params(0) * params(1)
          i += 4
        }

        // 3,50 => save INPUT VALUE or Program Argument under param 50
        case 3 => {
          var inputParam = BigDecimal(0)

          if (args.length > argumentIndex) {
            inputParam = args(argumentIndex)
            argumentIndex += 1
          } else {
            println("[3] Program is expecting input")
            inputParam = BigDecimal(StdIn.readLine())
          }

          val saveIndex = operator.parseSaveIndex(i, 1, memory)
          memory(saveIndex) = inputParam
          i += 2
        }

        // 4,x => output x
        case 4 => {
          val params = operator.parseParams(i, 1, memory)
          println(s"[4] output command: ${params(0)}")
          outputValue = Some(params(0))
          i += 2
        }

        // jump-if-true
        // 5,x,y => if x is non-zero set pointer to y
        case 5 => {
          val params = operator.parseParams(i, 2, memory)
          if (params(0) != 0) {
            i = params(1).toInt
          } else {
            i += 3
          }
        }

        // jump-if-false
        // 6,x,y => if x is zero set pointer to y
        case 6 => {
          val params = operator.parseParams(i, 2, memory)
          if (params(0) == 0) {
            i = params(1).toInt
          } else {
            i += 3
          }
        }

        // less than
        // 7,x,y,z => if x < y => store 1 in z, otherwise store 0 in z
        case 7 => {
          val params = operator.parseParams(i, 2, memory)
          val saveIndex = operator.parseSaveIndex(i, 3, memory)

          if (params(0) < params(1))
            memory(saveIndex) = 1
          else
            memory(saveIndex) = 0

          i += 4
        }

        // equals
        // 8,x,y,z => if x == y => store 1 in z, otherwise store 0 in z
        case 8 => {
          val params = operator.parseParams(i, 2, memory)
          val saveIndex = operator.parseSaveIndex(i, 3, memory)

          if (params(0) == params(1))
            memory(saveIndex) = 1
          else
            memory(saveIndex) = 0

          i += 4
        }

        // change relativeBase for mode 2 operator
        // 9,x -> add x to relativeBase
        case 9 => {
          val params = operator.parseParams(i, 1, memory)
          relativeBase += params(0).toInt
          i += 2
        }


        case 99 => {
          println("[99]")
          println(s"memory dump ${memory.take(20).mkString(",")}")
          programFinished = true
        }

        case x => {
          println(s"unknown code ${x} at index $i")
        }
      }
    }
    outputValue
  }
}

class Operator(operation: Int, relativeBase: Int) {

  private val opString = operation.toString()
  private val (inputModesReversed, operationCode) = opString.splitAt(opString.length - 2)
  private val code = operationCode.toInt
  private val modes = inputModesReversed.reverse.toArray

  def getCode = code


  def parseParams(fromIndex: Int, paramCount: Int, array: Array[BigDecimal]) = {
    Array.range(0, paramCount)
      .map(i => getValueForParam(i, fromIndex + i + 1, array))
  }

  def parseSaveIndex(index: Int, paramIndex: Int, array: Array[BigDecimal]): Int = {
    getModeFor(paramIndex - 1) match {
      case '0' => array(index + paramIndex).intValue
      case '1' => index + paramIndex
      case '2' => array(index + paramIndex).intValue + relativeBase
    }
  }

  private def getValueForParam(paramNo: Int, index: Int, array: Array[BigDecimal]) = {
    getModeFor(paramNo) match {
      case '0' => array(array(index).toInt)
      case '1' => array(index)
      case '2' => array(array(index).intValue + relativeBase)
    }
  }

  // mode 0 = memory location
  // mode 1 = direct value
  // mode 2 = memory location with base
  def getModeFor(index: Int) = {
    if (index < modes.length)
      modes(index)
    else
      '0'
  }
}
