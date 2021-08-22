package com.example.jetpackcomp.Model.databse

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.jetpackcomp.Model.entities.JetpackComp


@Database(entities = [JetpackComp::class], version = 1)
abstract class JetPackCompDatabase : RoomDatabase() {

    abstract fun JetpackDao(): JetpackDao

    companion object{
        @Volatile
        private var INSTANCE : JetPackCompDatabase? = null

        fun getDatabase(context : Context): JetPackCompDatabase{

            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    JetPackCompDatabase::class.java,
                    "Jetpack_app_comp"
                ).fallbackToDestructiveMigration().build()

                INSTANCE = instance
                instance
            }
        }
    }
}