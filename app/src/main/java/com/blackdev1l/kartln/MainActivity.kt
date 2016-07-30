package com.blackdev1l.kartln

import android.content.Context
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar

import android.view.Menu
import android.view.MenuItem
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue

import java.util.*


class MainActivity : AppCompatActivity() {

    var FILE_NAME = String()
    val  mapper = ObjectMapper().registerModule(KotlinModule())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FILE_NAME = resources.getString(R.string.filename) as String
        if (!fileList().contains(FILE_NAME))
            createFile()
        

        setContentView(R.layout.activity_main)
        val toolbar = findViewById(R.id.toolbar) as Toolbar?
        setSupportActionBar(toolbar)



        val mLayoutManager = LinearLayoutManager(this)

        val mRecyclerView = findViewById(R.id.recycler_view) as RecyclerView
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true)

        // use a linear layout manager
        mRecyclerView.layoutManager = mLayoutManager

        // specify an adapter (see also next example)

        val mAdapter = DayAdapter(readFile());
        mRecyclerView.setAdapter(mAdapter);



        val fab = findViewById(R.id.fab) as FloatingActionButton?
        fab!!.setOnClickListener { log(mRecyclerView) }

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.action_delete ->  {
                deleteFile(FILE_NAME)
                createFile()
                onStart()
            }

            R.id.action_undo -> undo()
        }
        return true
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.toolbar,menu)
        return true
    }

    private fun log(mRecyclerView : RecyclerView) {

        val input = openFileInput(FILE_NAME)
        val days: Vector<Day> = mapper.readValue(input)
        input.close()

        if(days.lastElement().marks.size < 4) {
            days.lastElement().mark()
        } else {
            days.addElement(Day())
            days.lastElement().mark()
        }

        val output = openFileOutput(FILE_NAME,Context.MODE_PRIVATE)
        mapper.writeValue(output,days)
        output.close()

        val mAdapter = DayAdapter(days);

         mRecyclerView.setAdapter(mAdapter);

    }


    private fun createFile() {
        if (fileList().isNotEmpty())
            deleteFile(FILE_NAME)
        val file = openFileOutput(FILE_NAME,Context.MODE_PRIVATE)
        val days = Vector<Day>()
        days.addElement(Day())

        mapper.writeValue(file,days)
        file.close()

    }

    fun readFile(): Vector<Day> {
        val input = openFileInput(FILE_NAME)
        val days: Vector<Day> = mapper.readValue(input)
        input.close()
        return days
    }

    private fun undo(): Boolean {
        return true

    }

}
