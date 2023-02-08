package kr.yooreka.hellstimer.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    companion object {
        val DEFAULT_EA = 10
    }

    private val _ea = MutableLiveData(DEFAULT_EA)
    val ea: LiveData<Int> = _ea

//    private val _timerVal = MutableLiveData<Int>(60)
//    val timerVal : LiveData<Int> = _timerVal

    private val _timerVal = MutableLiveData(convertText(60))
    val timerVal : LiveData<String> = _timerVal


    private lateinit var timerJob : Job

    fun increase(){
        _ea.value = _ea.value?.plus(1)
    }

    fun decrease(){
        _ea.value = _ea.value?.minus(1)
    }

    fun timerStart(sec : Int){
        var second = sec

        if(::timerJob.isInitialized) timerJob.cancel()

        viewModelScope.launch {
            while(second > 0){
                second = second.minus(1)
                delay(1000L)
                _timerVal.value = convertText(second)
            }
        }
    }

    fun timerStop(){
        if(::timerJob.isInitialized) timerJob.cancel()
    }

    private fun convertText(sec : Int):String{
        return "${String.format("%02d",sec/60)}:${String.format("%02d",sec%60)}"
    }
}