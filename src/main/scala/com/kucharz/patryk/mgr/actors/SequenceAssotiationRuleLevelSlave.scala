package main.scala.com.kucharz.patryk.mgr.actors

import akka.actor.ActorRef
import com.kucharz.patryk.mgr.messages._
import com.kucharz.patryk.mgr.model.{AssotiationRule, Itemset}
import main.scala.com.kucharz.patryk.mgr.algorithm.Apriori
import main.scala.com.kucharz.patryk.mgr.scenarios.MultiNodeScenario

import scala.collection.mutable.ListBuffer

class SequenceAssotiationRuleLevelSlave (override val id : Int, override val scenario : MultiNodeScenario, override val logEnabled : Boolean = false) extends Slave(id, scenario, logEnabled) {

  val rules = new ListBuffer[AssotiationRule]
  var slaveList : List[ActorRef] = null
  var aprioriAlgorithm: Apriori = Apriori.getAprioriAlgorithm(transactions, scenario.algorithmType)
  var assotiationRules : List[AssotiationRule] = aprioriAlgorithm.generateConfidentRules(scenario.minimal_support, scenario.minimal_confidence)

  override def receive() = {
    case fim : AssotiationRuleMessage =>
      log("SequenceAssotiationRuleLevelSlave:receive:TransactionMessage")
      rules.appendAll(assotiationRules)
      rules.appendAll(fim.assotiationRules)
      process()
    case StartMessage =>
      log("SequenceAssotiationRuleLevelSlave:receive:StartMessage")
      rules.appendAll(assotiationRules)
      if(!isLastInSequence) {
        slaves(id) ! AssotiationRuleMessage(assotiationRules)
      }
    case ByeMessage =>
      sender() ! SlaveByeMessage
      context.stop(self)
  }

  override def process = {
    if(!isLastInSequence) {
      slaves(id) ! AssotiationRuleMessage(rules.toList)
    } else {
      master ! SequenceAssotiationRuleMessage(rules.toList)
    }
  }

  def isLastInSequence = id == slaves.size
  def isFirstInSequence = (id == 1)

}
