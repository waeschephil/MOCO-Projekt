package com.example.test_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.test_app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var b : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityMainBinding.inflate(layoutInflater)
        setContentView(b.root)
        b.textView1.text = "Cunt :D"
        b.button1.setOnClickListener { onButton1Click() }
        b.mapButton.setOnClickListener { openMap() }
        b.camButton.setOnClickListener { startActivity(Intent(this, Camera::class.java)) }
    }

    private fun onButton1Click(){
        b.textView1.text = "Test ;D"
        openTestActivity()
    }

    private fun openTestActivity(){
        val intent = Intent(this, TestActivity::class.java)
        startActivity(intent)
    }

    private fun openMap(){
        startActivity(Intent(this, TestMapsActivity::class.java))
    }
}