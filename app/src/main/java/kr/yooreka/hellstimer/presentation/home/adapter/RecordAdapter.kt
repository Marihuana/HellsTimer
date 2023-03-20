package kr.yooreka.hellstimer.presentation.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import kr.yooreka.hellstimer.data.model.WorkoutSet
import kr.yooreka.hellstimer.databinding.ItemRecordBinding

class RecordAdapter(
    private val listener : OnRecordItemClickListener
    ) : ListAdapter<WorkoutSet, RecordAdapter.RecordViewHolder>(RecordDiffUtil) {

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
        private lateinit var item : WorkoutSet

        init {
            binding.root.setOnClickListener { v ->
                listener.onItemClicked(v, item)
            }
        }

        fun bind(item : WorkoutSet, position: Int){
            this.item = item
            binding.tvNo.text = "SET ${position + 1}"
            binding.tvState.text = if(item.success) "SUCCESS" else "FAIL"
        }
    }

    interface OnRecordItemClickListener {
        fun onItemClicked(view : View, item: WorkoutSet)
    }
}

object RecordDiffUtil : DiffUtil.ItemCallback<WorkoutSet>(){
    override fun areItemsTheSame(oldItem: WorkoutSet, newItem: WorkoutSet): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: WorkoutSet, newItem: WorkoutSet): Boolean {
        return oldItem == newItem
    }
}