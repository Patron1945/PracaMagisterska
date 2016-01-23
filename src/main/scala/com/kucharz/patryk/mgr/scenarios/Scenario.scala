package main.scala.com.kucharz.patryk.mgr.scenarios

import main.scala.com.kucharz.patryk.mgr.utils.Timer

abstract class Scenario(val minimal_support : Double, val minimal_confidence: Double, val fileNameTemplate : String) {

  val timer = new Timer

  def run()

  def getScenarioName() : String = this.getClass.getName


  def scenarioMessage(message : String) : String = {
    message + " : " + getScenarioName()
  }

}
