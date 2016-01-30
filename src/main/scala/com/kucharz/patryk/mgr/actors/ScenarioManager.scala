package main.scala.com.kucharz.patryk.mgr.actors

import akka.actor.{ActorRef, Actor}
import com.kucharz.patryk.mgr.messages._
import com.kucharz.patryk.mgr.model.AssotiationRule

import scala.collection.mutable.ListBuffer

abstract class ScenarioManager(val numberOfNodes : Int) extends Actor {

  protected var slaveList = new ListBuffer[ActorRef]
  protected var master : ActorRef = null

  override def receive: Receive = {
    case HelloSlave =>
//      println("MultiNodeScenarioManager:receive:HelloSlave:Sender=" + sender())
      slaveList.append(sender())
      if(areAllActorsLoggedIn) startScenario()
    case HelloMaster =>
//      println("MultiNodeScenarioManager:receive:HelloMaster")
      master = sender()
      if(areAllActorsLoggedIn) startScenario()
    case rm : ResultMessage =>
//      println("MultiNodeScenarioManager:receive:ResultMessage")
      printResults(rm.assotiationRules)
      finishScenario()
    case MasterByeMessage =>
//      println("MultiNodeScenarioManager:receive:MasterByeMessage")
      master = null
      if(areAllActorsLoggedOut) closeScenario()
    case SlaveByeMessage =>
//      println("MultiNodeScenarioManager:receive:SlaveByeMessage")
      slaveList = slaveList.filter(_ != sender())
      if(areAllActorsLoggedOut) closeScenario()
  }

  protected def areAllActorsLoggedOut: Boolean = {
    master == null && slaveList.size == 0
  }

  protected def areAllActorsLoggedIn: Boolean = {
    slaveList.size == numberOfNodes && master != null
  }

  protected def startScenario()

  protected def finishScenario() = {
//    println("ScenarioManager:finishScenario")
    master ! ByeMessage
    slaveList.foreach(_ ! ByeMessage)
  }

  protected def closeScenario(): Unit = {
//    println("ScenarioManager:closeScenario")
    context.stop(self)
    context.system.shutdown()
  }

  protected def printResults(assotiarionRules : List[AssotiationRule]) = {
    println("RESULTS: ")
    println(assotiarionRules)
  }

}
