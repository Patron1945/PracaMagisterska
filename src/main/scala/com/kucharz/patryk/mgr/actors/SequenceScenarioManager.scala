package main.scala.com.kucharz.patryk.mgr.actors

import com.kucharz.patryk.mgr.messages.{StartMessage}
import main.scala.com.kucharz.patryk.mgr.scenarios.MultiNodeScenario

class SequenceScenarioManager (override val numberOfNodes : Int, val scenario : MultiNodeScenario) extends ScenarioManager(numberOfNodes) {

  override def startScenario(): Unit = {
//    println("SequenceScenarioManager:startScenario")
    scenario.slaves(0) ! StartMessage
  }
}
