package com.example.domain.contract.FirebaseDataSource

import android.net.Uri
import com.example.domain.contract.Resource
import com.example.domain.model.AppUser
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.storage.UploadTask
import kotlinx.coroutines.flow.Flow

interface FirebaseAuthenticationRepository {

    suspend fun checkCurrentUserStatus(): Flow<Resource<AppUser?>>

    suspend fun getCurrentUserData(): Flow<Resource<AppUser?>>

    suspend fun signIn(email:String,password:String):Flow<Resource<AppUser>>

    suspend fun signUp(email:String,password:String):Flow<Resource<AppUser>>

    suspend fun uploadImageToFirestorage(uri: Uri): Flow<Resource<Any>>

    suspend fun getDownloadLink(): Flow<Resource<Uri>>

    suspend fun addNewUser(user: AppUser): Flow<Resource<Any>>

    suspend fun logout()


}