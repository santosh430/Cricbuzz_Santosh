package com.example.cricbuzz_santosh.data.dbhelper

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.format.DateTimeFormatter

@Entity(tableName = "player_table")
data class Player(

    @PrimaryKey(autoGenerate = true)
    var id:Int?=null,
    var name:String?,
    var team:String?,
    var country:String?,
    val profilePhoto: ByteArray?,
    var dob:String?,
    var style:String?,
    var matches:Int?,
    var runs:Int?,
    var wickets:Int?,
    var fav:Int?
)
