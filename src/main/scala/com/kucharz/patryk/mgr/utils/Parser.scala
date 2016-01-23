package com.kucharz.patryk.mgr.utils

import com.kucharz.patryk.mgr.model.Itemset

import scala.collection.mutable.ListBuffer

object Parser {

  def parse(transactions : List[String], splitter : String) : List[Itemset] = {
    var resultList : ListBuffer[Itemset] = new ListBuffer[Itemset]
    transactions.foreach(t =>  resultList.append(new Itemset(t.split(splitter).toList)))
    resultList.toList
  }

}
