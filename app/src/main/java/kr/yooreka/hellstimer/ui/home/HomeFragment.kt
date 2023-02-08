package kr.yooreka.hellstimer.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import kr.yooreka.hellstimer.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.btnUp.setOnClickListener {
            homeViewModel.increase()
        }
        binding.btnDown.setOnClickListener {
            homeViewModel.decrease()
        }
        binding.btnStart.setOnClickListener {
            homeViewModel.timerStart(60)
        }

        homeViewModel.ea.observe(viewLifecycleOwner){
            binding.tvEa.text = "$it"
        }

        homeViewModel.timerVal.observe(viewLifecycleOwner){
            binding.tvTimer.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}