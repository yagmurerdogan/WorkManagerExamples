package com.yagmurerdogan.waterreminder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import com.yagmurerdogan.waterreminder.databinding.ActivityMainBinding
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var request: WorkRequest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var minute = 15
        binding.minutesTextView.text = minute.toString()

        binding.upImageView.setOnClickListener {
            minute++
            binding.minutesTextView.text = minute.toString()
        }

        binding.downImageView.setOnClickListener {
            if (minute == 15) {
                Toast.makeText(this, "En az 15 dakika ayarlanabilir", Toast.LENGTH_SHORT).show()
            } else {
                minute--
                binding.minutesTextView.text = minute.toString()
            }
        }

        binding.setButton.setOnClickListener {
            request = PeriodicWorkRequestBuilder<NotifWorker>(
                binding.minutesTextView.text.toString().toLong(),
                TimeUnit.MINUTES
            )
                .build()

            WorkManager.getInstance(this@MainActivity).enqueue(request)
        }
    }
}