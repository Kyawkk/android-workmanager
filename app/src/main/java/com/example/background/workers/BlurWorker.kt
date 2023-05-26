package com.example.background.workers

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.text.TextUtils
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.background.KEY_IMAGE_URI
import com.example.background.R
import java.lang.Exception
import java.lang.IllegalArgumentException

class BlurWorker(ctx: Context, params: WorkerParameters): Worker(ctx,params) {
    override fun doWork(): Result {
        makeStatusNotification("Blurring image",applicationContext)

        sleep()

        val resourceUri = inputData.getString(KEY_IMAGE_URI)

        return try {
            if (TextUtils.isEmpty(resourceUri)) {
                Log.d("TAG", "Invalid input uri")
                throw IllegalArgumentException("Invalid input uri")
            }

            val resolver = applicationContext.contentResolver
            val picture = BitmapFactory.decodeStream(resolver.openInputStream(Uri.parse(resourceUri)))

            val output = blurBitmap(picture,applicationContext)
            val outputUri = writeBitmapToFile(applicationContext,output)

            makeStatusNotification("Output is $outputUri",applicationContext)

            val outputData = workDataOf(KEY_IMAGE_URI to outputUri.toString())

            Result.success(outputData)
        }catch (e: Exception){
            Result.failure()
        }
    }
}