package main.scala.com.kucharz.patryk.mgr.actors

import com.kucharz.patryk.mgr.messages.TransactionMessage
import main.scala.com.kucharz.patryk.mgr.scenarios.MultiNodeScenario

class TransactionLevelSlave(override val id : Int, override val scenario : MultiNodeScenario) extends Slave(id, scenario) {

  override def process(): Unit = {
    getMaster ! TransactionMessage(transactions)
  }

  override def initialize(): Unit = {

  }
}