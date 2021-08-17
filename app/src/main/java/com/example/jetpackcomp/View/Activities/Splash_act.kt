package com.example.jetpackcomp.View.Activities

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowInsets
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.example.jetpackcomp.R
import com.example.jetpackcomp.databinding.ActivitySplash2Binding

class Splash_act : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val splash2Binding: ActivitySplash2Binding = ActivitySplash2Binding.inflate(layoutInflater)
        setContentView(splash2Binding.root)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        }else{
            @Suppress("DEPRECATION")
            window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }

        val splash2Animation = AnimationUtils.loadAnimation(this, R.anim.anim_splash)
        splash2Binding.tvAppName.animation = splash2Animation

        splash2Animation.setAnimationListener(object : Animation.AnimationListener{
            override fun onAnimationStart(animation: Animation?) {
//                TODO("Not yet implemented")
            }

            override fun onAnimationEnd(animation: Animation?) {
//                TODO("Not yet implemented")
                Handler(Looper.getMainLooper()).postDelayed({
                    startActivity(Intent(this@Splash_act, MainActivity::class.java))
                    finish()
                },1000)
            }

            override fun onAnimationRepeat(animation: Animation?) {
//                TODO("Not yet implemented")
            }

        })

    }
}