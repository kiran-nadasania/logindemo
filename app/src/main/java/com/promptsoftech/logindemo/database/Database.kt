package com.promptsoftech.logindemo.database

import android.content.Context
import android.service.autofill.UserData
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.promptsoftech.logindemo.database.dao.UserDAO
import com.promptsoftech.logindemo.database.entity.UserEntity
import kotlinx.coroutines.CoroutineScope

@Database(entities = arrayOf(UserEntity::class),version = 1, exportSchema = false)
public abstract class Database : RoomDatabase(){
    abstract fun userDao(): UserDAO

    companion object {
        //For Singleton
        @Volatile
        private var INSTANCE: com.promptsoftech.logindemo.database.Database? = null

        fun getDatabase(context: Context): com.promptsoftech.logindemo.database.Database {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    com.promptsoftech.logindemo.database.Database::class.java,
                    "user_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}