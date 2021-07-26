package com.example.list_to_do.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.list_to_do.databinding.ActivityMainBinding
import com.example.list_to_do.datasource.TaskDataSource

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val adapter by lazy { TaskListAdapter()}


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvList.adapter = adapter
        updateList()
        insertListeners()
    }

    private fun insertListeners() {
        binding.buttonFloat.setOnClickListener {
            startActivityForResult(Intent(this, AddTask::class.java), CREATE_NEW_TASK)
        }
        adapter.listenerEdit= {
            val intent = Intent(this,AddTask::class.java)
            intent.putExtra(AddTask.TASK_ID,it.id)
            startActivityForResult(intent, CREATE_NEW_TASK)

        }
        adapter.listenerDelete={
            TaskDataSource.deleteTask(it)
            updateList()}
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CREATE_NEW_TASK && requestCode == Activity.RESULT_OK)
        binding.rvList.adapter = adapter
        updateList()
    }
    private fun  updateList(){
        val list = TaskDataSource.getList()
        if (list.isEmpty()){
            binding.includeLayout.emptyState.visibility = View.VISIBLE
        }else{
            binding.includeLayout.emptyState.visibility = View.GONE
        }
        adapter.submitList(list)
    }

    companion object{
        private const val CREATE_NEW_TASK = 1
    }
}
