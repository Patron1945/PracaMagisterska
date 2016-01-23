package main.scala.com.kucharz.patryk.mgr.actors

import com.kucharz.patryk.mgr.actors.Master
import com.kucharz.patryk.mgr.messages.{ByeMessage, AssotiationRuleMessage}
import com.kucharz.patryk.mgr.model.{Itemset, AssotiationRule}
import main.scala.com.kucharz.patryk.mgr.scenarios.MultiNodeScenario

import scala.collection.mutable.ListBuffer

class AssotiationRuleLevelMaster (override val scenario: MultiNodeScenario)
  extends Master(scenario) {

  private val rules: ListBuffer[AssotiationRule] = new ListBuffer[AssotiationRule]

  override def receive: Receive = {
    case arm : AssotiationRuleMessage =>
      listOfActors.append(sender())
      arm.assotiationRules.foreach(appendToList(_))
      counter += 1;
      if (counter == scenario.numberOfNodes) {
        println("RESULTS: " + rules.filter(_.getConfidence > scenario.minimal_confidence))
        listOfActors.foreach(_ ! ByeMessage)
        context.stop(self)
        context.system.shutdown()
      }
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
