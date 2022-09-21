package com.teasoft.demorsa.repository

import com.teasoft.demorsa.common.Resource
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun sendUid(uid: String): Flow<Resource<String>>

    suspend fun sendMessage(encryptedMessage: String): Flow<Resource<String>>
}