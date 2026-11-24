package com.vantacode.feature.agreement.data
import com.vantacode.core.platform.DesktopContext

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.vantacode.core.datastore.preferencesDataStore
import com.vantacode.core.datastore.preferencesCorruptionHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val DesktopContext.agreementDataStore by preferencesDataStore(
    name = "agreement_prefs",
    corruptionHandler = preferencesCorruptionHandler
)

@Singleton
class AgreementRepository @Inject constructor(
    private val context: DesktopContext
) {
    private companion object {
        val ACCEPTED_KEY = booleanPreferencesKey("agreement_accepted")
    }

    val acceptedFlow: Flow<Boolean> =
        context.agreementDataStore.data.map { it[ACCEPTED_KEY] ?: false }

    suspend fun accept() {
        context.agreementDataStore.edit { it[ACCEPTED_KEY] = true }
    }
}
