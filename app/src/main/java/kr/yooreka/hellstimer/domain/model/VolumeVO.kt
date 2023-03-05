package kr.yooreka.hellstimer.domain.model

import java.util.Date

data class VolumeVO(
    val id : Int,
    val name : String,
    val records: List<RecordVO>,
    val date : Date = Date()
){
    fun success() : Boolean = records.find { !it.success } == null
}