package main.scala.com.kucharz.patryk.mgr.actors

import com.kucharz.patryk.mgr.messages.StartMessage

class MultiNodeScenarioManager(override val numberOfNodes : Int) extends ScenarioManager(numberOfNodes) {

  override def startScenario(): Unit = {
//    println("MultiNodeScenarioManager:startScenario")
    slaveList.foreach(_ ! StartMessage)
  }

}
