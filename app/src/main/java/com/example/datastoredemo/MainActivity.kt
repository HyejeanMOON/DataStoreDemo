package com.example.datastoredemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.createDataStore
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.datastoredemo.databinding.ActivityMainBinding
import kotlinx.coroutines.coroutineScope

class MainActivity : AppCompatActivity() {

    private val dataStore: DataStore<Preferences> = createDataStore(
        name = DATA_STORE_KEY
    )

    private val viewModel by viewModels<MainViewModel>()

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        title = "Preferences DataStore"

        binding.confirmButton.setOnClickListener {
            val text = binding.edittext.text.toString()

            viewModel.saveText(dataStore, text)
        }

        binding.showButton.setOnClickListener {
            viewModel.getText(dataStore)
        }

        binding.nextButton.setOnClickListener {
            val intent = Intent()
            intent.setClass(this, SecondActivity::class.java)
            startActivity(intent)
        }

        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.textLiveData.observe(this, Observer {
            binding.textview.text = it
        })
    }


    companion object {
        const val DATA_STORE_KEY = "DATA_STORE_KEY"
        const val DATA_STORE_TEXT_KEY = "DATA_STORE_TEXT_KEY"
    }
}