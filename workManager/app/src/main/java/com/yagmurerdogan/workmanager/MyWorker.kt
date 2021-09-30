package com.yagmurerdogan.workmanager

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

/**
 * Created by Yağmur ERDOĞAN on 30 September 2021
 */
class MyWorker(
    appContext: Context,
    workerParams: WorkerParameters
) : Worker(appContext, workerParams) {

    override fun doWork(): Result {

        val sumProcess = 10 + 20
        Log.e("sumProcess: ","$sumProcess")

        return Result.success()
    }
}