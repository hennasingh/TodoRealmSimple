package com.example.todorealmsimple

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.todorealmsimple.model.Item
import io.realm.OrderedRealmCollection
import io.realm.RealmRecyclerViewAdapter

class TasksRecyclerAdapter(data: OrderedRealmCollection<Item>): RealmRecyclerViewAdapter<Item, TaskViewHolder>(data, true){

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.setUI(getItem(position))

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_item, parent, false)
        return TaskViewHolder(view)

    }

}