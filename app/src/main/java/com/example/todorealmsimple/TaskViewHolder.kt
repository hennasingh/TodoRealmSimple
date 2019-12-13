package com.example.todorealmsimple

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.todorealmsimple.model.Item
import kotlinx.android.synthetic.main.task_item.view.*

class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun setUI(item: Item?){

     item?.let {
         itemView.tv_task.text = it.body
         itemView.checkbox.isChecked = it.isDone
     }

        itemView.checkbox.setOnClickListener{
                item?.realm?.executeTransactionAsync{
                    val item = it.where(Item::class.java).equalTo("itemId", item.itemId).findFirst()
                    if(item!=null){
                        item.isDone = !item.isDone
                    }
                }

        }

    }
}