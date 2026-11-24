/*
 * VantaCode Desktop — DataStore delegate matching the Android call sites:
 *
 *     private val DesktopContext.xDataStore by preferencesDataStore(name = "x", ...)
 *
 * Stores under ~/.vantacode/files/datastore/<name>.preferences_pb.
 * Copyright (C) 2026 VantaCode contributors. GPL-3.0.
 */
package com.vantacode.core.datastore

import androidx.datastore.core.DataMigration
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import com.vantacode.core.platform.DesktopContext
import com.vantacode.core.platform.PlatformPaths
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import java.util.concurrent.ConcurrentHashMap
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

private val instances = ConcurrentHashMap<String, DataStore<Preferences>>()

fun preferencesDataStore(
    name: String,
    corruptionHandler: ReplaceFileCorruptionHandler<Preferences>? = null,
    scope: CoroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
    migrations: List<DataMigration<Preferences>> = emptyList()
): ReadOnlyProperty<DesktopContext, DataStore<Preferences>> {
    return object : ReadOnlyProperty<DesktopContext, DataStore<Preferences>> {
        override fun getValue(thisRef: DesktopContext, property: KProperty<*>): DataStore<Preferences> =
            instances.getOrPut(name) {
                PreferenceDataStoreFactory.create(
                    corruptionHandler = corruptionHandler,
                    migrations = migrations,
                    scope = scope,
                    produceFile = {
                        java.io.File(PlatformPaths.filesDir, "datastore/$name.preferences_pb")
                            .apply { parentFile.mkdirs() }
                    }
                )
            }
    }
}
