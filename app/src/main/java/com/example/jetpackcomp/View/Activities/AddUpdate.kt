package com.example.jetpackcomp.View.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.jetpackcomp.R
import com.example.jetpackcomp.databinding.ActivityAddUpdateBinding

class AddUpdate : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val addUpdate : ActivityAddUpdateBinding = ActivityAddUpdateBinding.inflate(layoutInflater)
        setContentView(addUpdate.root)
    }
}