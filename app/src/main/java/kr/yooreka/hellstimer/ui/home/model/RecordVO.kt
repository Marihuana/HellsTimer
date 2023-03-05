package kr.yooreka.hellstimer.ui.home.model

data class RecordVO(
    val number : Int,       //몇번째인지
    val weight : Float,     //무게
    val iteration : Int,    //ea
    val isSuccess : Boolean //성공,실패
)
