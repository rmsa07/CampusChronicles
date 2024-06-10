package com.example.journalapp

import com.google.firebase.Timestamp


data class Jounal(
      val title:String,
      val thoughts:String,
      val imageUrl:String,

      val userId:String,
      val timeAdded:Timestamp,
      val username:String
)
