package main.scala.com.kucharz.patryk.mgr.actors

import com.kucharz.patryk.mgr.messages.{AssotiationRuleMessage, StartMessage, TransactionMessage}
import com.kucharz.patryk.mgr.model.Itemset
import main.scala.com.kucharz.patryk.mgr.scenarios.MultiNodeScenario

import scala.collection.mutable.ListBuffer

class SequenceTransactionLevelSlave (override val id : Int, override val scenario : MultiNodeScenario) extends Slave(id, scenario) {

  val items = new ListBuffer[Itemset]

  override def initialize(): Unit = {}

  override def receive() = {
    case im : TransactionMessage =>
      items.appendAll(im.itemsets)
      if(!isLastInSequence) {
        slaves(id) ! TransactionMessage(items.toList)
      } else {
        master ! TransactionMessage(items.toList)
      }
    case StartMessage =>
      println(id + " received StartMessage")
      items.appendAll(transactions)
      process()
  }

  override def process = {
    if(!isLastInSequence) {
      slaves(id) ! StartMessage
      if(isFirstInSequence) slaves(id) ! TransactionMessage(items.toList)
    }
  }

  def isLastInSequence = (id == scenario.numberOfNodes)
  def isFirstInSequence = (id == 1)

}
