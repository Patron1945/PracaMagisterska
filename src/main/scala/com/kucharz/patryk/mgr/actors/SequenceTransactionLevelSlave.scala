package main.scala.com.kucharz.patryk.mgr.actors

import akka.actor.ActorRef
import com.kucharz.patryk.mgr.messages._
import com.kucharz.patryk.mgr.model.Itemset
import main.scala.com.kucharz.patryk.mgr.scenarios.MultiNodeScenario

import scala.collection.mutable.ListBuffer

class SequenceTransactionLevelSlave (override val id : Int, override val scenario : MultiNodeScenario, override val logEnabled : Boolean = false) extends Slave(id, scenario, logEnabled) {

  val items = new ListBuffer[Itemset]
  var slaveList : List[ActorRef] = null

  override def receive() = {
    case im : TransactionMessage =>
      log("SequenceTransactionLevelSlave:receive:TransactionMessage")
      items.appendAll(transactions)
      items.appendAll(im.itemsets)
      process()
    case StartMessage =>
      log("SequenceTransactionLevelSlave:receive:StartMessage:Sender=" + sender())
      items.appendAll(transactions)
      if(!isLastInSequence) {
        slaves(id) ! TransactionMessage(items.toList)
      }
    case ByeMessage =>
      sender() ! SlaveByeMessage
      context.stop(self)
  }

  override def process = {
    if(!isLastInSequence) {
      slaves(id) ! TransactionMessage(items.toList)
    } else {
      master ! SequenceTransactionMessage(items.toList)
    }
  }

  def isLastInSequence = {
    id == slaves.size
  }

  def isFirstInSequence = (id == 1)

}
