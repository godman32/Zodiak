package com.gm.zodiak.db

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ZodiakDAO {

    @Insert
    fun insert(zodiak: Zodiak)

    @Update
    fun update(zodiak: Zodiak)

    @Delete
    fun delete(zodiak: Zodiak)

    @Query("delete from zodiak_tb")
    fun deleteAll()

    @Query("select * from zodiak_tb")
    fun getAll(): LiveData<List<Zodiak>>
}