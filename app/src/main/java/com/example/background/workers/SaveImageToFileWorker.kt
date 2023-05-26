package com.example.background.workers

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.background.KEY_IMAGE_URI
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class SaveImageToFileWorker(ctx: Context, params: WorkerParameters): Worker(ctx,params) {

    val title = "Blurred Image"
    val dateFormatter = SimpleDateFormat(
        "yyyy.MM.dd 'at' HH:mm:ss z",
        Locale.getDefault()
    )
    override fun doWork(): Result {
        makeStatusNotification("Saving Image...",applicationContext)
        sleep()

        val resolver = applicationContext.contentResolver
        return try {
            val resourceUri = inputData.getString(KEY_IMAGE_URI)
            val bitmap = BitmapFactory.decodeStream(resolver.openInputStream(Uri.parse(resourceUri)))
            val imageUri = MediaStore.Images.Media.insertImage(resolver,bitmap,title,dateFormatter.format(
                Date()
            ))

            if (!imageUri.isNullOrEmpty()){
                val output = workDataOf(KEY_IMAGE_URI to imageUri)
                Result.success(output)
            }else Result.failure()
        }catch (e: Exception){Result.failure()}

    }
}