package com.evgenynaz.sportnote.bmi

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.evgenynaz.myhomework.homework16.BMI
import com.evgenynaz.sportnote.databinding.ItemHistoryRowBinding


class HistoryAdapter(
    private val clickListener: (BMI) -> Unit
) : ListAdapter<BMI, HistoryAdapter.BmiViewHolder>(DiffUtilItemCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BmiViewHolder =
        BmiViewHolder(
            ItemHistoryRowBinding.inflate(LayoutInflater.from(parent.context)), clickListener
        )

    override fun onBindViewHolder(holder: BmiViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class BmiViewHolder(
        private val bindingView: ItemHistoryRowBinding,
        private val clickListener: (BMI) -> Unit
    ) :
        RecyclerView.ViewHolder(bindingView.root) {

        fun bind(item: BMI) {
            bindingView.bmiTv.text = item.calories
            bindingView.dateItemTv.text = item.date

            itemView.setOnLongClickListener {
                clickListener(item)
                true
            }
        }
    }

}
class DiffUtilItemCallback: DiffUtil.ItemCallback<BMI>(){
    override fun areItemsTheSame(oldItem: BMI, newItem: BMI): Boolean {
        return  oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: BMI, newItem: BMI): Boolean {
        return  oldItem.date == newItem.date && oldItem.calories == newItem.calories
    }


}