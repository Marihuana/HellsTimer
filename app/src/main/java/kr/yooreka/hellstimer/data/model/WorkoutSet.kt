package kr.yooreka.hellstimer.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey

@Entity(tableName = "workout_sets", foreignKeys = [
    ForeignKey(entity = WorkoutSession::class, parentColumns = ["id"], childColumns = ["sessionId"], onDelete = CASCADE)
])
data class WorkoutSet(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val weight: Float,
    val reps: Int,
    val success: Boolean,
    val sessionId: Int
)

