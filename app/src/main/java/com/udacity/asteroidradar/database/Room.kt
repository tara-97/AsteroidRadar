package com.udacity.asteroidradar.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface AsteroidDao {
    @Query("SELECT * FROM asteroids")
    fun getAsteroids() : LiveData<List<AsteroidEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg asteroids:AsteroidEntity)
}

@Database(entities = [AsteroidEntity::class], version = 1,exportSchema = false)
abstract class AsteroidDatabase:RoomDatabase(){
    abstract val asteroidDao : AsteroidDao
}

private lateinit var INSTANCE : AsteroidDatabase

fun getDataBase(context: Context) : AsteroidDatabase{
    synchronized(AsteroidDatabase::class.java){
        if(!::INSTANCE.isInitialized){
            INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    AsteroidDatabase::class.java,
                    "asteroids").build()
        }
        return INSTANCE
    }
}