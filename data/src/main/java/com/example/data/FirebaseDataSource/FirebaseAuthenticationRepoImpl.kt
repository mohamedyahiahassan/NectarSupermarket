package com.example.data.FirebaseDataSource

import android.net.Uri
import android.util.Log
import com.example.data.FirebaseConstants
import com.example.domain.model.AppUser
import com.example.domain.contract.User
import com.example.domain.contract.FirebaseDataSource.FirebaseAuthenticationRepository
import com.example.domain.contract.Resource
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseAuthenticationRepoImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore,
    private val firebaseStorage: FirebaseStorage) :FirebaseAuthenticationRepository {

    override suspend fun checkCurrentUserStatus(): Flow<Resource<AppUser?>> = flow {

        emit(Resource.Loading())

        val result = try {

            val user = firebaseAuth.currentUser

            User.appUser = AppUser(uid = user?.uid, email = user?.email)

            Resource.Success(User.appUser)
        } catch (e:Exception){

            Resource.Error(e.message.toString())

        }

        emit(result)

    }

    override suspend fun getCurrentUserData(): Flow<Resource<AppUser?>> = flow {

        val user: AppUser?

        val result =try {

            val response = firebaseFirestore.collection(FirebaseConstants.USERS).document(User.appUser?.uid!!).get().await()

            user = response.toObject(AppUser::class.java)

            User.appUser = user

            Resource.Success(user)

        } catch (e:Exception){

            Resource.Error(e.message.toString())
        }

        emit(result)

    }

    override suspend fun signIn(email: String, password: String):Flow<Resource<AppUser>> = flow {

        emit(Resource.Loading())

        val result = try {

            val data = firebaseAuth.signInWithEmailAndPassword(email,password).await()

            val user =  AppUser(uid = data.user?.uid,email = data.user?.email)

            User.appUser = user

            Resource.Success(user)

        } catch (e:Exception){

            Resource.Error(e.message.toString())
        }
        emit(result)

    }

    override suspend fun signUp(email: String, password: String):Flow<Resource<AppUser>> = flow{

        emit(Resource.Loading())

        val result = try {

            val response =  firebaseAuth.createUserWithEmailAndPassword(email,password).await()

            val user =  AppUser(uid = response.user?.uid,email = email)

            User.appUser = user

            Log.e("app user sign up",User.appUser.toString())

            Log.e("user data",user.email.toString())

            Resource.Success(user)

        } catch (e:Exception){

            Resource.Error(e.message.toString())
        }
        emit(result)

    }

    override suspend fun uploadImageToFirestorage(uri:Uri): Flow<Resource<Any>> = flow {

        emit(Resource.Loading())

        val result = try {

            val response = firebaseStorage.reference.child("${User.appUser?.uid}/profile").putFile(uri).await()


            Resource.Success(response as Any)

        } catch (e:Exception){

            Resource.Error(e.message.toString())

        }

        emit(result)

    }

    override suspend fun getDownloadLink(): Flow<Resource<Uri>>  = flow{

        emit(Resource.Loading())
        val result = try {

            val response = firebaseStorage.reference.child("${User.appUser!!.uid}/profile").downloadUrl.await()

            Resource.Success(response)

        } catch (e:Exception){

            Resource.Error(e.message.toString())

        }

        emit(result)

    }

    override suspend fun addNewUser(user: AppUser): Flow<Resource<Any>> = flow {

        emit(Resource.Loading())

        val result = try {

            val response = firebaseFirestore.collection(FirebaseConstants.USERS).document(User.appUser?.uid!!).set(user)

            Resource.Success(response as Any)

        } catch (e:Exception){

            Resource.Error(e.message.toString())
        }

        emit(result)

    }

    override suspend fun logout() {

        return firebaseAuth.signOut()
    }
}