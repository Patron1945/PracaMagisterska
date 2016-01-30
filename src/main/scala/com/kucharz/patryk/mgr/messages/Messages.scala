package com.kucharz.patryk.mgr.messages

import com.kucharz.patryk.mgr.model.{AssotiationRule, Itemset}
import main.scala.com.kucharz.patryk.mgr.model.AprioriModelObject

abstract class AprioriMessage(val list : List[AprioriModelObject])
case class AssotiationRuleMessage(val assotiationRules: List[AssotiationRule]) extends AprioriMessage(assotiationRules)
case class FrequentItemsetMessage(val frequentItemset : List[Itemset]) extends AprioriMessage(frequentItemset)
case class ItemsetMessage(val itemsets : List[Itemset]) extends AprioriMessage(itemsets)
case class SequenceTransactionMessage(val itemsets : List[Itemset]) extends AprioriMessage(itemsets)
case class TransactionMessage(val itemsets : List[Itemset]) extends AprioriMessage(itemsets)
case class StartMessage()
case class ByeMessage()
case class HelloSlave()
case class HelloMaster()
case class ByeSlave()
case class ResultMessage(val assotiationRules : List[AssotiationRule])
case class MasterByeMessage()
case class SlaveByeMessage()
case class SequenceFrequentItemsetMessage(list: List[Itemset])
case class SequenceAssotiationRuleMessage(list: List[AssotiationRule])