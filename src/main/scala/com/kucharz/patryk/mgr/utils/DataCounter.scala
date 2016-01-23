package main.scala.com.kucharz.patryk.mgr.utils

import com.kucharz.patryk.mgr.messages.AprioriMessage
import main.scala.com.kucharz.patryk.mgr.model.AprioriModelObject
import scala.collection.mutable.ListBuffer

class DataCounter {

  val data = new ListBuffer[List[AprioriModelObject]]

  def appendMessage(message : AprioriMessage) : Unit = data.append(message.list)

  def countDataSize() : Int = {
    val listOfSizes = for(amolist <- data) yield amolist.foldLeft(0)(_ + _.countObjectSize())
    listOfSizes.foldLeft(0)(_ + _)
  }

}
