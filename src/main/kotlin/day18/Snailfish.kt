package day18

import kotlin.math.ceil

data class Node (
    var value: Int? = null,
    var left: Node? = null,
    var right: Node? = null,
) {
    var parent: Node? = null

    override fun toString(): String {
        return if (null != value) {
             value.toString()
        } else {
            "[" + left.toString() + "," + right.toString() + "]"
        }
    }
}

fun String.toNode(): Node {
    val stack = mutableListOf<Any>()
    var lastPair = mutableListOf<Any>()
    forEach {
        if ('[' == it) {
            val newPair = mutableListOf<Any>()
            if (stack.isNotEmpty()) {
                (stack.last() as MutableList<Any>).add(newPair)
            }
            stack.add(newPair)
        } else if (']' == it) {
            lastPair = stack.removeLast() as MutableList<Any>
        } else if (',' != it) {
            (stack.last() as MutableList<Any>).add(it.toString().toInt())
        }
    }

    return lastPair.toNode()
}

fun Node.reduce(): Node {
    var reduce = true
    while (reduce) {
        reduce = reduceAction()
    }

    return this
}

fun Node.explode() {
    parent!!.explodeUpLeft(this, left!!.value!!)
    parent!!.explodeUpRight(this, right!!.value!!)
    value = 0
    left = null
    right = null
}

fun Node.split() {
    val leftValue = value!! / 2
    val rightValue = ceil(value!!.toFloat() / 2).toInt()

    value = null
    left = Node(leftValue)
    left?.parent = this
    right = Node(rightValue)
    right?.parent = this
}

fun Node.getFirstNodeToExplode(): Node? {
    return goFourLevelDeep(0)?.parent
}

fun Node.getFirstNodeToSplit(): Node? {
    return if (null != value && value!! > 9) this
    else left?.getFirstNodeToSplit() ?: right?.getFirstNodeToSplit()
}

fun Node.magnitude(): Int {
    return value ?: ((3 * (left?.magnitude() ?: 0)) + (2 * (right?.magnitude() ?: 0)))
}

private fun Node.reduceAction(): Boolean {
    val nodeToExplode = getFirstNodeToExplode()?.explode() ?: getFirstNodeToSplit()?.split()

    return nodeToExplode != null
}

private fun Node.explodeUpLeft(childNode: Node, explodedValue: Int) {
    if (null != left && left !== childNode) {
        left!!.explodeDownRight(explodedValue)
    } else {
        parent?.explodeUpLeft(this, explodedValue)
    }
}

private fun Node.explodeDownLeft(explodedValue: Int) {
    if (null != value) {
        value = value!!.plus(explodedValue)
    } else {
        left!!.explodeDownLeft(explodedValue)
    }
}

private fun Node.explodeUpRight(childNode: Node, explodedValue: Int) {
    if (null != right && right !== childNode) {
        right!!.explodeDownLeft(explodedValue)
    } else {
        parent?.explodeUpRight(this, explodedValue)
    }
}

private fun Node.explodeDownRight(explodedValue: Int) {
    if (null != value) {
        value = value!!.plus(explodedValue)
    } else {
        right!!.explodeDownRight(explodedValue)
    }
}

private fun Node.goFourLevelDeep(currentLevel: Int): Node? {
    val nextLevel = currentLevel + 1
    return if (currentLevel == 5 && siblingNotAPair()) this
        else left?.goFourLevelDeep(nextLevel) ?: right?.goFourLevelDeep(nextLevel)

}

private fun Node.siblingNotAPair(): Boolean {
    return null != parent!!.left!!.value && null != parent!!.right!!.value
}

private fun MutableList<Any>.toNode(parent: Node? = null): Node {
    val node = Node()
    node.parent = parent

    val leftNode = first().toNode(node)
    val rightNode = last().toNode(node)

    node.left = leftNode
    node.right = rightNode

    return node
}

private fun Int.toNode(parent: Node): Node {
    val node = Node(this)
    node.parent = parent

    return node
}

private fun Any.toNode(parent: Node?): Node {
    return when (this) {
        is Int -> this.toNode(parent!!)
        else -> (this as MutableList<Any>).toNode(parent)
    }
}