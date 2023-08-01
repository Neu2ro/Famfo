package com.example.famfo

import android.provider.ContactsContract.CommonDataKinds.Email
import android.provider.ContactsContract.CommonDataKinds.Phone

data class User(
    val name : String? = null,
    val phoneNo : Phone? = null,
    val email : Email? = null,
    val username : String? = null){

}
