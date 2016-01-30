package main.scala.com.kucharz.patryk.mgr.actors

import com.kucharz.patryk.mgr.messages._
import com.kucharz.patryk.mgr.model.{Itemset}
import main.scala.com.kucharz.patryk.mgr.scenarios.MultiNodeScenario

import scala.collection.mutable.ListBuffer

class AllToAllFrequentItemsetsLevelSlave (override val id : Int, override val scenario : MultiNodeScenario, override val logEnabled : Boolean = false) extends Slave (id, scenario, logEnabled)  {

  val items = new ListBuffer[Itemset]
  var counter = 0

  override def initialize(): Unit = {
    scenario.manager ! HelloSlave
  }

  override def process(): Unit = {
    sendToTheOthers()
  }

  def sendToTheOthers(): Unit = {
    for(slave <- getSlaves) if(!slave.equals(this)) slave ! TransactionMessage(transactions)
  }

  override def receive() = {
    case tm : TransactionMessage =>
      log("AllToAllFrequentItemsetsLevelSlave:receive:TransactionMessage")
      items.appendAll(tm.itemsets)
      counter += 1
      if(counter == scenario.numberOfNodes) {
        master ! FrequentItemsetMessage(generateFrequentItemsets())
      }
    case StartMessage =>
      log("AllToAllFrequentItemsetsLevelSlave:receive:StartMessage")
      process()
    case ByeMessage =>
      log("AllToAllFrequentItemsetsLevelSlave:receive:ByeMessage")
      sender() ! SlaveByeMessage
      context.stop(self)
  }

  def generateFrequentItemsets() : List[Itemset] = {
    var frequentItemsets: List[Itemset] = getAprioriAlgorithm(items.toList, getAlgorithmType).generateFrequentItemsets(getMinsupp)
    val rulesPerSlave = Math.round(frequentItemsets.size / getSlaves.size)
    if(isLastSlave) {
      frequentItemsets.takeRight(frequentItemsets.size - (getSlaves.size - 1) * rulesPerSlave)
    } else {
      frequentItemsets.slice((id-1) * rulesPerSlave, rulesPerSlave * (id))
    }
  }

  def isLastSlave: Boolean = id == getSlaves.size

}
