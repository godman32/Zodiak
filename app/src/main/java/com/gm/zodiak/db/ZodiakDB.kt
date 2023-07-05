package com.gm.zodiak.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.gm.zodiak.utils.subscribeOnBackground
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.reflect.KParameter

@Database(entities = [Zodiak::class], version = 1)
abstract class ZodiakDB: RoomDatabase() {

    abstract fun zodiaDAO(): ZodiakDAO

    companion object {
        private var instance: ZodiakDB? = null

        @Synchronized
        fun getInstance(ctx: Context): ZodiakDB {
            if(instance == null)
                instance = Room.databaseBuilder(ctx.applicationContext, ZodiakDB::class.java,
                    "zodiak_db")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build()

            return instance!!
        }

        private val roomCallback = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                populateDatabase(instance!!)
            }
        }

        private fun populateDatabase(db: ZodiakDB) {
            val zodiakDao = db.zodiaDAO()
            subscribeOnBackground {
                zodiakDao.insert(Zodiak("21 Jan","19 Feb", "Aquarius"))
                zodiakDao.insert(Zodiak("20 Feb","21 Mar", "Pisces"))
                zodiakDao.insert(Zodiak("22 Mar","21 Apr", "Aries"))
                zodiakDao.insert(Zodiak("22 Apr","21 May", "Taurus"))
                zodiakDao.insert(Zodiak("22 May","21 Jun", "Gemini"))
                zodiakDao.insert(Zodiak("22 Jun","21 Jul", "Cancer"))

                zodiakDao.insert(Zodiak("22 Jul","22 Aug", "Leo"))
                zodiakDao.insert(Zodiak("23 Aug","23 Sep", "Virgo"))
                zodiakDao.insert(Zodiak("24 Sep","23 Oct", "Libra"))
                zodiakDao.insert(Zodiak("24 Oct","23 Nov", "Scorpio"))
                zodiakDao.insert(Zodiak("23 Nov","21 Dec", "Sagitarius"))
                zodiakDao.insert(Zodiak("22 Dec","20 Jan", "Capricorn"))
            }
        }
    }



}