package aoc2018

import aoc2018.util.readLines

fun main(args: Array<String>) {
    val polymer = readLines("input5.txt")[0]

    // solve first part
    val reactedPolymer = reactPolymer(polymer)
    // result: 11194
    println(reactedPolymer.length)

    // solve second part
    val bestReactedPolymer = findBestReactedPolymer(polymer)
    // result: 4178
    println(bestReactedPolymer.length)
}

fun findBestReactedPolymer(polymer: CharSequence): CharSequence {
    val units = polymer.toString().toLowerCase().toSet()
    return units.map { reactPolymer(polymer, excludedUnit = it) }.minBy { it.length }!!
}

fun reactPolymer(polymer: CharSequence, excludedUnit: Char? = null): CharSequence {
    val reactedPolymer = StringBuilder()
    for (c in polymer) {
        if (excludedUnit?.equals(c, ignoreCase = true) == true) {
            continue
        }
        if (reactedPolymer.isEmpty()) {
            reactedPolymer.append(c)
        } else {
            val last = reactedPolymer.last()
            if (c != last && c.equals(last, ignoreCase = true)) {
                reactedPolymer.setLength(reactedPolymer.length - 1)
            } else {
                reactedPolymer.append(c)
            }
        }
    }
    return reactedPolymer
}