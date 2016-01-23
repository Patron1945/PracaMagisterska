package main.scala.com.kucharz.patryk.mgr.actors

import com.kucharz.patryk.mgr.messages.FrequentItemsetMessage
import com.kucharz.patryk.mgr.model.Itemset
import main.scala.com.kucharz.patryk.mgr.algorithm.Apriori
import main.scala.com.kucharz.patryk.mgr.scenarios.MultiNodeScenario

class FrequentItemsetLevelSlave (override val id : Int,override val scenario : MultiNodeScenario)
extends Slave(id, scenario) {

  private var aprioriAlgorithm: Apriori = _
  private var frequentItemsets: List[Itemset] = _

  override def process(): Unit = getMaster ! FrequentItemsetMessage(frequentItemsets)

  override def initialize(): Unit = {
    aprioriAlgorithm = Apriori.getAprioriAlgorithm(transactions, getAlgorithmType)
    frequentItemsets = aprioriAlgorithm.generateFrequentItemsets(getMinsupp)
  }
}
