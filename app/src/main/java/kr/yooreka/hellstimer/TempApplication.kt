package kr.yooreka.hellstimer

import android.app.Application
import kr.yooreka.hellstimer.data.model.WorkoutDatabase

class TempApplication : Application() {
    val database: WorkoutDatabase by lazy { WorkoutDatabase.getInstance(this) }
}