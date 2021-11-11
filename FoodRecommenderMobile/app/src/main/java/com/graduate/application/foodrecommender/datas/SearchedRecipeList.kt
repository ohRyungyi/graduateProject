package com.graduate.application.foodrecommender.datas

class SearchedRecipeList( name : String , already : ArrayList<String> , non : ArrayList<String> ) {

    var name : String = ""
    var alreadys : ArrayList<String> = ArrayList()
    var nons : ArrayList<String> = ArrayList()

    init {
        this.name = name
        this.alreadys = already
        this.nons = non
    }

    fun alreadysToString() :String {
        var ret : String = ""

        for( already in alreadys ) {
            ret += already + " "
        }
        return ret
    }

    fun nonsToString() : String {
        var ret : String = ""

        for ( non in nons ) {
            ret +=  non + " "
        }
        return ret
    }
}