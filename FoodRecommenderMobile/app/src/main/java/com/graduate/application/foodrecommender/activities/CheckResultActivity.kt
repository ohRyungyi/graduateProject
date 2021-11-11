package com.graduate.application.foodrecommender.activities

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.graduate.application.foodrecommender.R
import com.graduate.application.foodrecommender.datas.UserInfo
import com.graduate.application.foodrecommender.utils.CheckResultRecyclerAdapter

class CheckResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_result)

        var intent = intent
//        var images = intent.getBundleExtra( "images" ) as ArrayList<Bitmap>
//        var ingredient = intent.getBundleExtra( "ingredient") as ArrayList<String>
        var user : UserInfo = intent.getSerializableExtra( "user" ) as UserInfo

        var drawable : Drawable? = getDrawable( R.drawable.leek99 )
        var drawableBitamp : BitmapDrawable = drawable as BitmapDrawable
        var bitmap : Bitmap = drawableBitamp.bitmap

        user.addIngredients( arrayListOf("파") )

        var image : ImageView = findViewById( R.id.image )
        image.setImageBitmap( bitmap )

        var adapter = CheckResultRecyclerAdapter( this , arrayListOf("파") )
        var stringRecyclerView : RecyclerView = findViewById( R.id.checkResultRecycler )
        stringRecyclerView.adapter = adapter

        val lm : LinearLayoutManager = LinearLayoutManager( this )
        stringRecyclerView.layoutManager = lm
        stringRecyclerView.setHasFixedSize( true )

        var finishModifyBtn : TextView = findViewById( R.id.finishModifyBtn )
        finishModifyBtn.setOnClickListener {
            var intent = Intent( this , ShowIngredientListActivity::class.java )
            intent.putExtra( "user" , user)
            startActivity( intent )
        }

    }
}