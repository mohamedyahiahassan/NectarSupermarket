package com.example.data.repository

import android.net.Uri
import com.example.domain.contract.FirebaseDataSource.FirebaseAuthenticationRepository
import com.example.domain.contract.Resource
import com.example.domain.contract.repository.AuthenticationRepo
import com.example.domain.model.AppUser
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthenticationRepoImpl @Inject constructor(private val firebaseAuthenticationRepository: FirebaseAuthenticationRepository): AuthenticationRepo {


    override suspend fun checkCurrentUserStatus(): Flow<Resource<AppUser?>> {

        return firebaseAuthenticationRepository.checkCurrentUserStatus()
    }

    override suspend fun getCurrentUserData(): Flow<Resource<AppUser?>> {

        return firebaseAuthenticationRepository.getCurrentUserData()
    }

    override suspend fun signIn(email: String, password: String): Flow<Resource<AppUser>> {

        return firebaseAuthenticationRepository.signIn(email, password)
    }

    override suspend fun signUp(email: String, password: String): Flow<Resource<AppUser>> {

        return firebaseAuthenticationRepository.signUp(email,password)
    }

    override suspend fun addImage(uri: Uri): Flow<Resource<Any>>  {

        return firebaseAuthenticationRepository.uploadImageToFirestorage(uri)
    }

    override suspend fun getDownloadLink(): Flow<Resource<Uri>> {

        return firebaseAuthenticationRepository.getDownloadLink()
    }

    override suspend fun addNewUser(user: AppUser): Flow<Resource<Any>> {

        return firebaseAuthenticationRepository.addNewUser(user)
    }

    override suspend fun logout() {
        return firebaseAuthenticationRepository.logout()
    }

}