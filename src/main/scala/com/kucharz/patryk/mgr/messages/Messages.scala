package com.kucharz.patryk.mgr.messages

import com.kucharz.patryk.mgr.model.{AssotiationRule, Itemset}
import main.scala.com.kucharz.patryk.mgr.model.AprioriModelObject

abstract class AprioriMessage(val list : List[AprioriModelObject])
case class AssotiationRuleMessage(val assotiationRules: List[AssotiationRule]) extends AprioriMessage(assotiationRules)
case class FrequentItemsetMessage(val frequentItemset : List[Itemset]) extends AprioriMessage(frequentItemset)
case class ItemsetMessage(val itemsets : List[Itemset]) extends AprioriMessage(itemsets)
case class TransactionMessage(val itemsets : List[Itemset]) extends AprioriMessage(itemsets)
case class StartMessage()
case class ByeMessage()
