package kr.yooreka.hellstimer.ui.home

import android.util.Log
import android.view.View
import androidx.lifecycle.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kr.yooreka.hellstimer.data.model.WorkoutSession
import kr.yooreka.hellstimer.data.model.WorkoutSessionDao
import kr.yooreka.hellstimer.data.model.WorkoutSet
import kr.yooreka.hellstimer.data.model.WorkoutSetDao
import kr.yooreka.hellstimer.ui.home.model.RecordVO
import kr.yooreka.hellstimer.ui.home.model.VolumeVO
import java.util.*
import kotlin.collections.ArrayList

class HomeViewModel(
    private val sessionDao: WorkoutSessionDao,
    private val setDao: WorkoutSetDao
) : ViewModel() {

    class HomeViewModelFactory(
        private val sessionDao: WorkoutSessionDao,
        private val setDao: WorkoutSetDao
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return HomeViewModel(sessionDao, setDao) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
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

    fun getSets() : Flow<List<WorkoutSet>> = setDao.getSetsForSession(0)
//    private val _record = MutableLiveData<ArrayList<RecordVO>>(arrayListOf())
//    val record: LiveData<List<RecordVO>> get() = _record.map { it }

    private val _volume = MutableLiveData<ArrayList<VolumeVO>>(arrayListOf())
    val volume: LiveData<List<VolumeVO>> get() = _volume.map { it }

//    private val _timerVal = MutableLiveData(convertText(60))
//    val timerVal : LiveData<String> = _timerVal

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
        addVolume()
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
        viewModelScope.launch {
            val session = sessionDao.getSession(1)

            WorkoutSet(
                weight = weight.value!!,
                reps = iteration.value!!,
                success = isSuccess,
                sessionId = session?.id ?:1
            ).let { set ->
                setDao.insert(set)
            }.also {
                _btnDoneVisibility.value = View.VISIBLE
            }
        }
//        RecordVO(
//            (_record.value?.size?:0) + 1,
//            weight.value!!,
//            iteration.value!!,
//            isSuccess
//        ).let { record ->
//            _record.value?.add(record)
//        }.also {
//            _btnDoneVisibility.value = View.VISIBLE
//        }
    }

    private fun addVolume(){
        viewModelScope.launch {
            val result = sessionDao.insert(
                WorkoutSession(
                    id = 1,
                    exerciseName = "가나다",
                    System.currentTimeMillis()
                )
            )
            Log.d("Test", "result $result")
        }
//        record.value?.let { record ->
//            val isSuccess = record.find { !it.isSuccess } == null
//            VolumeVO(
//                name.value!!,
//                record,
//                isSuccess
//            )
//        }?.also {
//            _volume.value?.add(it)
//        }
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
//        _record.value = arrayListOf()
        _btnStartVisibility.value = View.VISIBLE
        _btnDoneVisibility.value = View.GONE
        _btnStopVisibility.value = View.GONE
    }

    private fun convertText(sec : Int):String{
        return "${String.format("%02d",sec/60)}:${String.format("%02d",sec%60)}"
    }
}