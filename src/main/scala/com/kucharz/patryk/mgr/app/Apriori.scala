package com.kucharz.patryk.mgr.app

import main.scala.com.kucharz.patryk.mgr.scenarios._

object Apriori extends App {

//  new SingleNodeScenarioBruteForce(0.3, 0.7, "test").run()
  new SingleNodeScenarioTrie(0.3, 0.7, "test").run()
//OK  new MultiNodeScenarioTransactionLevel(0.3, 0.7, "test", 5, "TRIE").run()
//OK  new MultiNodeScenarioTransactionLevel(0.3, 0.7, "test", 5, "BRUTE").run()
//OK  new MultiNodeScenarionFrequentItemsetLevel(0.3, 0.7, "test", 5, "TRIE").run()
//OK  new MultiNodeScenarionFrequentItemsetLevel(0.3, 0.7, "test", 5, "BRUTE").run()
//OK new MultiNodeScenarioAssotiationRuleLevel(0.3, 0.7, "test", 5 , "TRIE").run()
//OK  new MultiNodeScenarioAssotiationRuleLevel(0.3, 0.7, "test", 5 , "BRUTE").run()
//NOK  new MultiNodeAllToAllTransactionLevelScenario(0.3, 0.7, "test", 5, "TRIE").run()
//NOK  new MultiNodeAllToAllTransactionLevelScenario(0.3, 0.7, "test", 5, "BRUTE").run()
//OK  new MultiNodeAllToAllFrequentItemsLevelScenario(0.3, 0.7, "test", 5, "TRIE").run()
//OK  new MultiNodeAllToAllFrequentItemsLevelScenario(0.3, 0.7, "test", 5, "BRUTE").run()
//OK  new MultiNodeSequenceTransactionLevelScenario(0.3, 0.7, "test", 5, "TRIE").run()
//OK  new MultiNodeSequenceTransactionLevelScenario(0.3, 0.7, "test", 5, "BRUTE").run()
//OK  new MultiNodeSequenceFrequentItemsetLevelScenario(0.3, 0.7, "test", 5, "TRIE").run()
//OK  new MultiNodeSequenceFrequentItemsetLevelScenario(0.3, 0.7, "test", 5, "BRUTE").run()
//OK  new MultiNodeSequenceAssotiationRuleLevelScenario(0.3, 0.7, "test", 5, "TRIE").run()
//OK  new MultiNodeSequenceAssotiationRuleLevelScenario(0.3, 0.7, "test", 5, "BRUTE").run()

}
