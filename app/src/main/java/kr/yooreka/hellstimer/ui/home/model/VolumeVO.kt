package kr.yooreka.hellstimer.ui.home.model

import java.util.Date

data class VolumeVO(
    val name : String,
    val records: List<RecordVO>,
    val isSuccess : Boolean,
    val date : Date = Date()
)