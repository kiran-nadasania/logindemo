package com.promptsoftech.logindemo.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class UserEntity(@PrimaryKey(autoGenerate = false)val userId:String,
                      @ColumnInfo(name = "userName")val userName:String,
                      @ColumnInfo(name = "xacc")val xacc:String) {
}