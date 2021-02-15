package com.example.datastoredemo

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    val textLiveData: MutableLiveData<String> = MutableLiveData()

    fun saveText(dataStore: DataStore<Preferences>, content: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val textKey = stringPreferencesKey(MainActivity.DATA_STORE_TEXT_KEY)
            dataStore.edit { settings ->
                settings[textKey] = content
            }
        }
    }

    fun getText(dataStore: DataStore<Preferences>) {
        viewModelScope.launch(Dispatchers.IO) {
            val textKey = stringPreferencesKey(MainActivity.DATA_STORE_TEXT_KEY)
            dataStore.edit { settings ->
                val text = settings[textKey]
                textLiveData.postValue(text)
            }
        }
    }
}