package com.udacity.asteroidradar

import android.app.Application
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.udacity.asteroidradar.work.AsteroidRefresherWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class ApplicationWorker : Application(){
    private val applicationScope = CoroutineScope(Dispatchers.Default)
    override fun onCreate() {
        super.onCreate()
        delayInit()
    }
    private fun delayInit(){
        applicationScope.launch {
            setUpRecurringWork()
        }
    }
    private  fun setUpRecurringWork(){
        val repeatingWork = PeriodicWorkRequestBuilder<AsteroidRefresherWorker>(1,TimeUnit.DAYS).build()

        WorkManager.getInstance().enqueueUniquePeriodicWork(
                AsteroidRefresherWorker.WORK_NAME,
                ExistingPeriodicWorkPolicy.KEEP,
                repeatingWork
        )
    }
}