package com.example.test_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.test_app.databinding.ActivityTestBinding

class TestActivity : AppCompatActivity() {

    private lateinit var b : ActivityTestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityTestBinding.inflate(layoutInflater)
        setContentView(b.root)
        b.textView.text = "Test Activity"
        b.button.setOnClickListener(View.OnClickListener { onButtonClick() })
    }

    private fun onButtonClick(){
        finish()
    }
}