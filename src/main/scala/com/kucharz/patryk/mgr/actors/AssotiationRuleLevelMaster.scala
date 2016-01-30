package main.scala.com.kucharz.patryk.mgr.actors

import com.kucharz.patryk.mgr.actors.Master
import com.kucharz.patryk.mgr.messages._
import com.kucharz.patryk.mgr.model.{Itemset, AssotiationRule}
import main.scala.com.kucharz.patryk.mgr.scenarios.MultiNodeScenario

import scala.collection.mutable.ListBuffer

class AssotiationRuleLevelMaster (override val scenario: MultiNodeScenario) extends Master(scenario) {

  private val rules: ListBuffer[AssotiationRule] = new ListBuffer[AssotiationRule]
  scenario.manager ! HelloMaster

  override def receive: Receive = {
    case arm : AssotiationRuleMessage =>
      println("AssotiationRuleLevelMaster:receive:AssotiationRuleMessage")
      listOfActors.append(sender())
      println("RECEICED: " + arm)
      arm.assotiationRules.foreach(appendToList(_))
      counter += 1;
      if (counter == scenario.numberOfNodes) {
        val results = rules.filter(_.getConfidence > scenario.minimal_confidence)
        scenario.manager ! ResultMessage(results.toList)
      }
    case ByeMessage =>
      println("AssotiationRuleLevelMaster:receive:ByeMessage")
      sender() ! MasterByeMessage
      context.stop(self)
    case sarm : SequenceAssotiationRuleMessage =>
      println("AssotiationRuleLevelMaster:receive:SequenceAssotiationRuleMessage")
      sarm.list.foreach(appendToList(_))
      val results = rules.filter(_.getConfidence > scenario.minimal_confidence)
      scenario.manager ! ResultMessage(results.toList)
  }

  def appendToList(rule : AssotiationRule) = {
    var maybeAssotiationRule: Option[AssotiationRule] = rules.find(_.equals(rule))
    if(maybeAssotiationRule == None) {
      rules.append(rule)
    } else {
      val maybeAR = maybeAssotiationRule.get
      val newSupportAB = rule.getSupportAB + maybeAR.getSupportAB
      val newSupportA = rule.getSupportA + maybeAR.getSupportA
      maybeAR.setParameters(newSupportAB, newSupportA)
    }
  }

}
