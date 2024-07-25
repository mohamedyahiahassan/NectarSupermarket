package com.example.data.di

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn (SingletonComponent::class)
object FirebaseModule {


    @Provides
    fun providesAuthInstance(): FirebaseAuth {

        return FirebaseAuth.getInstance()
    }

    @Provides
    fun providesFirebaseFirestore():FirebaseFirestore{

        return Firebase.firestore
    }

    @Provides
    fun providesFirebaseStorage():FirebaseStorage{

        return Firebase.storage
    }

}