package aoc2018

import aoc2018.util.readLines

fun main(args: Array<String>) {
    val lines = readLines("input1.txt")

    val frequencyChanges = lines.map { it.toInt() }

    // solve first part
    val frequency = calculateFrequency(frequencyChanges)
    // result: 508
    println(frequency)

    // solve second part
    val repeatedFrequency = findFirstRepeatedFrequency(frequencyChanges)
    // result: 549
    println(repeatedFrequency)
}

fun calculateFrequency(frequencyChanges: List<Int>): Int = frequencyChanges.sum()

fun findFirstRepeatedFrequency(frequencyChanges: List<Int>): Int {
    val frequencies = mutableSetOf<Int>()
    var currentFrequency = 0
    for (change in Ring(frequencyChanges)) {
        currentFrequency += change
        if (currentFrequency in frequencies) {
            break
        }
        frequencies.add(currentFrequency)
    }
    return currentFrequency
}

class Ring<T>(private val list: List<T>): Iterable<T> {
    override fun iterator(): Iterator<T> {
        return object : Iterator<T> {
            var index = 0

            override fun hasNext(): Boolean = true

            override fun next(): T {
                if (index == list.size) {
                    index = 0
                }
                return list[index++]
            }
        }
    }
}