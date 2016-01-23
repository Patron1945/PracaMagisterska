package main.scala.com.kucharz.patryk.mgr.algorithm

import com.kucharz.patryk.mgr.model.Itemset
import main.scala.com.kucharz.patryk.mgr.model.Trie

import scala.collection.mutable.ListBuffer


class TrieApriori(override val transactions : List[Itemset]) extends Apriori(transactions) {

  val trie : Trie = new Trie(transactions)

  def generateFrequentItemsets(minSupp: Double): List[Itemset] = {
    trie.head.getItemsetsBelow(null, new ListBuffer[Itemset]).filter(_.support > minSupp * trie.transactionsAmount)
  }


}