package main.scala.com.kucharz.patryk.mgr.model

import com.kucharz.patryk.mgr.model.Itemset

import scala.collection.mutable.ListBuffer


class Trie {

  def this(transactions : List[Itemset]) {
    this()
    this.buildTrie(transactions)
  }

  val head : TrieNode = new TrieNode("")

  def append(itemset : Itemset) = {

    def addItemset(items : List[String], trieNode: TrieNode) : Unit = items match {
      case x :: xs =>
        trieNode.noMoreLeaf
        var successor = trieNode.getSuccessorByItem(x)
        if(successor == null) {
          successor = new TrieNode(x)
          trieNode.addSuccesor(successor)
        }
        addItemset(xs, successor)
      case x => trieNode.incrementCount
    }

    val items = itemset.items
    (0 to items.size).foreach(items.combinations(_).foreach(addItemset(_, head)))
  }

  def getCount(itemset : Itemset) : Int = {

    def getTo(items: List[String], trieNode : TrieNode) : Int = items match {
      case x :: xs => getTo(xs, trieNode.getSuccessorByItem(x))
      case x => trieNode.getCountValue
      case null => 0
    }

    val items = itemset.items
    getTo(items, head)
  }

  def print() : Unit = head.print()

  def buildTrie(transactions : List[Itemset]) = transactions.foreach(this.append(_))

  def transactionsAmount = head.getCountValue

}

class TrieNode(val item : String) {
  var countValue : Int = 0
  var isLeaf : Boolean = true
  var successors : ListBuffer[TrieNode] = new ListBuffer[TrieNode]()

  def addSuccesor(trieNode: TrieNode) : Unit = successors.append(trieNode)

  def getCountValue = countValue

  def getSuccessorByItem(item : String) : TrieNode = successors.filter(_.item.equals(item)).toList match {
    case Nil => null
    case x => x.head
  }

  def incrementCount = countValue += 1

  def noMoreLeaf = isLeaf = false

  override def toString() : String = "TrieNode item: " + item + " countValue: " + countValue + " isLeaf: " + isLeaf

  def print() : Unit = {
    println(toString())
    if(successors.size > 0) successors.foreach(_.print)
  }

  def getItemsetsBelow(itemset : Itemset, result : ListBuffer[Itemset]) : List[Itemset] = {
    if (item == "") successors.foreach(_.getItemsetsBelow(null, result))
    else if(itemset == null ) {
      result.append(new Itemset(item, countValue))
      successors.foreach(_.getItemsetsBelow(new Itemset(item), result))
    } else {
      result.append(itemset.extend(item, countValue))
      if(!isLeaf) successors.foreach(_.getItemsetsBelow(itemset.extend(item), result))
    }

    result.toList
  }
}

