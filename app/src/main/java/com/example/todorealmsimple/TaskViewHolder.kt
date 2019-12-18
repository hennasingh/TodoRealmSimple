package com.example.todorealmsimple

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.todorealmsimple.model.Item
import io.realm.Realm
import kotlinx.android.synthetic.main.task_item.view.*

class TaskViewHolder(itemView: View, var realm: Realm) : RecyclerView.ViewHolder(itemView) {


    fun setUI(item: Item?, position: Int){

     item?.let {
         itemView.tv_task.text = it.body
         itemView.checkbox.isChecked = it.isDone
     }
        itemView.checkbox.setOnCheckedChangeListener{_, isChecked ->
            itemView.checkbox.isChecked = isChecked
            realm.executeTransactionAsync{
                val taskList = it.where(Item::class.java).findAll()
                val task = taskList[position]
                task?.isDone =  isChecked

            }
        }

    }



}