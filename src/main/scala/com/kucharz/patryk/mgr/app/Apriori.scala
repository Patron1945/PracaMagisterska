package com.kucharz.patryk.mgr.app

import main.scala.com.kucharz.patryk.mgr.scenarios._

object Apriori extends App {

//  new SingleNodeScenarioBruteForce(0.3, 0.7, "test").run()
//  new SingleNodeScenarioTrie(0.3, 0.7, "test").run()
  new MultiNodeScenarioTransactionLevel(0.3, 0.7, "test", 5, "TRIE").run()
//  new MultiNodeScenarioTransactionLevel(0.3, 0.7, "test", 5, "BRUTE").run()
//  new MultiNodeScenarionFrequentItemsetLevel(0.3, 0.7, "test", 5, "TRIE").run()
//  new MultiNodeScenarionFrequentItemsetLevel(0.3, 0.7, "test", 5, "BRUTE").run()
//  new MultiNodeScenarioAssotiationRuleLevel(0.3, 0.7, "test", 5 , "TRIE").run()
//  new MultiNodeScenarioAssotiationRuleLevel(0.3, 0.7, "test", 5 , "BRUTE").run()
//  new MultiNodeAllToAllTransactionLevelScenario(0.3, 0.7, "test", 5, "TRIE").run()
//  new MultiNodeAllToAllTransactionLevelScenario(0.3, 0.7, "test", 5, "BRUTE").run()
//  new MultiNodeAllToAllFrequentItemsLevelScenario(0.3, 0.7, "test", 5, "TRIE").run()
//  new MultiNodeAllToAllFrequentItemsLevelScenario(0.3, 0.7, "test", 5, "BRUTE").run()
  new MultiNodeSequenceTransactionLevelScenario(0.3, 0.7, "test", 5, "TRIE").run()

}
