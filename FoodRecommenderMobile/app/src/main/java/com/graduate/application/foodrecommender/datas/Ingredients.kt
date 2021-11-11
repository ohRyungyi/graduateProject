package com.graduate.application.foodrecommender.datas

import java.util.*

class Ingredients( name : String , date : Date ) {

    var nameStr : String = ""
    var dateStr : String = ""

    init {
        this.nameStr = name
        this.dateStr = dateToString( date )
    }

    fun dateToString( date : Date ) : String {
        return date.year.toString()+"." + String.format( "%02d" , date.month.toString() ) + "." + String.format( "%02d" , date.day.toString() )
    }
}