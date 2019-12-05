package com.example.reservationapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    var id = "id"
    var password = "pw"

    val mAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        button_loginActLogin.setOnClickListener {
            id = editText_loginActID.text.toString()
            password = editText_loginActPW.text.toString()

            if(id.isBlank() || password.isBlank()){
                Toast.makeText(baseContext, "아이디와 비밀번호를 확인하세요", Toast.LENGTH_SHORT).show()
            } else {
                signIn(id, password)
            }
        }

        button_loginActSignUp.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }

        button_loginActNoti.setOnClickListener{
            val intent = Intent(this, NotiTestActivity::class.java)
            startActivity(intent)
        }

        button_loginActForgotPW.setOnClickListener{
            val intent = Intent(this, DatabaseAdministerActivity::class.java)
            startActivity(intent)
        }

        button_loginActForgotPW2.setOnClickListener{
            val intent = Intent(this, DatabaseAdminister2Activity::class.java)
            startActivity(intent)
        }
    }

    fun signIn(id:String, password:String){
        mAuth.signInWithEmailAndPassword(id, password).addOnCompleteListener(this) { task ->
            if(task.isSuccessful){
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
            else{
                Toast.makeText(baseContext, "아이디와 비밀번호를 확인하세요", Toast.LENGTH_SHORT).show()
            }
        }
    }
}