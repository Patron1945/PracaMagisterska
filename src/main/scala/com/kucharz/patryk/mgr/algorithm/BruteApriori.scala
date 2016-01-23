package com.kucharz.patryk.mgr.algorithm

import com.kucharz.patryk.mgr.model.Itemset
import main.scala.com.kucharz.patryk.mgr.algorithm.Apriori

class BruteApriori(override val transactions : List[Itemset]) extends Apriori(transactions){

  def generateFrequentItemsets(minSupp : Double) : List[Itemset] = {
    def retrieveItems() : Set[String] = {
      var result : List[String] = List.empty
      for(t <- transactions) result = result ::: t.items
      result.toSet
    }

    def cutOffCandidates(minsupp : Double, candidates : List[Itemset]) : List[Itemset] = countSupport(candidates).filter(_.rsupport > minsupp)


    def countSupport(itemsets : List[Itemset]): List[Itemset] = {
      for(itemset <- itemsets) {
        itemset.support = countSupportForSingleItemset(itemset)
        itemset.rsupport = itemset.support.toDouble / transactions.size
      }
      itemsets
    }

    def countSupportForSingleItemset(itemset: Itemset) : Int = {
      var total : Int = 0
      for(t <- transactions) if (itemset.items.forall(s => t.items.contains(s))) total += 1
      total
    }

    def getLevelsNumber : Int = {
      def max(xs: List[Itemset]): Int = xs match {
        case Nil => 0
        case List(x : Itemset) => x.length
        case x :: y :: rest => max( (if (x.length > y.length) x else y) :: rest )
      }
      max(transactions)
    }

    def generateCandidates(level: Int, frequentItemsets: List[Itemset]) : List[Itemset] = {
      var resultList = new collection.mutable.ListBuffer[Itemset]
      var i : Int = 0;
      for(i <- 0 to (frequentItemsets.size - 2)) {
        var j : Int = 0;
        for(j <- i + 1 to (frequentItemsets.size - 1)) {
          if (frequentItemsets(i).items.take(level).zip(frequentItemsets(j).items.take(level)).forall(x => x._1 == x._2)) {
            val tmpList = frequentItemsets(i).items ::: frequentItemsets(j).items.takeRight(1)
            resultList.append(new Itemset(tmpList.sortWith(_ < _)))
          }
        }
      }
      resultList.toList
    }

    var result : List[Itemset] = List.empty
    var items : List[Itemset] = for(i <- retrieveItems.toList) yield new Itemset(List(i))
    var frequentItemsetsLevel : List[Itemset] = null
    for(i <- 0 to getLevelsNumber - 1) {
      frequentItemsetsLevel = cutOffCandidates(minSupp, items)
      result = result ::: frequentItemsetsLevel
      items = generateCandidates(i, frequentItemsetsLevel)
    }
    result.filter(x => x.rsupport > minSupp)
  }

  override def toString() = "BruteApriori: " + transactions

}
