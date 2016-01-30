package main.scala.com.kucharz.patryk.mgr.actors

import com.kucharz.patryk.mgr.messages.{FrequentItemsetMessage}
import com.kucharz.patryk.mgr.model.Itemset
import main.scala.com.kucharz.patryk.mgr.algorithm.Apriori
import main.scala.com.kucharz.patryk.mgr.scenarios.MultiNodeScenario

class FrequentItemsetLevelSlave (override val id : Int,override val scenario : MultiNodeScenario, override val logEnabled : Boolean = false) extends Slave(id, scenario, logEnabled) {

  private var aprioriAlgorithm: Apriori = _
  private var frequentItemsets: List[Itemset] = _

  override def process(): Unit = {
    aprioriAlgorithm = Apriori.getAprioriAlgorithm(transactions, getAlgorithmType)
    frequentItemsets = aprioriAlgorithm.generateFrequentItemsets(getMinsupp)
    getMaster ! FrequentItemsetMessage(frequentItemsets)
  }

}
