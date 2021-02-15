package com.example.datastoredemo

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.datastore.core.DataStore
import androidx.datastore.createDataStore
import androidx.lifecycle.lifecycleScope
import com.example.datastoredemo.databinding.ActivitySecondBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.observeOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SecondActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondBinding
    private val viewModel by viewModels<SecondViewModel>()

    private val datastore: DataStore<DataModelPreference> = createDataStore(
        fileName = PROTO_DATA_FILE_NAME,
        serializer = DataModelSerializer
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_second)
        title = "Proto DataStore"

        binding.showButton.setOnClickListener {
            viewModel.getText(datastore)
            observeFlow()
        }

        binding.updateButton.setOnClickListener {
            viewModel.updateText(datastore)
            observeFlow()
        }

        binding.resetButton.setOnClickListener {
            viewModel.resetText(datastore)
            observeFlow()
        }

    }

    private fun observeFlow() {
        lifecycleScope.launch {
            viewModel.dataModelFlow.collect {
                val text = "name: ${it.name} age: ${it.age}"
                Log.d("AAAA", text)
                binding.textview.text = text
            }
        }
    }

    companion object {
        const val PROTO_DATA_FILE_NAME = "data_model_preference.pb"
    }

}