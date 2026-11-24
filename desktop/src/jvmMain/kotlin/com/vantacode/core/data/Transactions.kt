/*
 * VantaCode Desktop — Room KMP 事务入口。
 * Android 版用 androidx.room.withTransaction；KMP runtime 只暴露
 * performInTransactionSuspending，这里包一层保持调用点不变。
 * Copyright (C) 2026 VantaCode contributors. GPL-3.0.
 */
package com.vantacode.core.data

import androidx.room.RoomDatabase
import androidx.room.util.performInTransactionSuspending

suspend fun <R> RoomDatabase.withTransaction(block: suspend () -> R): R =
    performInTransactionSuspending(this) { block() }
