package com.teasoft.demorsa.repository

import android.util.Log
import com.teasoft.demorsa.ApiService
import com.teasoft.demorsa.common.Resource
import com.teasoft.demorsa.model.EncryptedMess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception

class RepositoryImpl : Repository {
    override suspend fun sendUid(uid: String): Flow<Resource<String>> = flow{
        emit(Resource.Loading())
        try{
            val response = ApiService.apiService.sendUid(uid)
            if (response.isSuccessful){
                emit(Resource.Success(response.body()!!.publicKey))
            } else{
                emit(Resource.Error("error", response.errorBody().toString()))
            }
        } catch (e: Exception){
            emit(Resource.Error<String>("error", e.toString()))
        }

    }.flowOn(Dispatchers.IO)

    override suspend fun sendMessage(encryptedMessage: String): Flow<Resource<String>> = flow<Resource<String>> {
        emit(Resource.Loading())
        Log.e("EncryptedMess", encryptedMessage)
        try{
            val response = ApiService.apiService.sendMessage(EncryptedMess(message = encryptedMessage))
            if (response.isSuccessful){
                emit(Resource.Success(response.body()!!.message))
            } else {
                emit(Resource.Error("error", response.errorBody().toString()))
            }
        }catch (e: Exception){
            emit(Resource.Error("error", e.toString()))
        }
    }.flowOn(Dispatchers.IO)
}