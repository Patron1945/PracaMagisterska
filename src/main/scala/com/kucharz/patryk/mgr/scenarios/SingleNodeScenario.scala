package main.scala.com.kucharz.patryk.mgr.scenarios

import com.kucharz.patryk.mgr.model.AssotiationRule
import com.kucharz.patryk.mgr.utils.{FileReader, Parser}

abstract class SingleNodeScenario(override val minimal_support : Double, override val minimal_confidence : Double, override val fileNameTemplate : String)
  extends Scenario(minimal_support, minimal_confidence, fileNameTemplate) {

  val rawTransactions = FileReader.readFile(fileNameTemplate + ".txt", "\n")
  val transactions = Parser.parse(rawTransactions, " ")

  override def run() = {
    println(scenarioMessage("START"))
    timer.start()
    println("RESULTS: " + process())
    timer.stopAndPrintTime()
  }

  def process() : List[AssotiationRule]


}
