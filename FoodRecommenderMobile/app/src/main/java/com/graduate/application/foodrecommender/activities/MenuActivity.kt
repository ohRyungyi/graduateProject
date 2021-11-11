package com.graduate.application.foodrecommender.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.graduate.application.foodrecommender.R
import com.graduate.application.foodrecommender.datas.UserInfo

class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        var addIngredient : TextView = findViewById( R.id.addIngredientBtn )
        var searchRecipe : TextView = findViewById( R.id.searchRecipeBtn )
        var checkIngredient : TextView = findViewById( R.id.checkIngredientBtn )

        var user = intent.getSerializableExtra( "user" ) as UserInfo

        Log.d( "MENU" , user.printUser() )

        addIngredient.setOnClickListener {
            var intent = Intent( this , AddIngredientActivity::class.java )
            intent.putExtra( "user" , user )
            startActivity( intent )
        }
        searchRecipe.setOnClickListener {
            var intent = Intent( this , SearchRecipeActivity::class.java )
            intent.putExtra( "user" , user )
            startActivity( intent )
        }
        checkIngredient.setOnClickListener {
            var intent = Intent( this , CheckIngredientActivity::class.java )
            intent.putExtra( "user" , user )
            startActivity( intent )
        }

    }
}