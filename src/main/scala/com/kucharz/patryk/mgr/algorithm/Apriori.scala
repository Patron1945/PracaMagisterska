package main.scala.com.kucharz.patryk.mgr.algorithm

import com.kucharz.patryk.mgr.algorithm.BruteApriori
import com.kucharz.patryk.mgr.model.{AssotiationRule, Itemset}

import scala.collection.mutable.ListBuffer

abstract class Apriori(val transactions : List[Itemset]) {

  def generateFrequentItemsets(minSupp : Double) : List[Itemset]

  def generateConfidentRules(minsupp : Double, minconf : Double) : List[AssotiationRule] = Apriori.generateConfidentRules(generateFrequentItemsets(minsupp), minconf)

  def generateConfidentRules(frequetItemsets: List[Itemset], minconf: Double): List[AssotiationRule] = Apriori.generateConfidentRules(frequetItemsets, minconf)

}

object Apriori {

  def generateConfidentRules(frequetItemsets: List[Itemset], minconf: Double): List[AssotiationRule] = {

    def generateAssotiationRulesForItemset(itemset: Itemset, itemsetsList: List[Itemset], minconf: Double): List[AssotiationRule] = {
      var result = new ListBuffer[AssotiationRule]
      var rulesLevel: List[AssotiationRule] = List[AssotiationRule](new AssotiationRule(itemset.items, Nil))
      var i = 0;
      for (i <- 0 to itemset.items.size - 2) {
        rulesLevel = generateRulesLevel(rulesLevel)
        rulesLevel = cutOffRules(rulesLevel, itemset, itemsetsList, minconf)
        result.appendAll(rulesLevel)
      }
      result.toList
    }

    def cutOffRules(listOfRules: List[AssotiationRule], itemset: Itemset, itemsetsList: List[Itemset], minconf: Double): List[AssotiationRule] = {
      listOfRules.foreach(ar => ar.setParameters(itemset.support, itemsetsList.find(i => new Itemset(ar.leftSide).equals(i)).get.support))
      listOfRules.filter(ar => ar.getConfidence > minconf)
    }

    def generateRulesLevel(listOfRules: List[AssotiationRule]): List[AssotiationRule] = {
      val resultList = new ListBuffer[AssotiationRule]
      if (listOfRules.size == 1) {
        for (x <- listOfRules(0).leftSide) yield new AssotiationRule(listOfRules(0).leftSide.filter(_ != x), List(x))
      } else {
        var x: Int = 0
        for (x <- 0 to listOfRules.size - 2) {
          var y: Int = 0
          for (y <- x + 1 to listOfRules.size - 1) {
            val commonItems = listOfRules(x).leftSide.filter(z => listOfRules(y).leftSide.contains(z))
            val diffItems = listOfRules(x).leftSide.filter(!commonItems.contains(_)) ::: listOfRules(y).leftSide.filter(!commonItems.contains(_)) ::: listOfRules(y).rightSide.filter(!commonItems.contains(_)) ::: listOfRules(x).rightSide.filter(!commonItems.contains(_))
            val rule = new AssotiationRule(commonItems, diffItems.toSet.toList)
            if (resultList.filter(_.equals(rule)).size == 0) resultList.append(rule)
          }
        }
        resultList.toList.filter(x => x.leftSide.size > 0 && x.rightSide.size > 0)
      }
    }

    var result: List[AssotiationRule] = List.empty
    for (f <- frequetItemsets.filter(x => x.length > 1)) result = result ::: generateAssotiationRulesForItemset(f, frequetItemsets, minconf)
    result

  }

  def getAprioriAlgorithm(transaction : List[Itemset], name : String) : Apriori =  name match {
    case "TRIE" => new TrieApriori(transaction)
    case "BRUTE" => new BruteApriori(transaction)
  }

}
