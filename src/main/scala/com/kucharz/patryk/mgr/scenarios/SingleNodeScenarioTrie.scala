package main.scala.com.kucharz.patryk.mgr.scenarios

import com.kucharz.patryk.mgr.model.AssotiationRule
import main.scala.com.kucharz.patryk.mgr.algorithm.TrieApriori


class SingleNodeScenarioTrie (override  val minimal_support : Double, override val minimal_confidence: Double, override val fileNameTemplate : String)
  extends SingleNodeScenario(minimal_support, minimal_confidence, fileNameTemplate) {

  override def process(): List[AssotiationRule] = {
    timer.startEvent("BUILD_TREE")
    val trieApriori = new TrieApriori(transactions);
    timer.stopEvent("BUILD_TREE")
    timer.startEvent("FREQUENT_ITEMSET_GENERATION")
    val frequentItemsets = trieApriori.generateFrequentItemsets(minimal_support)
    timer.stopEvent("FREQUENT_ITEMSET_GENERATION")
    timer.startEvent("CONFIDENT_RULES_GENERATION")
    val rules = trieApriori.generateConfidentRules(frequentItemsets, minimal_confidence)
    timer.stopEvent("CONFIDENT_RULES_GENERATION")
    rules
  }

}

