package kr.yooreka.hellstimer.ui.home

import android.view.View
import androidx.lifecycle.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kr.yooreka.hellstimer.ui.home.model.RecordVO
import kr.yooreka.hellstimer.ui.home.model.VolumeVO
import java.util.*
import kotlin.collections.ArrayList

class HomeViewModel : ViewModel() {
    companion object {
        const val DEFAULT_NAME : String = "WORKOUT"
        const val DEFAULT_ITERATION : Int = 10
        const val DEFAULT_WEIGHT : Float = 5.0f
    }

    private val _name = MutableLiveData(DEFAULT_NAME)
    val name: LiveData<String> = _name

    private val _weight = MutableLiveData(DEFAULT_WEIGHT)
    val weight: LiveData<Float> = _weight

    private val _iteration = MutableLiveData(DEFAULT_ITERATION)
    val iteration: LiveData<Int> = _iteration

    private val _record = MutableLiveData<ArrayList<RecordVO>>(arrayListOf())
    val record: LiveData<List<RecordVO>> get() = _record.map { it }

    private val _volume = MutableLiveData<ArrayList<VolumeVO>>(arrayListOf())
    val volume: LiveData<List<VolumeVO>> get() = _volume.map { it }

    private val _timerVal = MutableLiveData(convertText(60))
    val timerVal : LiveData<String> = _timerVal

    private val _cntSet = MutableLiveData<Int>(1)
    val cntSet : LiveData<Int> = _cntSet

    private val _btnStartVisibility = MutableLiveData<Int>(View.VISIBLE)
    val btnStartVisibility : LiveData<Int> = _btnStartVisibility

    private val _btnStopVisibility = MutableLiveData<Int>(View.GONE)
    val btnStopVisibility : LiveData<Int> = _btnStopVisibility

    private val _btnDoneVisibility = MutableLiveData<Int>(View.GONE)
    val btnDoneVisibility : LiveData<Int> = _btnDoneVisibility

    private var setCount = 1

    fun performStartButton(){
        setStartButtonVisibility(false)
    }
    fun performDoneButton(){
        addVolume()
        refresh()
    }

    fun performSuccessButton(){
        setStartButtonVisibility(true)
        addRecord(true)
    }
    fun performFailButton(){
        setStartButtonVisibility(true)
        addRecord(false)
    }

    private fun addRecord(isSuccess : Boolean) {
        RecordVO(
            name.value!!,
            (_record.value?.size?:0) + 1,
            weight.value!!,
            iteration.value!!,
            isSuccess
        ).let { record ->
            _record.value?.add(record)
        }.also {
            _btnDoneVisibility.value = View.VISIBLE
        }
    }

    private fun addVolume(){
        record.value?.let { record ->
            val isSuccess = record.find { !it.isSuccess } == null
            VolumeVO(
                name.value!!,
                record,
                isSuccess
            )
        }?.also {
            _volume.value?.add(it)
        }
    }

    private fun setStartButtonVisibility(isVisible : Boolean) {
        if(!isVisible){
            _btnStartVisibility.value = View.GONE
            _btnStopVisibility.value = View.VISIBLE
        }else{
            _btnStartVisibility.value = View.VISIBLE
            _btnStopVisibility.value = View.GONE
        }
    }

    private fun refresh(){
        _name.value = DEFAULT_NAME
        _weight.value = DEFAULT_WEIGHT
        _iteration.value = DEFAULT_ITERATION
        _record.value = arrayListOf()
        _btnStartVisibility.value = View.VISIBLE
        _btnDoneVisibility.value = View.GONE
        _btnStopVisibility.value = View.GONE
    }

    private fun convertText(sec : Int):String{
        return "${String.format("%02d",sec/60)}:${String.format("%02d",sec%60)}"
    }
}