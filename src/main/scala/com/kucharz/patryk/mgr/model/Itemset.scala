package com.kucharz.patryk.mgr.model

import main.scala.com.kucharz.patryk.mgr.model.AprioriModelObject
import org.apache.commons.lang3.builder.HashCodeBuilder

class Itemset(val items : List[String]) extends AprioriModelObject {

  var support: Int = 0
  var rsupport : Double = 0.0
  val length : Int = items.size

  def this(items : List[String], support : Int) {
    this(items)
    this.support = support
  }

  def this(item : String, support : Int) = this(List(item), support)

  def this(item : String) = this(List[String](item))

  override def toString(): String = {
    "ITEMSET <[Items: " + items + " support: " + support + " rsupport: " + rsupport + "]>"
  }

  def equals(itemset : Itemset) : Boolean = (this.items.length == itemset.items.length) && this.items.forall(s => itemset.items.contains(s))

  override def equals(obj : Any) : Boolean = {
    if(obj.isInstanceOf[Itemset]) {
      equals(obj.asInstanceOf[Itemset])
    }
    false
  }

  override def hashCode() : Int = {
    var builder: HashCodeBuilder = new HashCodeBuilder()
    builder.append(items)
    builder.append(items.size)
    builder.hashCode()
  }

  def incrementSupport = changeSupport(1)

  def changeSupport(n : Int) = support += n

  def size() : Int = items.size

  def take(n : Int) = new Itemset(items.take(n))

  def generateAllPossibleKSubsets(k : Int) : List[Itemset] = items.combinations(k).map(new Itemset(_)).toList

  def extend(extension : List[String]) : Itemset = extend(extension, 0)

  def extend(extension : String) : Itemset = extend(extension, 0)

  def extend(extension : List[String], support : Int) = new Itemset((items ::: extension).sorted, support)

  def extend(extension : String, support : Int) = new Itemset((items ::: List(extension).sorted), support)

  override def countObjectSize(): Int = items.size
}
