package kr.yooreka.hellstimer.domain.model

data class RecordVO(
    val name : String,      //운동 이름
    val number : Int,       //몇번째 세트
    val weight : Float,     //무게
    val reps : Int,         //반복 횟수
    val success : Boolean   //성공,실패
)
