package com.example.journalapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.journalapp.databinding.ActivityJournalListBinding
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.google.firebase.storage.StorageReference


class JournalList : AppCompatActivity() {

    private lateinit var binding:ActivityJournalListBinding

    lateinit var firebaseAuth : FirebaseAuth
    lateinit var user : FirebaseUser
    var db = FirebaseFirestore.getInstance()
    lateinit var storageReference: StorageReference
    var collectionReference:CollectionReference =db.collection("Jounal")

    lateinit var journalList : MutableList<Jounal>
    lateinit var adapter: JournalRecyclerAdapter




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_journal_list)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_journal_list)
        setSupportActionBar(findViewById(R.id.my_toolbar))

        firebaseAuth = Firebase.auth
        user = firebaseAuth.currentUser !!

        //Recycle View
        binding.recyclerView.setHasFixedSize(true)

        binding.recyclerView.layoutManager=LinearLayoutManager(this)

        //Posta arralist
        journalList = arrayListOf<Jounal>()


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.action_add -> if(user != null && firebaseAuth != null){
                val intent = Intent(this,AddJournalActivity::class.java)
                startActivity(intent)
            }
            R.id.action_signout->{
                if(user != null && firebaseAuth != null){
                    firebaseAuth.signOut()
                    val intent = Intent(this,MainActivity::class.java)
                    startActivity(intent)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
    //getting all posts
    override fun onStart() {
        super.onStart()

        collectionReference.whereEqualTo("userId",
            user.uid)
            .get()
            .addOnSuccessListener {
                if (!it.isEmpty) {

                    for (document in it) {

                        var journal =Jounal(
                            document.data.get("title").toString(),
                            document.data.get("thoughts").toString(),
                            document.data.get("imageUrl").toString(),
                            document.data.get("userId").toString(),
                            document.data.get("timeAdded") as Timestamp,
                            document.data.get("username").toString()
                        )
                        journalList.add(journal)
                    }

                    //Recycler view
                    adapter = JournalRecyclerAdapter(
                        this, journalList
                    )
                    binding.recyclerView.setAdapter(adapter)
                    adapter.run { notifyDataSetChanged() }
                }
                else{
                    binding.fab.visibility = View.VISIBLE
                }
            }.addOnFailureListener{
                Toast.makeText(this,
                    "Error!!",
                    Toast.LENGTH_LONG
                    ).show()
            }
    }


}