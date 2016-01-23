package main.scala.com.kucharz.patryk.mgr.scenarios

import com.kucharz.patryk.mgr.algorithm.BruteApriori
import com.kucharz.patryk.mgr.model.AssotiationRule

class SingleNodeScenarioBruteForce (override  val minimal_support : Double, override val minimal_confidence: Double, override val fileNameTemplate : String)
  extends SingleNodeScenario(minimal_support, minimal_confidence, fileNameTemplate) {

  override def process(): List[AssotiationRule] = {
    timer.startEvent("PARSING")
    val bruteApriori = new BruteApriori(transactions)
    timer.stopEvent("PARSING")
    timer.startEvent("FREQUENT_ITEMSET_GENERATION")
    val frequentItemsets = bruteApriori.generateFrequentItemsets(minimal_support)
    timer.stopEvent("FREQUENT_ITEMSET_GENERATION")
    timer.startEvent("CONFIDENT_RULES_GENERATION")
    val rules = bruteApriori.generateConfidentRules(frequentItemsets, minimal_confidence)
    timer.stopEvent("CONFIDENT_RULES_GENERATION")
    rules
  }

}
