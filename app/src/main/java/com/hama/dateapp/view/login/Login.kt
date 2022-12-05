package com.hama.dateapp.view.login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.hama.dateapp.R
import com.hama.dateapp.databinding.ActivityLoginBinding
import com.hama.dateapp.view.MainActivity

class Login : AppCompatActivity() {
    private val TAG:String = "_Login"

    private var _binding :ActivityLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        //로그인
        binding.btnLogin.setOnClickListener {
            var email = binding.etId.text.toString()
            var passwd = binding.etPassword.text.toString()

            if(email.isNotEmpty()){
                if(passwd.isNotEmpty()){
                    auth.signInWithEmailAndPassword(email, passwd)
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                reload()
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail:failure", task.exception)
                                Toast.makeText(baseContext, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show()
                            }
                        }
                }else{
                    Toast.makeText(this,"비밀번호를 입력해주세요",Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this,"이메일을 입력해주세요",Toast.LENGTH_SHORT).show()
            }
        }

        //회원가입
        binding.btnLoginSignUp.setOnClickListener {
            var intent = Intent(this,SignUp::class.java)
            startActivity(intent)
        }



    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if(currentUser != null){
            reload();
        }
    }

    private fun reload(){
        var intent = Intent(this,MainActivity::class.java)
        finish()
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}