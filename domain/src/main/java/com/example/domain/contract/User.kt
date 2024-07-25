package com.example.domain.contract

import com.example.domain.model.AppUser
import com.google.firebase.auth.FirebaseUser

object User {

    var user:FirebaseUser? = null
    var appUser: AppUser? = null

    val publishableKey = "pk_test_51PehzlIjgmnnb5gvjQsr7X7u6KJeZX1F0DarLi5AatQMu4ODTIt1ARg4YFNj0q5B83l0sVLdCiUZklnntm3aFuHm00EH7ua4SB"

}