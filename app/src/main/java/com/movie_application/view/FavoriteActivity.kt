package com.movie_application.view

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.core.view.GestureDetectorCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.movie_application.adapter.FavMovieItemDecoration
import com.movie_application.adapter.FavMovieRecyclerViewAdapter
import com.movie_application.database.FavoriteMovieSys
import com.movie_application.database.Movie
import com.movie_application.databinding.ActivityFavoriteBinding
import com.movie_application.service.CustomWorker
import java.util.concurrent.TimeUnit
import kotlin.math.abs

class FavoriteActivity : AppCompatActivity(), FavMovieRecyclerViewAdapter.FavRecyclerViewInterface {

    private lateinit var favBinding: ActivityFavoriteBinding
    lateinit var favAdapter: FavMovieRecyclerViewAdapter
    var gDetector: GestureDetectorCompat? = null
    lateinit var workManager: WorkManager
    lateinit var workRequest: OneTimeWorkRequest

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        @Suppress("DEPRECATION")
        this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        favBinding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(favBinding.root)

        favAdapter = FavMovieRecyclerViewAdapter(this)

        favBinding.favMovieRecyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        favBinding.favMovieRecyclerView.adapter = favAdapter
        favBinding.favMovieRecyclerView.addItemDecoration(FavMovieItemDecoration(10))

        gDetector =  GestureDetectorCompat(this, CustomGesture())

        // work manager part
        val mNotificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        // Cancel the notification that we started
        val intent = intent
        val notificationId = intent.getIntExtra("NotificationID", 0)
        val message = intent.getStringExtra("Message")
        if (message != null) {
            mNotificationManager.cancel(notificationId)
            favBinding.favMovieDetailsTv.text = "Notification Message\nTotal Favorite Movie Amount: $message"
            mNotificationManager.cancel(notificationId)
        }

        val constraints: Constraints = Constraints.Builder()
            .setRequiresCharging(false)
            .setRequiresStorageNotLow(true)
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        workRequest = OneTimeWorkRequest.Builder(CustomWorker::class.java)
            .setInputData(Data.Builder().putInt("TotalFavMovie", FavoriteMovieSys.favMovieList.size).putString("AppName", "MovieApp").build())
            .build()

        workRequest = OneTimeWorkRequestBuilder<CustomWorker>().setBackoffCriteria(
            BackoffPolicy.LINEAR,
            30,//OneTimeWorkRequest.MIN_BACKOFF_MILLIS
            TimeUnit.MILLISECONDS)
            .build()

        workManager = WorkManager.getInstance(this)

        favBinding.btnStartService.setOnClickListener(View.OnClickListener {

            workRequest = OneTimeWorkRequest.Builder(CustomWorker::class.java)
                .setInputData(Data.Builder().putInt("TotalFavMovie", FavoriteMovieSys.favMovieList.size).putString("AppName", "MovieApp").build())
                .build()

            workManager.enqueue(workRequest)
            Toast.makeText(this@FavoriteActivity, "Worker result will be soon executed.",
                Toast.LENGTH_SHORT).show()

            //at the end of background task what will be done
            workManager.getWorkInfoByIdLiveData(workRequest.id).observe(this@FavoriteActivity,
                Observer{ workInfo ->
                    if (workInfo != null && workInfo.state == WorkInfo.State.SUCCEEDED) {
                        val resultData: Data = workInfo.outputData//get output of worker
                        Toast.makeText(this@FavoriteActivity, "Succeeded, fav movie total: " + resultData.getInt("TotalFavMovie", -2),
                            Toast.LENGTH_LONG ).show()
                    }
                })

        })
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        gDetector?.onTouchEvent(event)
        return super.onTouchEvent(event)
    }

    override fun showFavMovie(movie: Movie) {
        favBinding.favMovieDetailsTv.text = movie.toString()
    }

    inner class CustomGesture: GestureDetector.SimpleOnGestureListener() {
        override fun onFling(e1: MotionEvent?, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
            val distanceX = e2.x - e1!!.x
            val distanceY = e2.y - e1!!.y
            if (abs(distanceX) > abs(distanceY) && abs(distanceX) > 100 && abs(velocityX) > 100)
                if (distanceX > 0) {
                    val intent = Intent(this@FavoriteActivity, MainActivity::class.java)
                    startActivity(intent)
                    return true
                }

            return false
        }
    }
}