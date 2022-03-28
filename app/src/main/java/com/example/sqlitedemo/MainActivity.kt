package com.example.sqlitedemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var edName: EditText
    private lateinit var edEmail: EditText
    private lateinit var btnAdd: Button
    private lateinit var btnView: Button
    private lateinit var btnUpdate: Button

    private lateinit var sqLiteHelper: SQLiteHelper
    private lateinit var recyclerView: RecyclerView
    private var adapter:StudentAdapter? = null
    private var std:StudentModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        iniRecyclerView()
        sqLiteHelper = SQLiteHelper(this)
        btnAdd.setOnClickListener { addStudent() }
        btnView.setOnClickListener { getStudents() }
        btnUpdate.setOnClickListener { updateStudent() }

        adapter?.setOnClickItem {
            Toast.makeText(this, it.name, Toast.LENGTH_LONG).show()
            edName.setText(it.name)
            edEmail.setText(it.email)
            std = it
        }

        adapter?.setOnClickDeleteItem {
           deleteStudent(it.id)
        }
    }

    private fun getStudents() {
        val stdList = sqLiteHelper.getAllStudent()
        Log.e("pppp", "${stdList.size}")
        adapter?.addItems(stdList)
    }

    private fun addStudent() {
        val  name = edName.text.toString()
        val email = edEmail.text.toString()
        if (name.isEmpty() || email.isEmpty()){
            Toast.makeText(this, "Please enter requried field", Toast.LENGTH_SHORT).show()
        } else {
            val std = StudentModel(name = name, email = email)
            val status = sqLiteHelper.insertStudent(std)
            if (status > -1){
                Toast.makeText(this, "Student Added...", Toast.LENGTH_SHORT).show()
                clearEditText()
                getStudents()
            } else{
                Toast.makeText(this, "Record not saved", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun updateStudent(){
        val name = edName.text.toString()
        val email = edEmail.text.toString()

        if(name == std?.name && email == std?.email){
            Toast.makeText(this, "record not changed...", Toast.LENGTH_SHORT).show()
            return
        }

        if (std == null) return

        val std = StudentModel(id = std!!.id, name= name, email= email)
        val status = sqLiteHelper.updateStudent(std)
        if (status > -1){
            clearEditText()
            getStudents()
        } else{
            Toast.makeText(this, "Update failed", Toast.LENGTH_SHORT).show()
        }

    }

    private fun deleteStudent(id: Int){
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Are you sure you want to delete item?")
        builder.setCancelable(true)
        builder.setPositiveButton("yes"){dialog, _ ->
            sqLiteHelper.deleteStudentById(id)
            getStudents()
            dialog.dismiss()}
        builder.setNegativeButton("no"){dialog, _ ->dialog.dismiss()}
        val alerts = builder.create()
        alerts.show()
    }

    private fun clearEditText() {
        edName.setText("")
        edEmail.setText("")
        edName.requestFocus()
    }

    private fun iniRecyclerView(){
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = StudentAdapter()
        recyclerView.adapter = adapter
    }
    private fun initView() {
        edName = findViewById(R.id.edName)
        edEmail = findViewById(R.id.edEmail)
        btnAdd = findViewById(R.id.btnAdd)
        btnView = findViewById(R.id.btnView)
        btnUpdate = findViewById(R.id.btnUpdate)
        recyclerView = findViewById((R.id.recyclerView))
    }
}