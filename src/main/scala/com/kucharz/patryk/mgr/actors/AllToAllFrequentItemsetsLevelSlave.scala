package main.scala.com.kucharz.patryk.mgr.actors

import com.kucharz.patryk.mgr.messages.{FrequentItemsetMessage, StartMessage, AssotiationRuleMessage, TransactionMessage}
import com.kucharz.patryk.mgr.model.{AssotiationRule, Itemset}
import main.scala.com.kucharz.patryk.mgr.algorithm.Apriori
import main.scala.com.kucharz.patryk.mgr.scenarios.MultiNodeScenario

import scala.collection.mutable.ListBuffer

class AllToAllFrequentItemsetsLevelSlave (override val id : Int, override val scenario : MultiNodeScenario) extends Slave (id, scenario)  {

  val items = new ListBuffer[Itemset]
  var counter = 0

  override def initialize(): Unit = {

  }

  override def process(): Unit = {
    sendToTheOthers()
  }

  def sendToTheOthers(): Unit = {
    for(slave <- getSlaves) if(!slave.equals(this)) slave ! TransactionMessage(transactions)
  }

  override def receive() = {
    case tm : TransactionMessage =>
      items.appendAll(tm.itemsets)
      counter += 1
      if(counter == getSlaves.size) {
        master ! FrequentItemsetMessage(generateFrequentItemsets())
      }
    case StartMessage => process()
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
