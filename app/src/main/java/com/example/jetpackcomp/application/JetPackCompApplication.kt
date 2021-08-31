package com.example.jetpackcomp.application

import android.app.Application
import com.example.jetpackcomp.Model.databse.JetPackCompDatabase
import com.example.jetpackcomp.Model.databse.JetpackCompRepo

class JetPackCompApplication : Application() {

    private val database by lazy { JetPackCompDatabase.getDatabase(this@JetPackCompApplication)}

    val repository by lazy { JetpackCompRepo(database.JetpackDao())}
}