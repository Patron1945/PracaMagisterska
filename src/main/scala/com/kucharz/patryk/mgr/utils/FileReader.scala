package com.kucharz.patryk.mgr.utils

object FileReader {

  def readFile(fileName: String, splitter: String) : List[String] = {
    val source = scala.io.Source.fromFile(fileName)
    val lines = try source.mkString.split(splitter) finally source.close()
    lines.toList
  }

}
