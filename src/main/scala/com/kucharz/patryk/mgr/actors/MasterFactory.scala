package main.scala.com.kucharz.patryk.mgr.actors

import com.kucharz.patryk.mgr.actors.Master
import main.scala.com.kucharz.patryk.mgr.scenarios.MultiNodeScenario

object MasterFactory {

  def getMaster(name: String, scenario : MultiNodeScenario) : Master = name match {
    case "ALLTOALLTRANSACTIONLEVEL" => new AssotiationRuleLevelMaster(scenario)
    case "TRANSACTIONLEVEL" => new TransactionLevelMaster(scenario)
    case "FREQUENTITEMSETLEVEL" => new FrequentItemsetLevelMaster(scenario)
    case "ASSOTIATIONRULELEVEL" => new AssotiationRuleLevelMaster(scenario)
    case "ALLTOALLFREQUENTITEMSETSLEVEL" => new FrequentItemsetLevelMaster(scenario)
    case "SEQUENCETRANSACTIONLEVEL" => new TransactionLevelMaster(scenario)
  }

}
