package com.graduate.application.foodrecommender.datas

import android.graphics.Bitmap
import java.io.IOException

class Recipe( var images : ArrayList<Bitmap> , var descriptions : ArrayList<String> , var steps : Int ) {

    var image : ArrayList<Bitmap> = ArrayList()
    var description : ArrayList<String> = ArrayList()
    var step : Int = 0

    init {
        this.step = steps

        for( iamge in images ) {
            this.image.add( iamge )
        }
        for ( descript in descriptions ) {
            this.description.add( descript )
        }

    }

    fun getNth( order : Int ) : Pair<Bitmap , String> {
        if ( order >= step ) {
            var ret : Pair<Bitmap , String> = Pair<Bitmap , String >( this.images[ step -1 ] , this.description[ step - 1 ] )
            return ret
        }
        var ret : Pair<Bitmap , String> = Pair<Bitmap , String >( this.images[ order ] , this.description[ order ] )
        return ret
    }
}