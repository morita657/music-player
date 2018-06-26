package com.example.taro.myapplication

import android.content.Intent
import android.media.MediaPlayer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import android.widget.ArrayAdapter
import android.os.Environment
import android.widget.AdapterView
import java.io.File


class MainActivity : AppCompatActivity() {
//lateinit works to define object later
    internal lateinit var listview: ListView
    internal lateinit var list:MutableList<String>
    internal lateinit var nameList:MutableList<String>
    internal lateinit var durationList:MutableList<String>
//    ListAdapter is used to customze list view layout
    internal lateinit var adapter:ListAdapter
//    MediaPlayer is used to control audio files and streams, public class
//    ? mark is to allow to pass null value
    internal var mediaPlayer: MediaPlayer ?= null

//    Override is one of the method for class and inheritance.
//    If we want to override function, we require the explicit annotations like below
    override fun onCreate(savedInstanceState: Bundle?) {
//    Use "super" to call parent class constructor
        super.onCreate(savedInstanceState)
//    setContentView is a function to handle UI. This displays the Layyout created through the XML or dynamically created layout
//    R is a class that contains only public constants, that is used to call values defined in the res folder.
        setContentView(R.layout.activity_main)
//    findViewById is the moethod to find the view from the layout resources
//    "as" is cast operator, and usually cast operator throws an exception if the cast is not possible.
//    cast operator is used to convert a value to another type
//    Get the ListView
        listview = findViewById<View>(R.id.listview) as ListView
        list = ArrayList()
        durationList = ArrayList()

    findSongs(Environment.getExternalStorageDirectory().getPath() + "/TestFolder/")
    }
    fun findSongs(path:String) {
        list = ArrayList()
        nameList = ArrayList()
        val rootFolder = File(path)
        var files = rootFolder.listFiles() //here you will get NPE if directory doesn't contains  any file,handle it like this.
        for (file in files) {
                if (file.getName().endsWith(".mp3")) {
                list.add(file.getAbsolutePath())
                nameList.add(file.getName().toString().replace(".mp3", ""))
            }
        }
        //    ArrayAdapter provides views for an ArrayView
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, nameList)
        listview.adapter = adapter
        //    Invoke callback when an item in the list is clicked
        listview.onItemClickListener = AdapterView.OnItemClickListener{AdapterView, view,i,l ->
            if(mediaPlayer != null){
                mediaPlayer!!.release()
            }
            var songName = nameList[i]
            val intent = Intent(this, SecondActivity::class.java)
            intent.putExtra(SecondActivity.SONG_ID, list[i])
            intent.putExtra(SecondActivity.SONG_NAME, songName)
            startActivity(intent)
        }
    }
}
