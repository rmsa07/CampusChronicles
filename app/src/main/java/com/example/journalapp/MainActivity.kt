package com.example.journalapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.journalapp.databinding.ActivityMainBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.StorageReference

class MainActivity : AppCompatActivity() {
     private lateinit var binding: ActivityMainBinding

    //Firebase
    lateinit var auth : FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       //setContentView(R.layout.activity_main)

       binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.my_toolbar))

//        val db = Firebase.firestore
//        val user_collection = db.collection("Jounal")
//        val user1= hashMapOf(
//            "title" to "gg"
//        )
//        user_collection.document("user1").set(user1)
//
//

        binding.createAccBTN.setOnClickListener(){
            val intent = Intent(this,SignUpActivity::class.java)
            startActivity(intent)
        }

        binding.emailSignin.setOnClickListener(){
            LoginWithEmailPassword(
                binding.email.text.toString().trim(),
                binding.password.text.toString().trim()
            )
        }

        auth = Firebase.auth

    }

    private fun LoginWithEmailPassword(email: String, password: String) {

        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener(this) { task->

                if(task.isSuccessful){
                var journal :JournalUser = JournalUser.instance!!
                    journal.userId = auth.currentUser?.uid
                    journal.username=auth.currentUser?.displayName
                gotToJournalList()
            }else{
                Toast.makeText(this,"Login Failed",Toast.LENGTH_SHORT).show()
                }

        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser

        if(currentUser != null){
            gotToJournalList()
        }
    }

    private fun gotToJournalList() {
        val intent = Intent(this,JournalList::class.java)
        startActivity(intent)
    }
}