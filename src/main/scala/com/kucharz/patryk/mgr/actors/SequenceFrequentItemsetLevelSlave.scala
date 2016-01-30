package main.scala.com.kucharz.patryk.mgr.actors

import akka.actor.ActorRef
import com.kucharz.patryk.mgr.messages._
import com.kucharz.patryk.mgr.model.Itemset
import main.scala.com.kucharz.patryk.mgr.algorithm.Apriori
import main.scala.com.kucharz.patryk.mgr.scenarios.MultiNodeScenario

import scala.collection.mutable.ListBuffer

class SequenceFrequentItemsetLevelSlave (override val id : Int, override val scenario : MultiNodeScenario, override val logEnabled : Boolean = false) extends Slave(id, scenario, logEnabled) {

  val items = new ListBuffer[Itemset]
  var slaveList : List[ActorRef] = null
  var aprioriAlgorithm: Apriori = Apriori.getAprioriAlgorithm(transactions, scenario.algorithmType)
  var frequentItemsets:List[Itemset] = aprioriAlgorithm.generateFrequentItemsets(getMinsupp)

  override def initialize(): Unit = {
    scenario.manager ! HelloSlave
  }

  override def receive() = {
    case fim : FrequentItemsetMessage =>
      log("SequenceTransactionLevelSlave:receive:TransactionMessage")
      items.appendAll(frequentItemsets)
      fim.frequentItemset.foreach(appendToList(_))
      process()
    case StartMessage =>
      log("SequenceTransactionLevelSlave:receive:StartMessage")
      items.appendAll(transactions)
      if(!isLastInSequence) {
        slaves(id) ! FrequentItemsetMessage(frequentItemsets)
      }
    case ByeMessage =>
      sender() ! SlaveByeMessage
      context.stop(self)
  }

  override def process = {
    if(!isLastInSequence) {
      slaves(id) ! FrequentItemsetMessage(items.toList)
    } else {
      println("id:" + id + "items:" + items)
      master ! SequenceFrequentItemsetMessage(items.toList)
    }
  }

  def appendToList(itemset : Itemset) = {
    val maybeItemset: Option[Itemset] = items.find(_.equals(itemset))
    if(maybeItemset == None) {
      items.append(itemset)
    } else {
      maybeItemset.get.changeSupport(itemset.support)
    }
  }

  def isLastInSequence = id == slaves.size
  def isFirstInSequence = (id == 1)

}
