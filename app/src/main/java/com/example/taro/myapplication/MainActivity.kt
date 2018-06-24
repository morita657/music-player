package com.example.taro.myapplication

import android.content.Intent
import android.media.MediaPlayer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import android.widget.ArrayAdapter
import android.app.Activity
import android.support.v4.content.ContextCompat.startActivity
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import com.example.taro.myapplication.R.id.listview





class MainActivity : AppCompatActivity() {
//lateinit works to define object later
    internal lateinit var listview: ListView
    internal lateinit var list:MutableList<String>
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
//    runtime is the state when the program gets running
//    ::class.java gives you the Java Class<?>

//    !! is operator, the not-null assertion operator that converts any valu to a non-null type,
//    and throws an exception if the value is null
        val fields = R.raw::class.java!!.getFields()
        for(i in fields.indices){
            list.add(fields[i].getName())
        }
//    ArrayAdapter provides views for an ArrayView
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, list)
        listview.adapter = adapter
//    Invoke callback when an item in the list is clicked
        listview.onItemClickListener = AdapterView.OnItemClickListener{AdapterView, view,i,l ->
            if(mediaPlayer != null){
                mediaPlayer!!.release()
            }
//            Get id of resource
            val singh = resources.getIdentifier(list[i], "raw", packageName)
            var songName = fields[i].getName()
            val intent = Intent(this, SecondActivity::class.java)
            intent.putExtra(SecondActivity.SONG_ID, singh)
            intent.putExtra(SecondActivity.SONG_NAME, songName)
            startActivity(intent)
        }
    }
}
