package com.example.ff.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.ff.model.FridgeItem

@Database(entities = [FridgeItem::class], version = 1, exportSchema = false)
abstract class FridgeDB : RoomDatabase()  {

    abstract fun fridgeDao(): FridgeDao

    companion object{
        @Volatile
        private var INSTANCE: FridgeDB? = null

        fun getDatabase(context: Context): FridgeDB {
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FridgeDB::class.java,
                    "fridge_database"
                ).build()
                INSTANCE = instance
                return instance

            }

        }
    }
}