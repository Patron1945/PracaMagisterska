package main.scala.com.kucharz.patryk.mgr.actors

import main.scala.com.kucharz.patryk.mgr.scenarios.MultiNodeScenario

object SlaveFactory {

  def getSlave(name: String, id : Int, scenario : MultiNodeScenario) : Slave = name match {
    case "ALLTOALLTRANSACTIONLEVEL" => new AllToAllTransactionLevelSlave(id, scenario)
    case "TRANSACTIONLEVEL" => new TransactionLevelSlave(id, scenario)
    case "FREQUENTITEMSETLEVEL" => new FrequentItemsetLevelSlave(id, scenario)
    case "ASSOTIATIONRULELEVEL" => new AssotiationRuleLevelSlave(id, scenario)
    case "ALLTOALLFREQUENTITEMSETSLEVEL" => new AllToAllFrequentItemsetsLevelSlave(id, scenario)
    case "SEQUENCETRANSACTIONLEVEL" => new SequenceTransactionLevelSlave(id, scenario)
  }

}
