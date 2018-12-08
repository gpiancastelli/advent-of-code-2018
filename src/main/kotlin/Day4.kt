package aoc2018

import aoc2018.util.readLines

fun main(args: Array<String>) {
    val lines = readLines("input4.txt").sorted()

    val guards = createGuardMap(lines)

    // solve first part
    val guardAndMinuteByMostAsleep = findGuardAndMinuteByMostAsleep(guards)
    // result: 39698
    println(guardAndMinuteByMostAsleep.first * guardAndMinuteByMostAsleep.second)

    // solve second part
    val guardAndMinuteByMostFrequentlyAsleep = findGuardAndMinuteByMostFrequentlyAsleep(guards)
    // result: 14920
    println(guardAndMinuteByMostFrequentlyAsleep.first * guardAndMinuteByMostFrequentlyAsleep.second)
}

fun findGuardAndMinuteByMostAsleep(guards: Map<Int, IntArray>): Pair<Int, Int> {
    val mostAsleepGuard = guards.maxBy { it.value.sum() }
    val mostAsleepMinute = mostAsleepGuard!!.value.indexOf(mostAsleepGuard.value.max()!!)
    return mostAsleepGuard.key to mostAsleepMinute
}

fun findGuardAndMinuteByMostFrequentlyAsleep(guards: Map<Int, IntArray>): Pair<Int, Int> {
    val mostFrequentlyAsleepGuard = guards.maxBy { it.value.max()!! }
    val mostFrequentlyAsleepMinute = mostFrequentlyAsleepGuard!!.value.indexOf(
        mostFrequentlyAsleepGuard.value.max()!!
    )
    return mostFrequentlyAsleepGuard.key to mostFrequentlyAsleepMinute
}

fun createGuardMap(lines: List<String>): MutableMap<Int, IntArray> {
    val guards = mutableMapOf<Int, IntArray>()
    var lastGuardId = 0
    var lastFallsAsleep = 0
    for (line in lines) {
        val (minute, message) = line.substring(line.indexOf(':') + 1).split("] ")
        if ("falls asleep" == message) {
            lastFallsAsleep = minute.toInt()
        } else if ("wakes up" == message) {
            for (m in lastFallsAsleep until minute.toInt()) {
                guards[lastGuardId]!![m] = guards[lastGuardId]!![m] + 1
            }
        } else {
            lastGuardId = """\d+""".toRegex().find(message)!!.value.toInt()
            guards.putIfAbsent(lastGuardId, IntArray(60) { 0 })
        }
    }
    return guards
}