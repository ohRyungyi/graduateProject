package com.graduate.application.foodrecommender.activities

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.graduate.application.foodrecommender.R
import com.graduate.application.foodrecommender.datas.UserInfo
import com.graduate.application.foodrecommender.utils.AddIngredientRecyclerAdapter
import java.io.File

class AddIngredientActivity : AppCompatActivity() {

    var images : ArrayList<Bitmap> = arrayListOf()
    private val REQUEST_IMAGE_CAPTER : Int = 100
    lateinit var adapter : AddIngredientRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_ingredient)

        var user = intent.getSerializableExtra( "user" ) as UserInfo

        adapter = AddIngredientRecyclerAdapter( this , images )
        var imageRecyclerView : RecyclerView = findViewById( R.id.images )
        imageRecyclerView.adapter = adapter

        val gm : GridLayoutManager = GridLayoutManager( this ,3 )
        imageRecyclerView.layoutManager = gm
        imageRecyclerView.setHasFixedSize( true )

        var camera : TextView = findViewById( R.id.cameraBtn )
        var gallery : TextView = findViewById( R.id.galleryBtn )
        var runModel : TextView = findViewById( R.id.runModelBtn )

        camera.setOnClickListener {
            sendTakePhotoIntent()
        }

        gallery.setOnClickListener {
            var drawable : Drawable? = getDrawable( R.drawable.leek99 )
            var drawableBitamp : BitmapDrawable = drawable as BitmapDrawable
            var bitmap : Bitmap = drawableBitamp.bitmap

            images.add( bitmap )
            adapter.notifyDataSetChanged()
        }

        runModel.setOnClickListener {
            var intent = Intent( this , CheckResultActivity::class.java)
//            intent.putExtra("images" , images )
            Log.d( "ADD_INGREDIENT_ACTIVITY" , images.size.toString() )
            intent.putExtra( "ingredient" , arrayListOf( "파") )
            intent.putExtra( "user" , user )

            Log.d( "ADD_INGREDIENT_ACTIVITY" , user.printUser() )
            startActivity( intent )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if( requestCode == REQUEST_IMAGE_CAPTER && resultCode == RESULT_OK ) {
            var extras : Bundle = data!!.extras!!
            var bitmap : Bitmap = extras.get("data") as Bitmap
            images.add( bitmap )
            adapter.notifyDataSetChanged()
        }
    }

    fun sendTakePhotoIntent() {
        var takePictureIntent : Intent = Intent( MediaStore.ACTION_IMAGE_CAPTURE )
        if ( takePictureIntent.resolveActivity( packageManager ) != null ) {
            startActivityForResult( takePictureIntent , REQUEST_IMAGE_CAPTER )
        }
    }
}