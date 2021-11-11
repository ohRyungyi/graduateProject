package com.graduate.application.foodrecommender.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.graduate.application.foodrecommender.R

class CheckResultRecyclerAdapter( val context : Context , var data : ArrayList<String> ) : RecyclerView.Adapter< CheckResultRecyclerAdapter.Holder> () {

    inner class Holder( itemView : View ) : RecyclerView.ViewHolder( itemView ) {
        val ingredient : TextView = itemView.findViewById( R.id.ingredientText )
        val modifyBtn : TextView = itemView.findViewById( R.id.modifyBtn )

        fun bind( data : String , context : Context ) {
            ingredient.text = data
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from( context ).inflate( R.layout.layout_check_ingredient_modify , parent , false )
        return Holder( view )
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind( data[position] , context )
    }

    override fun getItemCount(): Int {
        return data.size
    }
}