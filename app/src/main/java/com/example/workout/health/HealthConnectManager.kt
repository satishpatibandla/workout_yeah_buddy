package com.example.workout.health

import android.content.Context
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.permission.HealthPermission
import androidx.health.connect.client.records.StepsRecord
import androidx.health.connect.client.request.ReadRecordsRequest
import androidx.health.connect.client.time.TimeRangeFilter
import java.time.Instant
import java.time.temporal.ChronoUnit

class HealthConnectManager(private val context: Context) {
    
    private val healthConnectClient by lazy {
        HealthConnectClient.getOrCreate(context)
    }
    
    // Permissions needed
    val permissions = setOf(
        HealthPermission.getReadPermission(StepsRecord::class)
    )
    
    // Check if Health Connect is available
    fun isAvailable(): Boolean {
        return try {
            HealthConnectClient.getOrCreate(context)
            true
        } catch (e: Exception) {
            false
        }
    }
    
    // Read today's step count
    suspend fun getTodaySteps(): Long {
        return try {
            val now = Instant.now()
            val startOfDay = now.truncatedTo(ChronoUnit.DAYS)
            
            val response = healthConnectClient.readRecords(
                ReadRecordsRequest(
                    StepsRecord::class,
                    timeRangeFilter = TimeRangeFilter.between(startOfDay, now)
                )
            )
            
            response.records.sumOf { it.count }
        } catch (e: Exception) {
            0L // Return 0 if error
        }
    }
    
    // Read steps for a date range
    suspend fun getStepsInRange(startTime: Instant, endTime: Instant): Long {
        return try {
            val response = healthConnectClient.readRecords(
                ReadRecordsRequest(
                    StepsRecord::class,
                    timeRangeFilter = TimeRangeFilter.between(startTime, endTime)
                )
            )
            
            response.records.sumOf { it.count }
        } catch (e: Exception) {
            0L
        }
    }
}
