package main.scala.com.kucharz.patryk.mgr.scenarios

object ActorsType extends Enumeration {

  type ActorsType = Value

  val AllToAll_Transactions,
      AllToMaster_Transactions,
      AllToMaster_FrequentItemsets,
      AllToMaster_AssotiationRules,
      AllInSequence_AssotiationRules,
      AllToAll_FrequentItemsets,
      AllInSequence_Transactions,
      AllInSequence_FrequentItemsets,
      None = Value
}
