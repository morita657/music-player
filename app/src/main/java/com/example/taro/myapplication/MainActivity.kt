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
        }
    }
}
