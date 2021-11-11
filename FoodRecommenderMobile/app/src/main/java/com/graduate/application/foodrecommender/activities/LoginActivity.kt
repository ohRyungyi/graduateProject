package com.graduate.application.foodrecommender.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.graduate.application.foodrecommender.R
import com.graduate.application.foodrecommender.datas.UserInfo

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        var id : TextView = findViewById( R.id.idText )
        var pw : TextView = findViewById( R.id.pwText )

        var login : TextView = findViewById( R.id.loginBtn )
        var signup : TextView = findViewById( R.id.signUpBtn )
        var findID : TextView = findViewById( R.id.findIdBtn )
        var findPW : TextView = findViewById( R.id.findPwBtn )

        login.setOnClickListener {
            var user = checkValid( id.text.toString() , pw.text.toString() )
            if ( user != null ) {
                var intent = Intent( this , MenuActivity::class.java )
                intent.putExtra( "user" , user )
                startActivity( intent )
            }
        }


    }

    fun checkValid( id : String , pw : String ) : UserInfo {
        // 계정 유효성 검사 함수
        var ingredients : ArrayList<String> = ArrayList()
        ingredients.add("감자")
//        ingredients.add("파")
        ingredients.add("오이")
        var user : UserInfo = UserInfo( id , pw , "" , ingredients )
        return user
    }
}