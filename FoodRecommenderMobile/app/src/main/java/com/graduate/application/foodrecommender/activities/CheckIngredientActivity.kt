package com.graduate.application.foodrecommender.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.graduate.application.foodrecommender.R
import com.graduate.application.foodrecommender.datas.Ingredients
import com.graduate.application.foodrecommender.datas.UserInfo
import com.graduate.application.foodrecommender.utils.CheckIngredientRecyclerAdapter
import com.graduate.application.foodrecommender.utils.ShowIngredientRecyclerAdapter

class CheckIngredientActivity : AppCompatActivity() {

    var ingredients : ArrayList<String> = arrayListOf()
    lateinit var adapter : CheckIngredientRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_ingredient)

        var user = intent.getSerializableExtra( "user" ) as UserInfo

        var confirmBtn : TextView = findViewById( R.id.confirmBtn )
        var recyclerView : RecyclerView = findViewById( R.id.ingredientList )

        adapter = CheckIngredientRecyclerAdapter( this , user.ingredients )
        recyclerView.adapter = adapter

        val lm : LinearLayoutManager = LinearLayoutManager( this )
        recyclerView.layoutManager = lm
        recyclerView.setHasFixedSize( true )

        confirmBtn.setOnClickListener {
            var intent = Intent( this , MenuActivity::class.java )
            user.ingredients = adapter.getData()
            Log.d( "SHOW_INGREDIENT_ACTIVITY" , user.printUser() )
            intent.putExtra( "user" , user )
            startActivity( intent )
        }

    }
}