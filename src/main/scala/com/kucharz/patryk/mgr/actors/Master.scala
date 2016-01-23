package com.kucharz.patryk.mgr.actors

import akka.actor._
import com.kucharz.patryk.mgr.messages.{FrequentItemsetMessage, ItemsetMessage}
import com.kucharz.patryk.mgr.model.Itemset
import main.scala.com.kucharz.patryk.mgr.scenarios.MultiNodeScenario

import scala.collection.mutable.ListBuffer

abstract class Master(val scenario : MultiNodeScenario) extends Actor {
  var counter = 0
  var listOfActors : ListBuffer[ActorRef] = new ListBuffer[ActorRef]


  var aggregatedItemsetList : scala.collection.mutable.Set[Itemset] = new scala.collection.mutable.HashSet[Itemset]

  def appendFrequentItemsetMessage(fim : FrequentItemsetMessage) = {
    if(aggregatedItemsetList.size == 0)
      aggregatedItemsetList = aggregatedItemsetList ++ fim.frequentItemset
    else {
      for(f <- fim.frequentItemset) {
        var itemset : Itemset = aggregatedItemsetList.find(f.equals(_)).getOrElse(null)
        if(itemset != null) {
          aggregatedItemsetList.remove(itemset)
          aggregatedItemsetList.add(new Itemset(itemset.items, f.support + itemset.support))
        } else {
          aggregatedItemsetList.add(f)
        }
      }
    }
  }

  def appendItemsetMessage(im : ItemsetMessage) = aggregatedItemsetList = aggregatedItemsetList ++ im.itemsets


}
