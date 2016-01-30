package main.scala.com.kucharz.patryk.mgr.actors

import com.kucharz.patryk.mgr.messages._
import com.kucharz.patryk.mgr.model.{AssotiationRule, Itemset}
import main.scala.com.kucharz.patryk.mgr.algorithm.Apriori
import main.scala.com.kucharz.patryk.mgr.scenarios.MultiNodeScenario

import scala.collection.mutable.ListBuffer

class AllToAllTransactionLevelSlave (override val id : Int, override val scenario : MultiNodeScenario, override val logEnabled : Boolean = true) extends Slave (id, scenario, logEnabled)  {

  val items = new ListBuffer[Itemset]
  var counter = 0

  override def initialize(): Unit = {
    scenario.manager ! HelloSlave
  }

  override def process(): Unit = {
    items.appendAll(transactions)
    sendToTheOthers()
  }

  override def receive() = {
    case im : TransactionMessage =>
      log("AllToAllTransactionLevelSlave:receive:TransactionMessage")
      items.appendAll(im.itemsets)
      counter += 1
      if(counter == getSlaves.size) {
        master ! AssotiationRuleMessage(generateAssotiationRules())
      }
    case StartMessage =>
      log("AllToAllTransactionLevelSlave:receive:StartMessage")
      process()
    case ByeMessage =>
      log("AllToAllTransactionLevelSlave:receive:ByeMessage")
      sender() ! SlaveByeMessage
      context.stop(self)
  }
  //DO POPRAWY, MAMY LICZYC REGULY DLA PODZIELONEGO ZBIORU
  def generateAssotiationRules() : List[AssotiationRule] = {
    var aprioriAlgorithm: Apriori = getAprioriAlgorithm(items.toList, getAlgorithmType)
    var confidentRules: List[AssotiationRule] = aprioriAlgorithm.generateConfidentRules(getMinsupp, getMinconf)
    val rulesPerSlave = Math.round(confidentRules.size / getSlaves.size)
    if(isLastSlave) {
      confidentRules.takeRight(confidentRules.size - (getSlaves.size - 1) * rulesPerSlave)
    } else {
      confidentRules.slice((id-1) * rulesPerSlave, rulesPerSlave * (id))
    }
  }

  def isLastSlave: Boolean = id == getSlaves.size

  def sendToTheOthers(): Unit = {
    for(slave <- getSlaves) if(!slave.equals(this)) slave ! TransactionMessage(transactions)
  }

}
