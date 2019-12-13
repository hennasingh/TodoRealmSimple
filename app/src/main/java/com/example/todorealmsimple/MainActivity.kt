package com.example.todorealmsimple

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todorealmsimple.model.Item
import io.realm.Realm
import io.realm.RealmResults
import io.realm.Sort
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_task.view.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var realm: Realm

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
        val tasks: RealmResults<Item> = realm.where(Item::class.java).findAll().where().sort("timeStamp",Sort.ASCENDING).findAllAsync()
         rv_list.adapter = TasksRecyclerAdapter(tasks)

    }

    private fun saveToDatabase(task: String) {
        realm.executeTransactionAsync ({

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
}
