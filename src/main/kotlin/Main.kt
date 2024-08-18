package org.example

import java.util.LinkedList
import java.util.Queue

data class State(val value: Int, val operations: List<String>)

fun main() {
    println("Введите целое число:")
    val target = readLine()?.toIntOrNull()

    if (target == null) {
        println("Некорректный ввод.")
        return
    }

    val result = findMinOperations(target)

    if (result.isEmpty()) {
        println("Невозможно получить заданное число с помощью операций сложения и умножения чисел от 1 до 15.")
    } else {
        println("Минимальное количество действий для получения $target:")
        println(result.joinToString(" "))
    }
}

fun findMinOperations(target: Int): List<String> {
    val queue: Queue<State> = LinkedList()
    val visited = mutableSetOf<Int>()

    queue.add(State(0, emptyList()))

    while (queue.isNotEmpty()) {
        val currentState = queue.poll()

        if (currentState.value == target) {
            return currentState.operations
        }

        for (i in 1..15) {
            // Проверка операции сложения
            val newValueAdd = currentState.value + i
            if (newValueAdd <= target && newValueAdd !in visited) {
                visited.add(newValueAdd)
                queue.add(State(newValueAdd, currentState.operations + "+$i"))
            }

            // Проверка операции умножения
            if (currentState.value != 0) { // Избегаем умножения на 0
                val newValueMul = currentState.value * i
                if (newValueMul <= target && newValueMul !in visited) {
                    visited.add(newValueMul)
                    queue.add(State(newValueMul, currentState.operations + "*$i"))
                }
            }
        }
    }

    return emptyList()
}
