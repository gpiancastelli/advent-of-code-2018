import aoc2018.util.readLines

data class Node(
    val metadata: List<Int>,
    val children: List<Node>
) {
    // solve first part
    fun sum(): Int = metadata.sum() + children.sumBy { it.sum() }

    // solve second part
    fun value(): Int {
        if (children.isEmpty()) {
            return metadata.sum()
        }
        var value = 0
        metadata.forEach {
            if (it > 0 && it <= children.size) {
                value += children[it - 1].value()
            }
        }
        return value
    }
}

fun main() {
    val input = readLines("input8.txt").joinToString()
    val numbers = input.split(" ").map { it.toInt() }
    val rootNode = parseNode(numbers.toMutableList())
    // result: 45868
    println(rootNode.sum())
    // result: 19724
    println(rootNode.value())
}

fun parseNode(input: MutableList<Int>): Node {
    val childCount = input.removeAt(0)
    val metadataCount = input.removeAt(0)
    val children = mutableListOf<Node>()
    repeat(childCount) {
        val child = parseNode(input)
        children.add(child)
    }
    val metadata = input.removeSlice(0 until metadataCount)
    return Node(metadata, children)
}

fun <T> MutableList<T>.removeSlice(range: IntRange): List<T> {
    val slice = this.slice(range)
    for (i in range) {
        this.removeAt(0)
    }
    return slice
}