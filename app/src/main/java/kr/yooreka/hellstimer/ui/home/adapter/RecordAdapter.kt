package kr.yooreka.hellstimer.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import kr.yooreka.hellstimer.databinding.ItemRecordBinding
import kr.yooreka.hellstimer.ui.home.model.RecordVO

class RecordAdapter(
    private val listener : OnRecordItemClickListener
    ) : ListAdapter<RecordVO, RecordAdapter.RecordViewHolder>(RecordDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordViewHolder {
        return LayoutInflater.from(parent.context).let { inflater ->
            ItemRecordBinding.inflate(inflater, parent, false)
        }.let { binding ->
            RecordViewHolder(binding, listener)
        }
    }

    override fun onBindViewHolder(holder: RecordViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }

    inner class RecordViewHolder(
        private val binding: ItemRecordBinding,
        private val listener : OnRecordItemClickListener
    ) : ViewHolder(binding.root){
        private lateinit var item : RecordVO

        init {
            binding.root.setOnClickListener { v ->
                listener.onItemClicked(v, item)
            }
        }

        fun bind(item : RecordVO, position: Int){
            this.item = item
            binding.tvNo.text = "SET ${position + 1}"
            binding.tvState.text = if(item.isSuccess) "SUCCESS" else "FAIL"
        }
    }

    interface OnRecordItemClickListener {
        fun onItemClicked(view : View, item: RecordVO)
    }
}

object RecordDiffUtil : DiffUtil.ItemCallback<RecordVO>(){
    override fun areItemsTheSame(oldItem: RecordVO, newItem: RecordVO): Boolean {
        return oldItem.number == newItem.number
    }

    override fun areContentsTheSame(oldItem: RecordVO, newItem: RecordVO): Boolean {
        return oldItem == newItem
    }
}