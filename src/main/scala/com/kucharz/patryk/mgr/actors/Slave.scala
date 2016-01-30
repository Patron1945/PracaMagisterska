package main.scala.com.kucharz.patryk.mgr.actors

import akka.actor.Actor
import com.kucharz.patryk.mgr.algorithm.BruteApriori
import com.kucharz.patryk.mgr.messages.{HelloSlave, SlaveByeMessage, StartMessage, ByeMessage}
import com.kucharz.patryk.mgr.model.Itemset
import com.kucharz.patryk.mgr.utils.{FileReader, Parser}
import main.scala.com.kucharz.patryk.mgr.algorithm.{Apriori, TrieApriori}
import main.scala.com.kucharz.patryk.mgr.scenarios.{MultiNodeScenario}

abstract class Slave(val id : Int, val scenario : MultiNodeScenario, val logEnabled : Boolean) extends Actor {

  val master = scenario.master
  val slaves = scenario.slaves

  val transactions = Parser.parse(FileReader.readFile(scenario.fileNameTemplate + id.toString + ".txt", "\n"), " ")
  initialize()

  override def receive: Receive = {
    case ByeMessage =>
      sender() ! SlaveByeMessage
      context.stop(self)
    case StartMessage => process()
  }

  def getAprioriAlgorithm(transaction : List[Itemset], name : String) : Apriori =  name match {
    case "TRIE" => new TrieApriori(transaction)
    case "BRUTE" => new BruteApriori(transaction)
  }

  def getMaster = scenario.master

  def getMinsupp = scenario.minimal_support

  def getMinconf = scenario.minimal_confidence

  def getAlgorithmType = scenario.algorithmType

  def getSlaves = scenario.slaves

  def initialize() : Unit = scenario.manager ! HelloSlave

  def process()

  def log(message : String) = if(logEnabled) println(message)

}
