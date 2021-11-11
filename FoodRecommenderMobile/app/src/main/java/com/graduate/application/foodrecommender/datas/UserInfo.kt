package com.graduate.application.foodrecommender.datas

import android.util.Log
import java.io.Serializable

class UserInfo(id: String, pw: String, token: String, ingredients: ArrayList<String>) : Serializable {

    var id : String = ""
    var pw : String = ""
    var token : String = ""
    var ingredients : ArrayList<String> = ArrayList()

    init {
        this.id = id
        this.pw = pw
        this.token = token
        this.ingredients = ingredients
    }

    fun addIngredients( lists : ArrayList<String> ) : Boolean {
        var addString : String = ""

        for( item in lists ) {
            if ( ! this.ingredients.contains(item) ) {
                ingredients.add( item )
                addString += item+" "
            }
        }

        Log.d( "INGREDIENTS" , addString+"items are added.")
        return true
    }

    fun removeIngredients( lists : ArrayList<String> ) : Boolean {
        var removeString : String = ""

        for ( item in lists ) {
            if ( ! this.ingredients.contains(item) ) {
                ingredients.remove( item )
                removeString += item + " "
            }
        }

        Log.d( "INGREDIENTS" , removeString + "itmes are removed.")
        return true
    }

    fun printUser() : String {
        var ret : String = ""
        ret += "id : "+id.toString()+ " , ingredients list : "
        for ( ingredient in ingredients ) {
            ret += ingredient + " "
        }

        return ret
    }
}