package com.example.taro.myapplication

import android.media.MediaPlayer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import android.widget.ListView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    internal lateinit var listview: ListView
    internal lateinit var list:MutableList<String>
    internal lateinit var adapter:ListAdapter
    internal var mediaPlayer: MediaPlayer ?= null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listview = findViewById<View>(R.id.listview) as ListView
        list = ArrayList()

        val fields = R.raw::class.java!!.getFields()
        for(i in fields.indices){
            list.add(fields[i].getName())
        }
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, list)
        listview.adapter = adapter

        listview.onItemClickListener = AdapterView.OnItemClickListener{AdapterView, view,i,l ->
            if(mediaPlayer != null){
                mediaPlayer!!.release()
            }
            val singh = resources.getIdentifier(list[i], "raw", packageName)
            mediaPlayer = MediaPlayer.create(this, singh)
            mediaPlayer!!.start()
        }
    }
}
