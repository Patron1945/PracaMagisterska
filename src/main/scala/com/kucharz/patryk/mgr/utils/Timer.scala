package main.scala.com.kucharz.patryk.mgr.utils

import scala.collection.mutable

class Timer {

  var startTime : Long = 0
  var stopTime : Long = 0
  var timeEventMap : mutable.HashMap[String, TimeEvent] = new mutable.HashMap[String, TimeEvent]

  def start() = startTime = System.nanoTime()

  def stop() = stopTime = System.nanoTime()

  def reset() = { startTime = 0; stopTime = 0 }

  def startEvent(key: String) = timeEventMap.put(key, new TimeEvent(System.nanoTime(), 0, key))

  def stopEvent(key: String) = {
    val maybeEvent: TimeEvent = timeEventMap.get(key).get
    maybeEvent.updateStopTime(System.nanoTime())
    timeEventMap.update(key, maybeEvent)
  }

  def printTime() = {
    val valuesIterator : Iterator[TimeEvent] = timeEventMap.valuesIterator
    while(valuesIterator.hasNext) {
      val next: TimeEvent = valuesIterator.next()
      println(next.printEventInfo(startTime))
    }
    println("WHOLE SCENARIO LASTED: " + (stopTime - startTime) / 1000)
  }

  def stopAndPrintTime() = {
    stop()
    printTime()
  }

}

class TimeEvent(eventStartTime : Long, var eventStopTime : Long, key : String) {

  def printEventInfo(startTime : Long) = {
    val lastTime : Long = (eventStopTime - eventStartTime) / (1000)
    "EVENT " + key + " STARTED " + startTime + " LASTED " + lastTime
  }

  def updateStopTime(stopTime : Long) = this.eventStopTime = stopTime

}