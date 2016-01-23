package main.scala.com.kucharz.patryk.mgr.scenarios

class MultiNodeScenarionFrequentItemsetLevel  (override  val minimal_support : Double, override val minimal_confidence: Double, override val fileNameTemplate : String,override val numberOfNodes : Int, override val algorithmType: String)
  extends MultiNodeScenario(minimal_support, minimal_confidence, fileNameTemplate, numberOfNodes, algorithmType) {

  override val actorsType = "FREQUENTITEMSETLEVEL"

}
