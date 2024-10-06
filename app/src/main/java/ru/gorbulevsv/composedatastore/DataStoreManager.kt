package ru.gorbulevsv.composedatastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesOf
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

class DataStoreManager(val context: Context) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user")

    companion object {
        val NAME = stringPreferencesKey("NAME")
        val PHONE_NUMBER = stringPreferencesKey("PHONE_NUMBER")
        val ADDRESS = stringPreferencesKey("ADDRESS")
    }

    suspend fun saveToDataStore(phonebook: PhoneBook) {
        context.dataStore.edit {
            it[NAME] = phonebook.name
            it[PHONE_NUMBER] = phonebook.phone
            it[ADDRESS] = phonebook.address
        }
    }

    fun getFromDataStore() = context.dataStore.data.map {
        PhoneBook(
            name = it[NAME] ?: "",
            phone = it[PHONE_NUMBER] ?: "",
            address = it[ADDRESS] ?: ""
        )
    }
}