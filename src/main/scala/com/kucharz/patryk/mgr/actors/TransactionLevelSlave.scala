package main.scala.com.kucharz.patryk.mgr.actors

import com.kucharz.patryk.mgr.messages.{HelloSlave, TransactionMessage}
import main.scala.com.kucharz.patryk.mgr.scenarios.MultiNodeScenario

class TransactionLevelSlave(override val id : Int, override val scenario : MultiNodeScenario, override val logEnabled : Boolean = false) extends Slave(id, scenario, logEnabled) {

  override def process(): Unit = {
    getMaster ! TransactionMessage(transactions)
  }
}