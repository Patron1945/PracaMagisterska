package main.scala.com.kucharz.patryk.mgr.actors

import com.kucharz.patryk.mgr.actors.Master
import com.kucharz.patryk.mgr.messages._
import com.kucharz.patryk.mgr.model.{AssotiationRule, Itemset}
import main.scala.com.kucharz.patryk.mgr.algorithm.Apriori
import main.scala.com.kucharz.patryk.mgr.scenarios.{MultiNodeScenario}

import scala.collection.mutable.ListBuffer

class FrequentItemsetLevelMaster(override val scenario: MultiNodeScenario) extends Master(scenario) {

  private val itemsets: ListBuffer[Itemset] = new ListBuffer[Itemset]
  val frequentItemsets = new ListBuffer[Itemset]()
  scenario.manager ! HelloMaster

  override def receive: Receive = {
    case fim : FrequentItemsetMessage =>
      println("FrequentItemsetLevelMaster:receive:FrequentItemsetMessage")
      listOfActors.append(sender())
      fim.frequentItemset.foreach(appendToList(_))
      counter += 1;
      if (counter == scenario.numberOfNodes) {
        val confidentRules = Apriori.generateConfidentRules(itemsets.toList, scenario.minimal_confidence)
        scenario.manager ! ResultMessage(confidentRules)
      }
    case ByeMessage =>
      println("FrequentItemsetLevelMaster:receive:ByeMessage")
      sender() ! MasterByeMessage
      context.stop(self)
    case sfim : SequenceFrequentItemsetMessage =>
      println("TransactionLevelMaster:receive:SequenceTransactionMessage")
      frequentItemsets.appendAll(sfim.list)
      val confidentRules: List[AssotiationRule] = Apriori.generateConfidentRules(getFrequentItemsets(), scenario.minimal_confidence)
      scenario.manager ! ResultMessage(confidentRules)
  }

  def appendToList(itemset : Itemset) = {
    val maybeItemset: Option[Itemset] = itemsets.find(_.equals(itemset))
    if(maybeItemset == None) {
      itemsets.append(itemset)
    } else {
      maybeItemset.get.changeSupport(itemset.support)
    }
  }

  def getFrequentItemsets() : List[Itemset] = frequentItemsets.toList

}