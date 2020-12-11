package com.udacity.asteroidradar.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.database.getDataBase
import com.udacity.asteroidradar.repository.AsteroidRepository
import java.lang.Exception

class AsteroidRefresherWorker(private val context: Context, params:WorkerParameters):CoroutineWorker(context,params){
    companion object{
        const val WORK_NAME = "RefreshAsteroid"
    }
    override suspend fun doWork(): Result {
        val database = getDataBase(context)
        val repository = AsteroidRepository(database)

        return try {
            repository.refreshDatabase()
            Result.success()
        }catch (e:Exception){
            Result.retry()
        }
    }

}