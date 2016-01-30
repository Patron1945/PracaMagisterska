package main.scala.com.kucharz.patryk.mgr.actors

import com.kucharz.patryk.mgr.actors.Master
import com.kucharz.patryk.mgr.messages._
import com.kucharz.patryk.mgr.model.Itemset
import main.scala.com.kucharz.patryk.mgr.algorithm.Apriori
import main.scala.com.kucharz.patryk.mgr.scenarios.MultiNodeScenario

import scala.collection.mutable.ListBuffer

class TransactionLevelMaster(override val scenario: MultiNodeScenario)
  extends Master(scenario) {

  val transactions = new ListBuffer[Itemset]()
  scenario.manager ! HelloMaster

  override def receive: Receive = {
        case im : TransactionMessage =>
          println("TransactionLevelMaster:receive:TransactionMessage")
          listOfActors.append(sender())
          transactions.appendAll(im.itemsets)
          counter += 1;
          if (counter == scenario.numberOfNodes) {
            var aprioriAlgorithm : Apriori = Apriori.getAprioriAlgorithm(getTransactions(), scenario.algorithmType)
            val confidentRules = aprioriAlgorithm.generateConfidentRules(scenario.minimal_support, scenario.minimal_confidence)
            scenario.manager ! ResultMessage(confidentRules)
          }
        case ByeMessage =>
          println("TransactionLevelMaster:receive:MasterByeMessage")
          sender() ! MasterByeMessage
          context.stop(self)
        case stm : SequenceTransactionMessage =>
          println("TransactionLevelMaster:receive:SequenceTransactionMessage")
          transactions.appendAll(stm.itemsets)
          var aprioriAlgorithm : Apriori = Apriori.getAprioriAlgorithm(getTransactions(), scenario.algorithmType)
          val confidentRules = aprioriAlgorithm.generateConfidentRules(scenario.minimal_support, scenario.minimal_confidence)
          scenario.manager ! ResultMessage(confidentRules)
  }

  def getTransactions() : List[Itemset] = transactions.toList
}
