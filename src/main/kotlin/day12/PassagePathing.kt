package day12

typealias Nodes = MutableMap<String, Node>

enum class Sizes {
    SMALL, LARGE
}

data class Node(val name: String, val size: Sizes = Sizes.SMALL, val links: Nodes = mutableMapOf()) {
    override fun equals(other: Any?): Boolean = other is Node && other.name == name
    override fun toString(): String = name
}

fun List<String>.toGraph(): Node? {
    val nodes: Nodes = mutableMapOf()
    forEach {
        val stringNodes = it.split('-')
        val nodeA = nodes.getOrCreateNode(stringNodes[0])
        val nodeB = nodes.getOrCreateNode(stringNodes[1])

        nodeA.links[nodeB.name] = nodeB
        nodeB.links[nodeA.name] = nodeA
    }

    return nodes["start"]
}

fun Nodes.getOrCreateNode(name: String): Node {
    if (this.contains(name))
        return this[name]!!

    val node = createNode(name)
    this[name] = node

    return node
}

fun createNode(name: String): Node {
    val sizeA = if (name == name.uppercase()) Sizes.LARGE else Sizes.SMALL

    return  Node(name, sizeA)
}

fun Node.countPathsToEnd(): Int {
    var count = 0
    links.forEach { (key, link) ->
        val visitedNodes = mutableListOf("start")
        if (link.size == Sizes.SMALL) {
            visitedNodes.add(key)
        }

        count += link.countBranchPaths(visitedNodes)
    }

    return count
}

fun Node.countBranchPaths(visitedSmallNode: MutableList<String>, usedTime: Boolean = false): Int {
    var count = 0
    links.forEach { (key, value) ->
        if ("end" == key) {
            count++
        } else {
            val path = visitedSmallNode.toMutableList()
            if (value.size == Sizes.SMALL) {
                if (!path.contains(key)) {
                    path.add(key)
                    count += value.countBranchPaths(path, usedTime)
                } else if (!usedTime && "start" != key) {
                    count += value.countBranchPaths(path, true)
                }
            } else {
                count += value.countBranchPaths(path, usedTime)
            }
        }
    }

    return count
}
