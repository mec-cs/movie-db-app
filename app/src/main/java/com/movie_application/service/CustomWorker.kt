package com.movie_application.service


import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.ListenableWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.movie_application.util.NotificationUtil

class CustomWorker(var context: Context, private var workerParams: WorkerParameters):Worker (context, workerParams){
    val tagforLogcat = "WorkerEx"

    override fun doWork(): ListenableWorker.Result {

        val sumOfFavsInitial: Int = inputData.getInt("TotalFavMovie", -1)
        val name: String = inputData.getString("AppName").toString()

        return try {

            val sumOfFavs = sumOfFavsInitial

            //create the output of worker
            val outputData = Data.Builder().putInt("TotalFavMovie", sumOfFavs).build()
            NotificationUtil.sendNotification(context, sumOfFavs.toString() + "")

            Log.d(tagforLogcat, "End of worker")

            ListenableWorker.Result.success(outputData)
        } catch (throwable: Throwable) {

            Log.d(tagforLogcat, "Error Sending Notification" + throwable.message)
            ListenableWorker.Result.failure()
        }
    }
}