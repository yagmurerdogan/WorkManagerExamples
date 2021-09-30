package com.yagmurerdogan.workmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.work.*
import com.yagmurerdogan.workmanager.databinding.ActivityMainBinding
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var request: WorkRequest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.doButton.setOnClickListener {

            val workCondition = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

            request = PeriodicWorkRequestBuilder<NotifWorker>(15, TimeUnit.MINUTES)
                //OneTimeWorkRequestBuilder<NotifWorker>()
                .setConstraints(workCondition)
                .build()

            WorkManager.getInstance(this@MainActivity)
                .enqueue(request)

            WorkManager.getInstance(this@MainActivity)
                .getWorkInfoByIdLiveData(request.id)
                .observe(this@MainActivity, { workInfo ->
                    val status = workInfo.state.name
                    Log.e("sum process state: ", status)
                })
        }
    }
}