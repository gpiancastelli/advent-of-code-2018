package aoc2018

import aoc2018.util.readLines

fun main(args: Array<String>) {
    val lines = readLines("input7.txt")

    // solve first part
    val order = findCompletingOrder(buildStepSet(lines))
    // result: BFKEGNOVATIHXYZRMCJDLSUPWQ
    println(order)

    // solve second part
    val time = findCompletionTime(buildStepSet(lines))
    // result: 1020
    println(time)
}

fun findCompletionTime(stepSet: MutableSet<Step>): Int {
    var elapsedTime = -1
    val workerCount = 5
    val workerMap = mutableMapOf<Step, Int>()
    while (stepSet.isNotEmpty() || workerMap.isNotEmpty()) {
        workerMap.toMap().forEach { (step, time) ->
            if (time - 1 == 0) {
                workerMap.remove(step)
                step.postSet.forEach { it.preSet.remove(step) }
                stepSet.remove(step)
            } else {
                workerMap[step] = time - 1
            }
        }

        val nextSteps = stepSet.filter { it.preSet.isEmpty() }.sortedBy { it.id }.map { it to it.id[0] - 'A' + 1 + 60 }

        nextSteps.filter {
            it.first !in workerMap
        }.take(workerCount - workerMap.size).forEach {
            workerMap[it.first] = it.second
        }

        elapsedTime++
    }
    return elapsedTime
}

fun findCompletingOrder(stepSet: MutableSet<Step>): StringBuilder {
    val order = StringBuilder()
    while (stepSet.isNotEmpty()) {
        val nextStepCandidates = stepSet.filter { it.preSet.isEmpty() }.sortedBy { it.id }
        val nextStep = nextStepCandidates[0]
        order.append(nextStep.id)
        nextStep.postSet.forEach { it.preSet.remove(nextStep) }
        stepSet.remove(nextStep)
    }
    return order
}

fun buildStepSet(lines: List<String>): MutableSet<Step> {
    val stepMap = mutableMapOf<String, Step>()
    lines.forEach {
        val steps = """[Ss]tep (\w)""".toRegex().findAll(it)
        val (pre) = steps.first().destructured
        val (post) = steps.last().destructured
        val preStep = stepMap.getOrPut(pre) { Step(pre) }
        val postStep = stepMap.getOrPut(post) { Step(post) }

        preStep.postSet.add(postStep)
        postStep.preSet.add(preStep)
    }
    return stepMap.values.toMutableSet()
}

class Step(val id: String) {
    val preSet = mutableSetOf<Step>()
    val postSet = mutableSetOf<Step>()
}