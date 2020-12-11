package com.udacity.asteroidradar.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.udacity.asteroidradar.Asteroid

@Entity(tableName = "asteroids")
data class AsteroidEntity constructor(
        @PrimaryKey
        val id: Long,
        val codename: String, val closeApproachDate: String,
        val absoluteMagnitude: Double, val estimatedDiameter: Double,
        val relativeVelocity: Double, val distanceFromEarth: Double,
        val isPotentiallyHazardous: Int
)

fun List<AsteroidEntity>.asDomainModel() : List<Asteroid>{
    return map {
        Asteroid(
                it.id,
                it.codename,
                it.closeApproachDate,
                it.absoluteMagnitude,
                it.estimatedDiameter,
                it.relativeVelocity,
                it.distanceFromEarth,
                it.isPotentiallyHazardous == 1
        )

    }
}