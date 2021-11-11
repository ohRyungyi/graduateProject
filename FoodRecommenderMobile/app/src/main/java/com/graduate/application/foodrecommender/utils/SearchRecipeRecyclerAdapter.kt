package com.graduate.application.foodrecommender.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.graduate.application.foodrecommender.R
import com.graduate.application.foodrecommender.datas.SearchedRecipeList

class SearchRecipeRecyclerAdapter( val context : Context , val data : ArrayList<SearchedRecipeList> ) : RecyclerView.Adapter<SearchRecipeRecyclerAdapter.Holder>() {

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val recipeName : TextView = itemView.findViewById( R.id.recipeNameText )
        val alreadys : TextView = itemView.findViewById( R.id.alreadyIngredientText )
        val nons : TextView = itemView.findViewById( R.id.nonIngredientText )

        fun bind( data : SearchedRecipeList , context: Context ) {
            recipeName.text = data.name
            alreadys.text = data.alreadysToString()
            nons.text = data.nonsToString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from( context ).inflate( R.layout.layout_searched_recipe_list , parent , false )
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind( data[position] , context )
    }

    override fun getItemCount(): Int {
        return data.size
    }
}