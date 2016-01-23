package main.scala.com.kucharz.patryk.mgr.model

class ConfusionMatrix(rules1found : Int, rules1notfound : Int, rules2found : Int, rules2notfound : Int) {

  def printMatrix : String = "LIST1: FOUND=" + rules1found + ", NOTFOUND=" +rules1notfound + ", LIST2: FOUND=" + rules2found + ", NOTFOUND=" + rules2notfound

  override def toString = {
    val sb = new StringBuilder;
    sb.append("CONFUSION MATRIX\n")
    sb.append("| " + rules1found + " | " + rules2notfound + " |\n")
    sb.append("| " + rules1notfound + " | " + rules2found + " |\n")
    sb.toString()
  }

}
