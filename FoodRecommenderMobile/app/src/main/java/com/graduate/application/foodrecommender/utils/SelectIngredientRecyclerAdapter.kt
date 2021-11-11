package com.graduate.application.foodrecommender.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.graduate.application.foodrecommender.R
import com.graduate.application.foodrecommender.datas.CheckedIngredient
import com.graduate.application.foodrecommender.datas.Ingredients

class SelectIngredientRecyclerAdapter( val context : Context , var data : ArrayList<CheckedIngredient> )
    : RecyclerView.Adapter< SelectIngredientRecyclerAdapter.Holder>() {

    inner class Holder( itemView : View) : RecyclerView.ViewHolder( itemView ) {
        val ingredientText : TextView = itemView.findViewById( R.id.ingredientText )
        val check : CheckBox = itemView.findViewById( R.id.check )

        fun bind( data : CheckedIngredient , context : Context ) {
            ingredientText.text = data.name

            check.setOnCheckedChangeListener { compoundButton, b ->
                if ( b== true ) {
                    data.checked = true
                }
                if ( b == false ) {
                    data.checked = false
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from( context ).inflate( R.layout.layout_select_ingredient , parent , false )
        return Holder( view )
    }

    override fun onBindViewHolder( holder: Holder, position: Int) {
        holder.bind( data[position] , context )
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun changeVal( str : String , checked : Boolean ) {
        for ( data in data ) {
            if ( data.equals( str ) ) {
                data.checked = checked
                notifyDataSetChanged()
                return
            }
        }
    }

    fun getCheckedData() : ArrayList<String> {
        var ret = arrayListOf<String>()

        for ( data in data ) {
            if (data.checked == true ) {
                ret.add( data.name )
            }
        }

        return ret
    }

}