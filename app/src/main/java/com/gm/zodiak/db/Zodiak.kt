package com.gm.zodiak.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "zodiak_tb")
data class Zodiak (
    val start:String,
    val end: String,
    @PrimaryKey val name: String )

