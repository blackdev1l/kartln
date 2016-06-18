package com.blackdev1l.kartln

import android.content.Context
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    var FILE_NAME = String()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById(R.id.toolbar) as Toolbar?
        setSupportActionBar(toolbar)
        FILE_NAME = resources.getString(R.string.filename) as String


        val fab = findViewById(R.id.fab) as FloatingActionButton?
        fab!!.setOnClickListener { log() }
        createFile()
        setTextView()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.action_delete ->  {
                deleteFile(FILE_NAME)
                createFile()
            }

            R.id.action_undo -> undo()
        }
        setTextView()
        return true
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.toolbar,menu)
        return true
    }

    private fun log() {
        val output = openFileOutput(FILE_NAME, Context.MODE_APPEND)
        val format = SimpleDateFormat("dd-MM-yyyy HH:mm:ss\n");
        val today = Date()
        output.write(format.format(today).toByteArray())
        output.close()

        setTextView()

    }

    private fun setTextView() {
        val input = openFileInput(FILE_NAME)
        val builder = StringBuilder()
        val textView = findViewById(R.id.textView) as TextView

        input.bufferedReader().readLines().forEach { builder.append(it+"\n") }

        textView.text = builder.toString()
    }

    private fun createFile() {
        if (!fileList().contains(FILE_NAME))
            log()

    }

    private fun undo(): Boolean {
        try {
            val input = openFileInput(FILE_NAME)
            val builder = StringBuilder()

            input.bufferedReader().readLines().dropLast(1).forEach { builder.append(it+"\n") }

            val output = openFileOutput(FILE_NAME, Context.MODE_PRIVATE)
            output.write(builder.toString().toByteArray())
        } catch(e: Exception) {

        }


        return true

    }

}
