package com.matbadev.dabirva.util

import java.util.concurrent.Executor

/**
 * [Executor] that runs each task in the thread that invokes [execute].
 */
class DirectExecutor : Executor {

    override fun execute(command: Runnable) {
        command.run()
    }

}
