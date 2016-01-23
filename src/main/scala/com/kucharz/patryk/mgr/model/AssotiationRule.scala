package com.kucharz.patryk.mgr.model

import main.scala.com.kucharz.patryk.mgr.model.AprioriModelObject

class AssotiationRule(val leftSide: List[String], val rightSide : List[String]) extends AprioriModelObject {

  //confidence = supportAB/supportA
  private var confidence : Double = 0.0
  private var supportAB : Int = 0
  private var supportA : Int = 0

  override def toString() : String = {
    leftSide.mkString(",") + " -> " + rightSide.mkString(",") + " | conf = " + confidence + " | suppAB = " + supportAB + " | suppA = " + supportA
  }

  def equals(assotiationRule: AssotiationRule) : Boolean = {
    this.leftSide.forall(assotiationRule.leftSide.contains(_)) && this.leftSide.size == assotiationRule.leftSide.size &&
      this.rightSide.forall(assotiationRule.rightSide.contains(_)) && this.rightSide.size == assotiationRule.rightSide.size
  }

  def setParameters(supportAB : Int, supportA : Int) = {
    this.supportAB = supportAB
    this.supportA = supportA
    this.confidence = supportAB.toDouble/supportA.toDouble
  }

  def getSupportAB = this.supportAB

  def getSupportA = this.supportA

  def getConfidence = this.confidence

  override def countObjectSize: Int = leftSide.size + rightSide.size
}
