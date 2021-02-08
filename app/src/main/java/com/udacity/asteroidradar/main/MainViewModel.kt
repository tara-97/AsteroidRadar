package com.udacity.asteroidradar.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.PictureOfDay

import com.udacity.asteroidradar.api.NasaApi
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.database.getDataBase
import com.udacity.asteroidradar.repository.AsteroidRepository
import kotlinx.coroutines.launch
import org.json.JSONObject

private const val TAG = "MainViewModel"

class MainViewModel(application:Application) : AndroidViewModel(application) {

    private val database = getDataBase(application)
    private val asteroidRepo = AsteroidRepository(database)

    private val _pictureOfDay = MutableLiveData<PictureOfDay>()
    val pictureOfDay:LiveData<PictureOfDay>
    get() = _pictureOfDay

    private var _navigateToDetailPage = MutableLiveData<Asteroid>()
    val navigateToDetailPage : LiveData<Asteroid>
    get() = _navigateToDetailPage


    init {
        viewModelScope.launch {
            asteroidRepo.refreshDatabase()
            _pictureOfDay.value = NasaApi.retrofitService.getPictureOfDay(Constants.apiKey)
            Log.d(TAG, "Picture of day url is ${pictureOfDay.value!!.url}")
        }

    }
    val asteroids = asteroidRepo.asteroids


    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
    fun choseAsteroidNavigate(asteroid: Asteroid){
        _navigateToDetailPage.value = asteroid
    }
    fun doneNavigatingToDetailPage(){
        _navigateToDetailPage.value = null
    }



}