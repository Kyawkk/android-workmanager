package com.example.background.workers

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.background.OUTPUT_PATH
import java.io.File
import java.lang.Exception

class CleanUpWorker(ctx: Context, params: WorkerParameters): Worker(ctx,params) {
    override fun doWork(): Result {
        makeStatusNotification("Cleaning up old temporary files...",applicationContext)
        sleep()

        return try {
            val outputDirectory = File(applicationContext.filesDir, OUTPUT_PATH)
            if (outputDirectory.exists()){
                val entries = outputDirectory.listFiles()
                if (entries != null){
                    for (entry in entries){
                        val name = entry.name
                        if (name.isEmpty() && name.endsWith(".png")) entry.delete()
                    }
                }
            }

            Result.success()
        }
        catch (e: Exception){Result.failure()}
    }
}