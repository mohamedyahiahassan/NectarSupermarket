package com.example.domain.contract.repository

import android.net.Uri
import com.example.domain.contract.Resource
import com.example.domain.model.AppUser
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface AuthenticationRepo {

    suspend fun checkCurrentUserStatus(): Flow<Resource<AppUser?>>

    suspend fun getCurrentUserData(): Flow<Resource<AppUser?>>

    suspend fun signIn(email:String,password:String):Flow<Resource<AppUser>>

    suspend fun signUp(email:String,password:String):Flow<Resource<AppUser>>

    suspend fun addImage(uri: Uri): Flow<Resource<Any>>

    suspend fun getDownloadLink(): Flow<Resource<Uri>>

    suspend fun addNewUser(user: AppUser): Flow<Resource<Any>>

    suspend fun logout()

}