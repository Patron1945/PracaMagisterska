package main.scala.com.kucharz.patryk.mgr.scenarios

import akka.actor.{Props, ActorRef}
import main.scala.com.kucharz.patryk.mgr.actors.{MultiNodeScenarioManager, SequenceScenarioManager}

class MultiNodeSequenceFrequentItemsetLevelScenario (override  val minimal_support : Double, override val minimal_confidence: Double, override val fileNameTemplate : String,override val numberOfNodes : Int, override val algorithmType: String)
    extends MultiNodeScenario(minimal_support, minimal_confidence, fileNameTemplate, numberOfNodes, algorithmType) {

  override val actorsType = ActorsType.AllInSequence_FrequentItemsets

  override def initializeManager() : ActorRef = {
    system.actorOf(Props(new SequenceScenarioManager(numberOfNodes, this)), name = "manager")
  }

}
