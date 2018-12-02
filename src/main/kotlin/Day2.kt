package aoc2018.day2

import java.io.File

fun main(args: Array<String>) {
    val inputFile = File(ClassLoader.getSystemResource("input2.txt").file)
    val lines = inputFile.readLines()

    // solve first part
    val checksum = findChecksum(lines)
    // result: 4920
    println(checksum)

    // solve second part
    val letters = findCommonLetters(lines)
    // result: fonbwmjquwtapeyzikghtvdxl
    println(letters)
}

fun findChecksum(lines: List<String>): Int {
    val idList = mutableListOf<Set<Int>>()

    for (line in lines) {
        val letterMap = mutableMapOf<Char, Int>()
        for (letter in line) {
            letterMap.merge(letter, 1, Int::plus)
        }
        idList.add(letterMap.values.toSet())
    }

    return idList.count { 2 in it } * idList.count { 3 in it }
}

fun findCommonLetters(lines: List<String>): String? {
    for (i in 0 until lines.size) {
        for (j in i + 1 until lines.size) {
            val id1 = lines[i]
            val id2 = lines[j]
            if (id1.length != id2.length) {
                continue
            }
            var diffCount = 0
            var diffIndex = 0
            for (z in 0 until id1.length) {
                if (id1[z] != id2[z]) {
                    if (diffCount > 1) {
                        break
                    }
                    diffCount++
                    diffIndex = z
                }
            }
            if (diffCount == 1) {
                return id1.removeRange(diffIndex, diffIndex + 1)
            }
        }
    }
    return null
}