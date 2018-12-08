package aoc2018

import aoc2018.util.readLines
import kotlin.math.absoluteValue

fun main(args: Array<String>) {
    val lines = readLines("input6.txt")

    val points = lines.map { line ->
        line.split(", ").map { it.toInt() }
    }.map { it[0] to it[1] }

    val gridSize = points.flatMap { listOf(it.first, it.second) }.max()!!

    // solve first part
    val largestFiniteAreaSize = findLargestFiniteAreaSize(points, gridSize)
    // result: 4976
    println(largestFiniteAreaSize)

    // solve second part
    val maximumTotalDistanceAreaSize = findMaximumTotalDistanceAreaSize(points, gridSize, 10_000)
    // result: 46462
    println(maximumTotalDistanceAreaSize)
}

fun findMaximumTotalDistanceAreaSize(points: List<Pair<Int, Int>>, gridSize: Int, maxDistance: Int): Int {
    var areaSize = 0
    for (i in 0 until gridSize) {
        for (j in 0 until gridSize) {
            val distances = points.map { distance(it,i to j) }
            val totalDistance = distances.sum()
            if (totalDistance < maxDistance) {
                areaSize++
            }
        }
    }
    return areaSize
}

fun findLargestFiniteAreaSize(points: List<Pair<Int, Int>>, gridSize: Int): Int {
    val excludedPointIndexes = mutableSetOf<Int>()
    for (i in 0 until gridSize) {
        excludedPointIndexes.add(minimumDistantPointIndex(points, i to 0))
        excludedPointIndexes.add(minimumDistantPointIndex(points, i to gridSize - 1))
    }
    for (j in 0 until gridSize) {
        excludedPointIndexes.add(minimumDistantPointIndex(points, 0 to j))
        excludedPointIndexes.add(minimumDistantPointIndex(points, gridSize - 1 to j))
    }

    val areaMap = mutableMapOf<Int, Int>()
    for (i in 0 until points.size) {
        areaMap[i] = 0
    }
    for (i in 1 until gridSize - 1) {
        for (j in 1 until gridSize - 1) {
            val pointIndex = minimumDistantPointIndex(points, i to j)
            if (pointIndex >= 0) {
                areaMap.merge(pointIndex, 1, Int::plus)
            }
        }
    }
    excludedPointIndexes.forEach { areaMap.remove(it) }
    return areaMap.maxBy { it.value }!!.value
}

fun minimumDistantPointIndex(points: List<Pair<Int, Int>>, point: Pair<Int, Int>): Int {
    val distances = points.map { distance(it, point) }
    val minDistance = distances.min()
    return if (distances.count { it == minDistance } > 1) -1 else distances.indexOf(minDistance)
}

fun distance(point: Pair<Int, Int>, otherPoint: Pair<Int, Int>) =
    (point.first - otherPoint.first).absoluteValue + (point.second - otherPoint.second).absoluteValue