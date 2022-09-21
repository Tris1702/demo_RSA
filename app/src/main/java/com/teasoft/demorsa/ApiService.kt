package com.teasoft.demorsa

import com.google.gson.GsonBuilder
import com.teasoft.demorsa.model.DecryptedMess
import com.teasoft.demorsa.model.EncryptedMess
import com.teasoft.demorsa.model.PublicKeyModel
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    companion object{
        var gson = GsonBuilder()
            .setDateFormat("yyyy-mm-dd HH:mm:ss")
            .setLenient()
            .create()
        var apiService: ApiService = Retrofit.Builder()
            .baseUrl("http://192.168.1.56:8081")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService::class.java)
    }

    @POST("/devices")
    suspend fun sendUid(@Query("uid") uid: String): Response<PublicKeyModel>

    @POST("/data")
    suspend fun sendMessage(@Body encryptedMess: EncryptedMess): Response<DecryptedMess>
}