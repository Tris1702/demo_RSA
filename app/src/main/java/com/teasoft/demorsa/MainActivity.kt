package com.teasoft.demorsa

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.teasoft.demorsa.common.Resource
import com.teasoft.demorsa.databinding.ActivityMainBinding
import com.teasoft.demorsa.repository.RepositoryImpl
import com.teasoft.rsa.RSA
import kotlinx.coroutines.*


class MainActivity : AppCompatActivity() {
    val repo = RepositoryImpl()
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        val pref = getSharedPreferences("Data", MODE_PRIVATE)

        binding.btnConnect.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                repo.sendUid("12x23sf").collect{
                    when(it){
                        is Resource.Loading ->
                            Log.e("Resource", "loading" )
                        is Resource.Error ->
                            Log.e("Resource Error", it.data.toString() )
                        else -> {
                            Log.e("Resource Success", it.data.toString())
                            pref.edit().putString("publicKey", it.data.toString()).apply()
                        }
                    }
                }
            }
        }
        binding.btnSend.setOnClickListener {
            lifecycleScope.launch(Dispatchers.Main){
                val keyString = pref.getString("publicKey", null)
                keyString?.let{
                    repo.sendMessage(RSA(keyString).encrypt(binding.edtMessage.text.toString())).collect{
                        when(it){
                            is Resource.Loading ->
                                Log.e("Resource", "loading" )
                            is Resource.Error ->
                                Log.e("Resource Error", it.data.toString() )
                            else -> {
                                Log.e("Resource Successful", it.data.toString())

                            }
                        }
                    }
                }
            }
        }
    }
}