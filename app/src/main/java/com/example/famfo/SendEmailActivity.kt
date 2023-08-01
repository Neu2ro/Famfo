package com.example.famfo

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.famfo.databinding.ActivityMainBinding

class SendEmailActivity : AppCompatActivity() {

    private lateinit var subject : EditText
    private lateinit var mail : EditText
    private lateinit var message : EditText
    private lateinit var sendBtn : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_email)

        subject = findViewById(R.id.subjectOfMail)
        mail = findViewById(R.id.toMail)
        message = findViewById(R.id.messageToSent)
        sendBtn = findViewById(R.id.sendBtn)

        sendBtn.setOnClickListener {
            val emailAddress = mail.text.toString()
            val subjectToSend = subject.text.toString()
            val messageToSend = message.text.toString()

            val addresses = emailAddress.split(",".toRegex()).toTypedArray()

            val intent = Intent(Intent.ACTION_SEND).apply {
                data = Uri.parse("mailto:")
                putExtra(Intent.EXTRA_EMAIL, addresses)
                putExtra(Intent.EXTRA_SUBJECT, subjectToSend)
                putExtra(Intent.EXTRA_TEXT, messageToSend)
            }

            val chooser = Intent.createChooser(intent, "Send email with: ")

            if(intent.resolveActivity(packageManager) != null) {
                startActivity(chooser)
            }
            else {
                Toast.makeText(applicationContext, "Required Application not installed", Toast.LENGTH_LONG).show()
            }
        }


    }
}