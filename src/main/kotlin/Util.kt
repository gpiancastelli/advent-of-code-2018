package aoc2018.util

import java.io.File

fun readLines(fileName: String): List<String> =
    File(ClassLoader.getSystemResource(fileName).file).readLines()