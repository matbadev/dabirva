package com.matbadev.dabirva.example.util

import java.util.concurrent.Executor

/**
 * An [Executor] that queues tasks until they are manually executed with [executePendingTasks].
 */
class QueueExecutor : Executor {

    private val pendingTasks = mutableListOf<Runnable>()

    val pendingTasksCount: Int
        get() = pendingTasks.size

    override fun execute(runnable: Runnable) {
        pendingTasks.add(runnable)
    }

    fun executePendingTasks() {
        while (pendingTasks.isNotEmpty()) {
            val task = pendingTasks.removeFirst()
            task.run()
        }
    }

}
