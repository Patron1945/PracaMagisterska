package main.scala.com.kucharz.patryk.mgr.actors

import com.kucharz.patryk.mgr.actors.Master
import com.kucharz.patryk.mgr.messages.{ByeMessage, FrequentItemsetMessage}
import com.kucharz.patryk.mgr.model.Itemset
import main.scala.com.kucharz.patryk.mgr.algorithm.Apriori
import main.scala.com.kucharz.patryk.mgr.scenarios.{MultiNodeScenario}

import scala.collection.mutable.ListBuffer

class FrequentItemsetLevelMaster(override val scenario: MultiNodeScenario)
  extends Master(scenario) {

  private val itemsets: ListBuffer[Itemset] = new ListBuffer[Itemset]

  override def receive: Receive = {
    case fim : FrequentItemsetMessage =>
      listOfActors.append(sender())
      fim.frequentItemset.foreach(appendToList(_))
      counter += 1;
      if (counter == scenario.numberOfNodes) {
        val confidentRules = Apriori.generateConfidentRules(itemsets.toList, scenario.minimal_confidence)
        println("RESULTS: " + confidentRules)
        listOfActors.foreach(_ ! ByeMessage)
        context.stop(self)
        context.system.shutdown()
      }
  }

  def appendToList(itemset : Itemset) = {
    var maybeItemset: Option[Itemset] = itemsets.find(_.equals(itemset))
    if(maybeItemset == None) {
      itemsets.append(itemset)
    } else {
      maybeItemset.get.changeSupport(itemset.support)
    }
  }

}