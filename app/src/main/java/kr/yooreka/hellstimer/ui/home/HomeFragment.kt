package kr.yooreka.hellstimer.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import kr.yooreka.hellstimer.databinding.FragmentHomeBinding
import kr.yooreka.hellstimer.ui.home.adapter.RecordAdapter
import kr.yooreka.hellstimer.ui.home.adapter.VolumeAdapter
import kr.yooreka.hellstimer.ui.home.model.RecordVO
import kr.yooreka.hellstimer.ui.home.model.VolumeVO

class HomeFragment : Fragment(), RecordAdapter.OnRecordItemClickListener, VolumeAdapter.OnVolumeItemClickListener {
    private val viewModel : HomeViewModel by viewModels()
    private val recordAdapter = RecordAdapter(this)
    private val volumeAdapter = VolumeAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentHomeBinding.inflate(inflater, container, false)
        bindView(binding)
        subscribeUI(binding)

        return binding.root
    }

    private fun bindView(binding : FragmentHomeBinding){
        binding.tvName.setOnClickListener {
            Toast.makeText(context, "이름 고치는 다이얼로그 나와야됨", Toast.LENGTH_SHORT).show()
        }

        binding.tvWeight.setOnClickListener {
            Toast.makeText(context, "무게 고치는 다이얼로그 나와야됨", Toast.LENGTH_SHORT).show()
        }

        binding.tvIteration.setOnClickListener {
            Toast.makeText(context, "횟수 고치는 다이얼로그 나와야됨", Toast.LENGTH_SHORT).show()
        }

        binding.btnStart.setOnClickListener {
            viewModel.performStartButton()
        }

        binding.btnDone.setOnClickListener {
            viewModel.performDoneButton()
        }

        binding.btnSuccess.setOnClickListener {
            viewModel.performSuccessButton()
        }

        binding.btnFail.setOnClickListener {
            viewModel.performFailButton()
        }

        binding.rvRecord.apply {
            adapter = recordAdapter
            layoutManager = LinearLayoutManager(context)
        }

        binding.rvVolume.apply {
            adapter = volumeAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun subscribeUI(binding : FragmentHomeBinding){
        viewModel.name.observe(viewLifecycleOwner){ name ->
            binding.tvName.text = name
        }

        viewModel.weight.observe(viewLifecycleOwner){ weight ->
            binding.tvWeight.text = "$weight"
        }

        viewModel.iteration.observe(viewLifecycleOwner){ iteration ->
            binding.tvIteration.text = "$iteration"
        }

        viewModel.btnStartVisibility.observe(viewLifecycleOwner){ visibility ->
            binding.loStartButtons.visibility = visibility
        }

        viewModel.btnStopVisibility.observe(viewLifecycleOwner){ visibility ->
            binding.loStopButtons.visibility = visibility
        }

        viewModel.btnDoneVisibility.observe(viewLifecycleOwner){ visibility ->
            binding.btnDone.visibility = visibility
        }

        viewModel.record.observe(viewLifecycleOwner){ list ->
            recordAdapter.submitList(list)
        }

        viewModel.volume.observe(viewLifecycleOwner){ list ->
            volumeAdapter.submitList(list)
        }
    }

    override fun onItemClicked(view: View, item: RecordVO) {
        Toast.makeText(context, "세트 상세 다이얼로그 나와야됨", Toast.LENGTH_SHORT).show()
    }

    override fun onItemClicked(view: View, item: VolumeVO) {
        Toast.makeText(context, "운동 상세 다이얼로그 나와야됨", Toast.LENGTH_SHORT).show()
    }
}