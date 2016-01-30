package main.scala.com.kucharz.patryk.mgr.actors

import main.scala.com.kucharz.patryk.mgr.scenarios.ActorsType.ActorsType
import main.scala.com.kucharz.patryk.mgr.scenarios.{ActorsType, MultiNodeScenario}

object SlaveFactory {

  def getSlave(actorsType: ActorsType, id : Int, scenario : MultiNodeScenario) : Slave = actorsType match {
    case ActorsType.AllToAll_Transactions           => new AllToAllTransactionLevelSlave(id, scenario)
    case ActorsType.AllToMaster_Transactions        => new TransactionLevelSlave(id, scenario)
    case ActorsType.AllToMaster_FrequentItemsets    => new FrequentItemsetLevelSlave(id, scenario)
    case ActorsType.AllToMaster_AssotiationRules    => new AssotiationRuleLevelSlave(id, scenario)
    case ActorsType.AllInSequence_AssotiationRules  => new SequenceAssotiationRuleLevelSlave(id, scenario)
    case ActorsType.AllToAll_FrequentItemsets       => new AllToAllFrequentItemsetsLevelSlave(id, scenario)
    case ActorsType.AllInSequence_Transactions      => new SequenceTransactionLevelSlave(id, scenario)
    case ActorsType.AllInSequence_FrequentItemsets  => new SequenceFrequentItemsetLevelSlave(id, scenario)
  }

}
