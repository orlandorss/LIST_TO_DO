package com.example.list_to_do.ui

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.list_to_do.databinding.AddTaskActBinding
import com.example.list_to_do.datasource.TaskDataSource
import com.example.list_to_do.extensions.format
import com.example.list_to_do.extensions.text
import com.example.list_to_do.model.Task
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.*
//@Dao
class AddTask: AppCompatActivity() {
    private lateinit var binding: AddTaskActBinding
   // @Query("SELECT * FROM Task")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


         binding = AddTaskActBinding.inflate(layoutInflater)
        setContentView(binding.root)


        if (intent.hasExtra(TASK_ID)){
          val taskID =   intent.getIntExtra(TASK_ID,0)
            TaskDataSource.findById(taskID)?.let {
                binding.titleTil.text = it.title
                binding.data.text = it.date
                binding.hourTil.text = it.hour
            }
        }

        insertListeners()
    }
   // @Insert
    private fun insertListeners() {
        binding.data.editText?.setOnClickListener{
            val datePicker =  MaterialDatePicker.Builder.datePicker().build()
            datePicker.addOnPositiveButtonClickListener{
                val default = TimeZone.getDefault()
                val offset = default.getOffset(Date().time) * -1
                binding.data.editText?.setText(Date(it+offset).format())
            }
            datePicker.show(supportFragmentManager,"DATE_PICKER_TAG")
        }

        binding.hourTil.editText?.setOnClickListener{
            val timePicker =  MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .build()
            timePicker.addOnPositiveButtonClickListener{
             val minute =   if (timePicker.minute in 0..9) "0${timePicker.minute}" else timePicker.minute
                val hour = if (timePicker.hour in 0..9) "0${timePicker.hour}" else timePicker.hour
                binding.hourTil.editText?.setText("$hour:$minute")
            }
            timePicker.show(supportFragmentManager,null)
        }
        binding.cancelTask.setOnClickListener {
            finish()
        }

        binding.taskButton.setOnClickListener{
            val task = Task(
                title = binding.titleTil.text,
                date = binding.data.text,
                hour = binding.hourTil.text,
                id = intent.getIntExtra(TASK_ID,0))

            TaskDataSource.insertTask(task)
            setResult(Activity.RESULT_OK)
            finish()
        }
    }
    companion object{
        const val TASK_ID = "task_id"
    }

}