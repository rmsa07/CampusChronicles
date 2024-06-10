package com.example.journalapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.journalapp.databinding.JournalRowBinding
import com.squareup.picasso.Picasso


class JournalRecyclerAdapter( val context : Context, val journalList: List<Jounal>)
    : RecyclerView.Adapter<JournalRecyclerAdapter.MyViewHolder>() {
    lateinit var img:ImageView

     lateinit var binding:JournalRowBinding

        //view Holder
         class MyViewHolder(var binding: JournalRowBinding)
            :RecyclerView.ViewHolder(binding.root){

                fun bind(journal:Jounal){
                    binding.journal =journal
                }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
//        val view:View = LayoutInflater.from(context)
//            .inflate(R.layout.journal_row,parent,false)
        binding = JournalRowBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )


        return MyViewHolder(binding)

    }

    override fun getItemCount(): Int = journalList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val jounal:Jounal = journalList[position]
        holder.bind(jounal)


        //set image in recycle view using picasso

        var imageurl = jounal.imageUrl.toString()
        img = binding.journalImageList

        Picasso
            .get()
            .load(imageurl)
            .noFade()
            .into(img);


    }

}

//private fun Any.into(s: String) {
//
//}
