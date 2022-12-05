package com.hama.dateapp.view.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.hama.dateapp.R
import com.hama.dateapp.databinding.ActivityLoginBinding
import com.hama.dateapp.databinding.ActivitySignUpBinding
import com.hama.dateapp.view.MainActivity

class SignUp : AppCompatActivity() {
    private val TAG: String = "_SignUp"

    private var _binding: ActivitySignUpBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth

    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        //회원가입
        binding.btnSignUp.setOnClickListener {
            var email = binding.inputEmail.text.toString()
            var passwd = binding.inputPw.text.toString()
            var passwdRe = binding.inputPwRe.text.toString()

            if (email.isNotEmpty()) {
                if (passwd.isNotEmpty() && passwdRe.isNotEmpty() && passwd.equals(passwdRe)) {
                    auth.createUserWithEmailAndPassword(email, passwd)
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success")
                                updateUserInfo()

                                val intent = Intent(this, MainActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                startActivity(intent)

                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.exception)
                                Toast.makeText(
                                    baseContext, "Authentication failed.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                } else {
                    Toast.makeText(this, "비밀번호를 확인해주세요", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "이메일을 입력해주세요", Toast.LENGTH_SHORT).show()
            }
        }


    }


    private fun updateUserInfo() {
        val user = auth.currentUser
        // Create a new user with a first and last name
        val setUser = hashMapOf(
            "uid" to user!!.uid,
            "email" to user.email,
            "displayName" to user.displayName
        )

        // Add a new document with a generated ID
        db.collection("User")
            .add(setUser)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}