package main.scala.com.kucharz.patryk.mgr.scenarios

import com.kucharz.patryk.mgr.messages.StartMessage

class MultiNodeSequenceTransactionLevelScenario (override  val minimal_support : Double, override val minimal_confidence: Double, override val fileNameTemplate : String,override val numberOfNodes : Int, override val algorithmType: String)
  extends MultiNodeScenario(minimal_support, minimal_confidence, fileNameTemplate, numberOfNodes, algorithmType) {

  override val actorsType = "SEQUENCETRANSACTIONLEVEL"

  override def prepareEnvironment(): Unit = {
    master = initializeMaster(system)
    slaves = initializeSlaves(system, master)
    slaves.head ! StartMessage
  }

}
