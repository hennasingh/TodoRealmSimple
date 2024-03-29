package com.example.todorealmsimple

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todorealmsimple.model.Item
import io.realm.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_task.view.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var realm: Realm
    private lateinit var realmAsyncTask: RealmAsyncTask
    private lateinit var  taskList: RealmResults<Item>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        realm = Realm.getDefaultInstance()
        rv_list.layoutManager = LinearLayoutManager(this)

        readToDisplayData()

        fab.setOnClickListener{
            val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_task, null)
           AlertDialog.Builder(this)
                .setTitle("Add a new Task")
                .setMessage("What do you want to do next?")
                .setView(dialogView)
                .setPositiveButton("Add"
                ) { _, _ ->  dialogView.et_task.text?.let{
                    saveToDatabase(it.toString())
                }
                    dialogView.et_task.error = "Cant leave it empty"
                    dialogView.et_task.requestFocus()
                }
                .setNegativeButton("Cancel")
                {dialog, _ -> dialog.cancel()
                }
                .create()
                .show()
        }
    }

    private fun readToDisplayData() {

        val taskList = realm.where(Item::class.java)
            .sort("timeStamp",Sort.ASCENDING)
            .findAllAsync()

        taskList.addChangeListener(taskListListener)

    }

   private val taskListListener: RealmChangeListener<RealmResults<Item>> = RealmChangeListener {
        rv_list.adapter = TasksRecyclerAdapter(it, realm)
    }

    private fun saveToDatabase(task: String) {
       realmAsyncTask =  realm.executeTransactionAsync ({

            val item = it.createObject(Item::class.java, UUID.randomUUID().toString())
            item.body = task

        },
            {
                Log.d("MainActivity", "OnSuccess: Data written successfully")
            },

            {
                Log.d("MainActivity", "OnError: Error in saving Data! ${it.message}")
            })
    }

    override fun onStop() {
        super.onStop()

        if (!realmAsyncTask.isCancelled) {
            realmAsyncTask.cancel()
        }

        taskList?.let{
            it.removeChangeListener(taskListListener)
        }

         realm.close()
    }
}
