package com.matbadev.dabirva.internal

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

internal object CommonExecutors {

    val itemDiffing: ExecutorService by lazy { Executors.newFixedThreadPool(2) }

}
