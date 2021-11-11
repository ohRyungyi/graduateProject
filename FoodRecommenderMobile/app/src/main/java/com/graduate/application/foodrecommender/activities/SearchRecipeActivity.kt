package com.graduate.application.foodrecommender.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.graduate.application.foodrecommender.R
import com.graduate.application.foodrecommender.datas.CheckedIngredient
import com.graduate.application.foodrecommender.datas.SearchedRecipeList
import com.graduate.application.foodrecommender.datas.UserInfo
import com.graduate.application.foodrecommender.utils.SearchRecipeRecyclerAdapter
import com.graduate.application.foodrecommender.utils.SelectIngredientRecyclerAdapter

class SearchRecipeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_recipe)

        var user = intent.getSerializableExtra( "user" ) as UserInfo

        var radioGroup : RadioGroup = findViewById( R.id.typeGroup )
        var recyclerView : RecyclerView = findViewById( R.id.recipeListView )
        var searchBtn : TextView = findViewById( R.id.searchBtn )
        var recipe : RadioButton = findViewById( R.id.recipe )
        var ingredient : RadioButton = findViewById( R.id.ingredients )
        var searchword : EditText = findViewById( R.id.searchWord )

        radioGroup.setOnCheckedChangeListener { radioGroup, i ->
            if ( i == R.id.recipe ) {
                searchword.visibility = View.VISIBLE
                var data : ArrayList<SearchedRecipeList> = arrayListOf()

                var adapter : SearchRecipeRecyclerAdapter = SearchRecipeRecyclerAdapter( this , data )
                recyclerView.adapter = adapter

                val lm : LinearLayoutManager = LinearLayoutManager( this )
                recyclerView.layoutManager = lm
                recyclerView.setHasFixedSize( true )
            }
            if ( i == R.id.ingredients ) {
                searchword.visibility = View.GONE
                var data : ArrayList<CheckedIngredient > = arrayListOf()

                for ( ingredient in user.ingredients ) {
                    data.add( CheckedIngredient( ingredient , false ))
                }
                var adapter : SelectIngredientRecyclerAdapter = SelectIngredientRecyclerAdapter( this , data )
                recyclerView.adapter = adapter

                val lm : LinearLayoutManager = LinearLayoutManager( this )
                recyclerView.layoutManager = lm
                recyclerView.setHasFixedSize( true)
            }
        }

        searchBtn.setOnClickListener {
            if ( recipe.isChecked ) {
                var data : ArrayList<SearchedRecipeList> = arrayListOf()
                data.add( SearchedRecipeList( "파기름" , arrayListOf("파") , arrayListOf("기름") ))

                var adapter : SearchRecipeRecyclerAdapter = SearchRecipeRecyclerAdapter( this , data )
                recyclerView.adapter = adapter

                val lm : LinearLayoutManager = LinearLayoutManager( this )
                recyclerView.layoutManager = lm
                recyclerView.setHasFixedSize( true )
            }
            else if ( ingredient.isChecked ) {
                var data : ArrayList<SearchedRecipeList> = arrayListOf()
                data.add( SearchedRecipeList( "파기름" , arrayListOf("파") , arrayListOf("기름") ))
                data.add( SearchedRecipeList( "파간장" , arrayListOf("파") , arrayListOf("간장") ))
                data.add( SearchedRecipeList( "파전병" , arrayListOf("파") , arrayListOf("밀가루" , "소금" , "참기름" , "베이컨" , "기름") ))

                var adapter : SearchRecipeRecyclerAdapter = SearchRecipeRecyclerAdapter( this , data )
                recyclerView.adapter = adapter

                val lm : LinearLayoutManager = LinearLayoutManager( this )
                recyclerView.layoutManager = lm
                recyclerView.setHasFixedSize( true )
            }
        }
    }
}