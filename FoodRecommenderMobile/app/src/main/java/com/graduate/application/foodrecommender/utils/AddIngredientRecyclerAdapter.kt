package com.graduate.application.foodrecommender.utils

import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.graduate.application.foodrecommender.R

class AddIngredientRecyclerAdapter( val context : Context , var data : ArrayList<Bitmap> ) : RecyclerView.Adapter< AddIngredientRecyclerAdapter.Holder >() {

    inner class Holder( itemView : View ) : RecyclerView.ViewHolder( itemView ){
        val imageView : ImageView = itemView.findViewById( R.id.imageView )

        fun bind( data : Bitmap , context : Context ) {
            imageView.setImageBitmap( data )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from( context ).inflate( R.layout.layout_add_ingredient , parent , false )
        return Holder( view )
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind( data[position] , context )
    }

    override fun getItemCount(): Int {
        return data.size
    }

}