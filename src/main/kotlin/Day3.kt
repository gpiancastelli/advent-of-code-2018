package aoc2018.day3

import java.io.File

fun main(args: Array<String>) {
    val inputFile = File(ClassLoader.getSystemResource("input3.txt").file)
    val lines = inputFile.readLines()

    val claimList = createClaimList(lines)
    val overlappingInchesMap = createOverlappingInchesMap(claimList)

    // solve first part
    val overlappingInches = overlappingInchesMap.size
    // result: 107043
    println(overlappingInches)

    // solve second part
    val nonOverlappingClaim = findNonOverlappingClaim(claimList, overlappingInchesMap)
    // result: 346
    println(nonOverlappingClaim?.id)
}

fun createClaimList(lines: List<String>): List<Claim?> {
    val claimPattern = """#(\d+) @ (\d+),(\d+): (\d+)x(\d+)""".toRegex()
    return lines.map { line ->
        claimPattern.matchEntire(line)?.let {
            val (id, top, left, width, height) = it.destructured
            Claim(id.toInt(), top.toInt(), left.toInt(), width.toInt(), height.toInt())
        }
    }
}

fun createOverlappingInchesMap(claims: List<Claim?>): Map<Pair<Int, Int>, Int> {
    val inchesMap = mutableMapOf<Pair<Int, Int>, Int>()
    for (claim in claims) {
        claim?.inches?.forEach { inchesMap.merge(it, 1, Int::plus) }
    }
    return inchesMap.filterValues { it > 1 }
}

fun findNonOverlappingClaim(claimList: List<Claim?>, overlappingInchesMap: Map<Pair<Int, Int>, Int>): Claim? =
    claimList.asSequence().first { claim ->
        claim?.inches?.none { it in overlappingInchesMap } ?: false
    }

class Claim(val id: Int, top: Int, left: Int, width: Int, height: Int) {
    val inches = mutableListOf<Pair<Int, Int>>()

    init {
        for (i in top..(top + width - 1)) {
            for (j in left..(left + height - 1)) {
                inches.add(i to j)
            }
        }
    }
}