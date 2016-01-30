package main.scala.com.kucharz.patryk.mgr.actors

import com.kucharz.patryk.mgr.actors.Master
import main.scala.com.kucharz.patryk.mgr.scenarios.ActorsType.ActorsType
import main.scala.com.kucharz.patryk.mgr.scenarios.{ActorsType, MultiNodeScenario}

object MasterFactory {

  def getMaster(actorsType: ActorsType, scenario : MultiNodeScenario) : Master = actorsType match {
    case ActorsType.AllToAll_Transactions           => new AssotiationRuleLevelMaster(scenario)
    case ActorsType.AllToMaster_Transactions        => new TransactionLevelMaster(scenario)
    case ActorsType.AllToMaster_FrequentItemsets    => new FrequentItemsetLevelMaster(scenario)
    case ActorsType.AllToAll_FrequentItemsets       => new FrequentItemsetLevelMaster(scenario)
    case ActorsType.AllToMaster_AssotiationRules    => new AssotiationRuleLevelMaster(scenario)
    case ActorsType.AllInSequence_AssotiationRules  => new AssotiationRuleLevelMaster(scenario)
    case ActorsType.AllInSequence_FrequentItemsets  => new FrequentItemsetLevelMaster(scenario)
    case ActorsType.AllInSequence_Transactions      => new TransactionLevelMaster(scenario)
  }

}
