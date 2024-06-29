package com.example.chatappb

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class signup : AppCompatActivity() {

    private lateinit var name : EditText
    private lateinit var userid : EditText
    private lateinit var pswd : EditText
    private lateinit var btnsignup : Button
    private lateinit var mAuth : FirebaseAuth
    private lateinit var mDbRef : DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        supportActionBar?.hide()

        mAuth = FirebaseAuth.getInstance()
        userid= findViewById(R.id.edtuserid)
        pswd= findViewById(R.id.edtpswd)
        name = findViewById(R.id.edtname)
        btnsignup= findViewById(R.id.btnsignup)

        btnsignup.setOnClickListener{
            val name = name.text.toString()
            val email = userid.text.toString()
            val password = pswd.text.toString()


            signUp(name,email,password)

        }

    }

    private fun signUp(name:String,email:String,password:String){
        //logic of creating a new user
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    //code if success then create a new intent
                    addUsertoDatabase(name,email,mAuth.currentUser?.uid!!)
                    val intent = Intent(this@signup,MainActivity::class.java)
                    finish()
                    startActivity(intent)

                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this@signup,"Some error occured",Toast.LENGTH_SHORT).show()
                }
            }


    }

    private fun addUsertoDatabase(name:String, email:String,uid:String){

        mDbRef = FirebaseDatabase.getInstance().getReference()
        mDbRef.child("User").child(uid).setValue(User(name,email,uid))
    }
}