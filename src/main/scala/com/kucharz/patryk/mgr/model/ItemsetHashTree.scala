package main.scala.com.kucharz.patryk.mgr.model


import com.kucharz.patryk.mgr.model.Itemset

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

class ItemsetHashTree(val transactions : List[Itemset], val mod : Int) {

  var start : TreeNode = new TreeNode("B", 0, mod)
  val treeMap = new mutable.HashMap[String, Int]
  fillTreeMap()

  def fillTreeMap() = {
    var result : List[String] = List.empty
    for(t <- transactions) result = result ::: t.items
    var counter = 0
    for(i <- result.toSet[String]) {
      treeMap.+=((i, counter))
      counter += 1
    }
  }

  def buildHashTree(): Unit = {

    def retrieveItems() : Set[String] = {
      var result : List[String] = List.empty
      for(t <- transactions) result = result ::: t.items
      result.toSet
    }

    def updateNodeWithItemset(itemset : Itemset, count : Int, node : TreeNode) : Unit = {
      println("###updateNodeWithItemset###: " + itemset + ", " + count)
      println("node itemsets: " + node.itemsets)
      if(count < itemset.size()) {
        println("FIRST PATH")
        val counter = count + 1
        val item = itemset.items(count)
        println("Item: " + item)
        println("HasSuccessors: " + node.hasSuccessor())
        if (!node.hasSuccessor()) node.createSuccessors()
        val successor = node.getSuccessor(treeMap.get(item).get)
        println("Successor: " + successor.itemsets)
        println("getItemset: " + itemset.take(counter))
        val successorItemset = successor.getItemset(itemset.take(counter), count)
        println("SuccessorItemset: " + successorItemset)
        if (successorItemset == null) {
          println("AddItemset: " + itemset.take(counter))
          val itemsetTmp = itemset.take(counter)
          itemsetTmp.incrementSupport
          successor.addItemset(itemsetTmp)
        } else {
          println("SuccessorItemset: before: " + successorItemset)
          successorItemset.incrementSupport
          println("SuccessorItemset: after: " + successorItemset)
        }
        println("node: " + successor.itemsets)
        updateNodeWithItemset(itemset, counter, successor)
      }

    }


    //budowanie start i pierwszego poziomu
    for(c <- 1 to mod) start.addSuccesor(new TreeNode("L", 1, mod))
    for(i <- retrieveItems()) {
      val count = treeMap.get(i).get % mod
      start.getSuccessor(count).addItemset(new Itemset(i))
    }

    //kolejne poziomy
    for(t <- transactions) {
      println("TRANSACTION: " + t)
      for(i <- 1 to t.size()) {
        for(y <- t.generateAllPossibleKSubsets(i)) {
          updateNodeWithItemset(y, 0, start)
        }
      }
    }

  }
//
//  def lookUpForSupportOfItemset(itemset : Itemset) : Int = {
//    for(i <- itemset.items) {
//      start
//    }
//  }

  override def toString() : String = start.toString()

  class TreeNode(var option : String, val level : Int, val mod : Int) {
    //option: leaf [L], branch [B], top [T]
    val nodes : ListBuffer[TreeNode] = new ListBuffer[TreeNode]
    val itemsets : ListBuffer[Itemset] = new ListBuffer[Itemset]

    def addSuccesor(successor : TreeNode) = nodes.append(successor)

    def addItemset(itemset: Itemset) = itemsets.append(itemset)

    def getSuccessor(pos : Int) : TreeNode = if(hasSuccessor()) nodes(pos) else null

    def hasSuccessor() : Boolean = nodes.size > 0

    def createSuccessors() : Unit = {
      option = "B"
      for(i <- 1 to mod) {
        nodes.append(new TreeNode("L", level + 1, mod))
      }
    }

    def getItemset(itemset : Itemset, level : Int) : Itemset = {
      if(this.itemsets.size == 0) null
      else if(this.level == itemset.items.size) itemsets.find(_.equals(itemset)).get
      else nodes(treeMap.get(itemset.items(level)).get % mod).getItemset(itemset, level + 1)
    }

    override def toString() : String = {
      "##LEVEL: " + level + " ##\nTREENODE ITEMSETS: " + itemsets + "\n NODES " + nodes + "" + "\n"
//      "##LEVEL: " + level + " TREENODE ITEMSETS: " + itemsets + ", NODES " + nodes + "##" + "\n"
    }
  }



}