package com.example.datastoredemo

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class SecondViewModel : ViewModel() {

    var dataModelFlow: Flow<DataModel> = emptyFlow()

    fun updateText(dataStore: DataStore<DataModelPreference>) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStore.updateData { proto ->
                proto.toBuilder()
                    .setAge(proto.age + 1)
                    .setName((proto.name.toInt() + 1).toString())
                    .build()
            }
        }
    }

    fun getText(dataStore: DataStore<DataModelPreference>) {
        viewModelScope.launch(Dispatchers.IO) {
            dataModelFlow = dataStore.data.map { pref ->
                Log.d("AAAA", "name: ${pref.name} age: ${pref.age}")
                DataModel(pref.name, pref.age)
            }
        }
    }

    fun resetText(dataStore: DataStore<DataModelPreference>) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStore.updateData { proto ->
                proto.toBuilder()
                    .setAge(1)
                    .setName("1")
                    .build()
            }
        }
    }
}