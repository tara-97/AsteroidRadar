package com.udacity.asteroidradar.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.LocalDateExt
import com.udacity.asteroidradar.api.*
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.database.asDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.lang.Exception

private const val TAG = "AsteroidRepository"
class AsteroidRepository (private val database:AsteroidDatabase){

    val asteroids: LiveData<List<Asteroid>> = Transformations.map(
        database.asteroidDao.getAsteroids(LocalDateExt.dateNowFormatted())){
            it.asDomainModel()
    }
    suspend fun refreshDatabase(){

        try {
            withContext(Dispatchers.IO){
                val asteroidJsonString = NasaApi.retrofitService.getAsteroids(LocalDateExt.dateNowFormatted(),Constants.apiKey)

                val asteroidOfDatabase = NetworkAsteroidContainer(
                        parseAsteroidsJsonResult(JSONObject(asteroidJsonString))
                ).asDatabaseModel()
                database.asteroidDao.insertAll(*asteroidOfDatabase)
            }
        }catch (e:Exception){
            Log.d(TAG, "refreshDatabase:Got error ->  ${e.message}")
        }
    }
}