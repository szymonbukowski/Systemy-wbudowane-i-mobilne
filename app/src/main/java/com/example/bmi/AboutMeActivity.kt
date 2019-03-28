package com.example.bmi

import android.content.ComponentCallbacks2
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.about_me_activity.*

class AboutMeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.about_me_activity)
        ame_returnB.setOnClickListener{ Toast.makeText(this,"Button clicked", Toast.LENGTH_SHORT)}
    }


}
