package main.scala.com.kucharz.patryk.mgr.utils

import com.kucharz.patryk.mgr.model.AssotiationRule
import main.scala.com.kucharz.patryk.mgr.model.ConfusionMatrix

import scala.collection.mutable.ListBuffer

class ResultComparator(val referenceData : List[AssotiationRule]) {

  val resultsToCompare = new ListBuffer[AssotiationRule]()

  def appendResults(arList : List[AssotiationRule]): Unit = arList.foreach(resultsToCompare.append(_))

  def countQuality(): ConfusionMatrix = {
    val commonAndDisjunctinveTrues: (Int, Int) = countCommonAndDisjunctiveSetsSize(referenceData, resultsToCompare.toList)
    val commonAndDisjunctiveFalses: (Int, Int) = countCommonAndDisjunctiveSetsSize(resultsToCompare.toList, referenceData)

    new ConfusionMatrix(commonAndDisjunctinveTrues._1, commonAndDisjunctinveTrues._2, commonAndDisjunctiveFalses._1, commonAndDisjunctiveFalses._2)
  }

  def countCommonAndDisjunctiveSetsSize(list1 : List[AssotiationRule], list2 : List[AssotiationRule]) : (Int, Int) = {
    var commonItemsNumber = 0
    for(ar <- list1) if(list2.find(ar.equals(_)).getOrElse(null) != null) commonItemsNumber += 1

    (commonItemsNumber, list1.size - commonItemsNumber)
  }
}
