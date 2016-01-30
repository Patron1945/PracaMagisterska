package main.scala.com.kucharz.patryk.mgr.scenarios

import akka.actor.{Props, ActorRef, ActorSystem}
import main.scala.com.kucharz.patryk.mgr.actors.{MultiNodeScenarioManager, MasterFactory, SlaveFactory}
import main.scala.com.kucharz.patryk.mgr.scenarios.ActorsType.ActorsType


abstract class MultiNodeScenario (override  val minimal_support : Double, override val minimal_confidence: Double, override val fileNameTemplate : String, val numberOfNodes : Int, val algorithmType : String)
  extends Scenario(minimal_support, minimal_confidence, fileNameTemplate) {

  val actorsType : ActorsType = ActorsType.None
  val system = ActorSystem("AprioriAlgorithm")
  var master : ActorRef = _
  var slaves : List[ActorRef] = _
  var manager : ActorRef = _

  override def run() {
    println(scenarioMessage("START"))
    prepareEnvironment()
    system.awaitTermination()
    println(scenarioMessage("END"))
  }

  def prepareEnvironment(): Unit = {
    manager = initializeManager()
    master = initializeMaster()
    slaves = initializeSlaves()
  }

  def initializeSlaves() : List[ActorRef] = {
    (for (i <- 1 to this.numberOfNodes) yield (system.actorOf(Props(SlaveFactory.getSlave(actorsType, i, this)), name = "SLAVE" + i))).toList
  }

  def initializeMaster() : ActorRef = {
    system.actorOf(Props(MasterFactory.getMaster(actorsType, this)), name = "master")
  }

  def initializeManager() : ActorRef = {
    system.actorOf(Props(new MultiNodeScenarioManager(numberOfNodes)), name = "manager")
  }
}
