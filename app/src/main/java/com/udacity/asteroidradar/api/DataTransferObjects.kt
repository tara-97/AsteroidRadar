package com.udacity.asteroidradar.api


import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.database.AsteroidEntity



data class NetworkAsteroidContainer(val asteroids:List<Asteroid>)

fun NetworkAsteroidContainer.asDatabaseModel() : Array<AsteroidEntity>{
    return asteroids.map {
        AsteroidEntity(
                it.id,
                it.codename,
                it.closeApproachDate,
                it.absoluteMagnitude,
                it.estimatedDiameter,
                it.relativeVelocity,
                it.distanceFromEarth,
                if (it.isPotentiallyHazardous) 1 else 0
        )
    }.toTypedArray()

}