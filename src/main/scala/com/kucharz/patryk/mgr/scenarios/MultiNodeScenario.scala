package main.scala.com.kucharz.patryk.mgr.scenarios

import akka.actor.{Props, ActorRef, ActorSystem}
import com.kucharz.patryk.mgr.messages.StartMessage
import main.scala.com.kucharz.patryk.mgr.actors.{MasterFactory, SlaveFactory}


abstract class MultiNodeScenario (override  val minimal_support : Double, override val minimal_confidence: Double, override val fileNameTemplate : String, val numberOfNodes : Int, val algorithmType : String)
  extends Scenario(minimal_support, minimal_confidence, fileNameTemplate) {

  val actorsType : String = ""
  val system = ActorSystem("AprioriAlgorithm")
  var master : ActorRef = _
  var slaves : List[ActorRef] = _

  override def run() {
    println(scenarioMessage("START"))
    prepareEnvironment()
    system.awaitTermination()
    println(scenarioMessage("END"))
  }

  def prepareEnvironment(): Unit = {
    master = initializeMaster(system)
    slaves = initializeSlaves(system, master)
    slaves.foreach(_ ! StartMessage)
  }

  def initializeSlaves(system : ActorSystem, master : ActorRef) : List[ActorRef] = {
    (for (i <- 1 to this.numberOfNodes) yield (system.actorOf(Props(SlaveFactory.getSlave(actorsType, i, this)), name = "SLAVE" + i))).toList
  }

  def initializeMaster(system : ActorSystem) : ActorRef = {
    system.actorOf(Props(MasterFactory.getMaster(actorsType, this)), name = "master")
  }
}
