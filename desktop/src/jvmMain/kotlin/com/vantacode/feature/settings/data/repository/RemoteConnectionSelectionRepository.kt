/*
 * VantaCode Desktop — persistence for the user's active remote connection (the
 * desktop counterpart of the Android "active container profile").
 * Copyright (C) 2026 VantaCode contributors. GPL-3.0.
 */
package com.vantacode.feature.settings.data.repository

import com.vantacode.core.datastore.preferencesCorruptionHandler
import com.vantacode.core.datastore.preferencesDataStore
import com.vantacode.core.platform.DesktopContext
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val DesktopContext.remoteConnectionSelectionDataStore by preferencesDataStore(
    name = "remote_connection_selection_prefs",
    corruptionHandler = preferencesCorruptionHandler
)

/**
 * Holds the id of the remote connection the app should use in REMOTE_SSH mode (and in
 * local mode remembers the last selection so flipping back is instant).
 */
@Singleton
class RemoteConnectionSelectionRepository @Inject constructor(
    private val context: DesktopContext
) {
    private companion object {
        val ACTIVE_KEY = stringPreferencesKey("active_remote_connection_id")
    }

    val activeIdFlow: Flow<String?> = context.remoteConnectionSelectionDataStore.data.map { it[ACTIVE_KEY] }

    suspend fun setActiveId(id: String?) {
        context.remoteConnectionSelectionDataStore.edit {
            if (id == null) it.remove(ACTIVE_KEY) else it[ACTIVE_KEY] = id
        }
    }
}
