package kr.yooreka.hellstimer.presentation.home.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import kr.yooreka.hellstimer.databinding.DialogTimerBinding

class SetTimerDialog(context : Context, val listener : OnTimerSetListener) : Dialog(context) {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView
        val binding = DialogTimerBinding.inflate(layoutInflater)
        bindView(binding)
        setContentView(binding.root)
    }

    private fun bindView(binding: DialogTimerBinding){
        binding.minPicker.maxValue = 10
        binding.minPicker.minValue = 0

        //set secPicker increase interval 5
        binding.secPicker.maxValue = 11
        binding.secPicker.displayedValues = arrayOf("00", "05", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55")


        binding.btnDone.setOnClickListener {
            val min = binding.minPicker.value
            val sec = binding.secPicker.value * 5
            listener.onTimerSet(min, sec)
            dismiss()
        }
    }

    //ceate interface when dialog button clicked
    interface OnTimerSetListener {
        fun onTimerSet(min : Int, sec : Int)
    }
}