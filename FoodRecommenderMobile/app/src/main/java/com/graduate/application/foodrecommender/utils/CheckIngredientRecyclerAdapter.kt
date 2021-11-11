package com.graduate.application.foodrecommender.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.graduate.application.foodrecommender.R
import com.graduate.application.foodrecommender.datas.Ingredients

class CheckIngredientRecyclerAdapter( val context : Context , var data : ArrayList<String> )
    : RecyclerView.Adapter< CheckIngredientRecyclerAdapter.Holder>() {

        inner class Holder( itemView : View) : RecyclerView.ViewHolder( itemView ) {
            val ingredientText : TextView = itemView.findViewById( R.id.ingredientText )
            val addDateText : TextView = itemView.findViewById( R.id.addDateText )
            val noExistBtn : TextView = itemView.findViewById( R.id.noExistBtn )

            fun bind( data : String , context : Context ) {
                ingredientText.text = data

                noExistBtn.setOnClickListener {
                    removeItem( data )
                }
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from( context ).inflate( R.layout.layout_check_ingredient , parent , false )
        return Holder( view )
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind( data[position] , context )
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun removeItem( remove : String ){
        if ( data.contains( remove ) ) {
            data.remove( remove )
            notifyDataSetChanged()
        }
    }

    @JvmName("getData1")
    fun getData( ) : ArrayList<String> {
        return data
    }
}