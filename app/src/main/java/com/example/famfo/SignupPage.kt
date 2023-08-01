package com.example.famfo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.famfo.databinding.ActivityMainBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignupPage : AppCompatActivity() {

    private lateinit var namevw : EditText
    private lateinit var phoneNovw : EditText
    private lateinit var emailvw : EditText
    private lateinit var userNamevw : EditText
    private lateinit var register : Button

    private lateinit var dbRef : DatabaseReference

    private val emailAddress = "[a-zA-Z0-9,_-]+@[a-z]+\\.+[a-z]+"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_page)

        namevw = findViewById(R.id.name)
        phoneNovw = findViewById(R.id.phoneNo)
        emailvw = findViewById(R.id.email)
        userNamevw = findViewById(R.id.userName)
        register = findViewById(R.id.registerBtn)

        dbRef = FirebaseDatabase.getInstance().getReference("Members")

        register.setOnClickListener{
            saveMembersData()
        }
    }

    private fun saveMembersData() {
        // Getting values
        val mname = namevw.text.toString()
        val mphone = phoneNovw.text.toString()
        val memail = emailvw.text.toString()
        val musername = userNamevw.text.toString()

        if(mname.isEmpty()){
            namevw.error = "Please enter name"
        }
        if(mphone.isEmpty() || mphone.length != 10){
            phoneNovw.error = "Please enter valid phone no."
        }
        if(memail.isEmpty() || !memail.matches(emailAddress.toRegex())){
            emailvw.error = "Please enter valid email"
        }
        if(musername.isEmpty()){
            userNamevw.error = "Please enter username"
        }
        else {
//        val memId = dbRef.push().key!!
            val member = MemberModelTwo(mname, mphone, memail, musername)
//        val member = MemberModelTwo(memId, mname, mphone, memail, musername)
            dbRef.child(mphone).setValue(member)
                .addOnCompleteListener{
                    Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG).show()
                    namevw.text.clear()
                    phoneNovw.text.clear()
                    emailvw.text.clear()
                    userNamevw.text.clear()
                    val intent = Intent(this, LoginPage::class.java)
                    startActivity(intent)
                }.addOnFailureListener{err ->
                    Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
                }
        }
    }
}