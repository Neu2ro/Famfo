package com.example.famfo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.*

class LoginPage : AppCompatActivity() {

    private lateinit var dbRef : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)
        val phoneNovw : EditText = findViewById(R.id.phoneNo)
        val userNamevw : EditText = findViewById(R.id.userName)
        val loginBtn : Button = findViewById(R.id.loginBtn)
        val signupBtn : Button = findViewById(R.id.signupBtn)
        val backBtn : Button = findViewById(R.id.back)

        signupBtn.setOnClickListener{
            val intent = Intent(this, SignupPage::class.java)
            startActivity(intent)
        }

        backBtn.setOnClickListener{
            val intent = Intent(this, ApplicationSwitch::class.java)
            startActivity(intent)
        }

        loginBtn.setOnClickListener{
            val phone = phoneNovw.text.toString()
            val user = userNamevw.text.toString()
            var name = ""
            var email = ""
            var isMatched = false

            if(phone.isEmpty() || phone.length != 10){
                userNamevw.error = "Please enter valid phone no."
            }
            if(user.isEmpty()){
                phoneNovw.error = "Please enter username"
            }
            else{
                dbRef = FirebaseDatabase.getInstance().getReference("Members")
                dbRef.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists()) {
                            for (memSnap in snapshot.children) {
                                val memData = memSnap.getValue(MemberModelTwo::class.java)
                                val dbPhone = memData?.mphoneNo
                                val dbUsername = memData?.musername
                                if (phone.equals(dbPhone) && user.equals(dbUsername)) {
                                    name = memData?.mname.toString()
                                    email = memData?.memail.toString()
                                    isMatched = true
                                    break
                                }
                            }
                            if(isMatched){
                                Toast.makeText(applicationContext, "Login Successfull", Toast.LENGTH_LONG).show()
                                val intent = Intent(applicationContext, MainActivity::class.java).also {
                                    it.putExtra("Name", name)
                                    it.putExtra("Username", user)
                                    it.putExtra("Phone", phone)
                                    it.putExtra("Email", email)
                                    startActivity(it)
                                }
                                startActivity(intent)
                            }
                            else{
                                Toast.makeText(applicationContext, "Login UnSuccessfull", Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                    override fun onCancelled(error: DatabaseError) {
                    }
                })
            }
        }
    }
}