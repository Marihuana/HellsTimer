package kr.yooreka.hellstimer.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import kr.yooreka.hellstimer.databinding.ItemVolumeBinding
import kr.yooreka.hellstimer.ui.home.model.VolumeVO

class VolumeAdapter(
    private val listener : OnVolumeItemClickListener
    ) : ListAdapter<VolumeVO, VolumeAdapter.VolumeViewHolder>(VolumeDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VolumeViewHolder {
        return LayoutInflater.from(parent.context).let { inflater ->
            ItemVolumeBinding.inflate(inflater, parent, false)
        }.let { binding ->
            VolumeViewHolder(binding, listener)
        }
    }

    override fun onBindViewHolder(holder: VolumeViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }

    inner class VolumeViewHolder(
        private val binding: ItemVolumeBinding,
        private val listener : OnVolumeItemClickListener
    ) : ViewHolder(binding.root){
        private lateinit var item : VolumeVO

        init {
            binding.root.setOnClickListener { v ->
                listener.onItemClicked(v, item)
            }
        }

        fun bind(item : VolumeVO, position: Int){
            this.item = item
            binding.tvName.text = "SET ${position + 1}"
            binding.btnState.text = if(item.isSuccess) "SUCCESS" else "FAIL"
        }
    }

    interface OnVolumeItemClickListener {
        fun onItemClicked(view : View, item: VolumeVO)
    }
}

object VolumeDiffUtil : DiffUtil.ItemCallback<VolumeVO>(){
    override fun areItemsTheSame(oldItem: VolumeVO, newItem: VolumeVO): Boolean {
        return oldItem.records == newItem.records
    }

    override fun areContentsTheSame(oldItem: VolumeVO, newItem: VolumeVO): Boolean {
        return oldItem == newItem
    }
}