package com.example.taro.myapplication

import android.media.MediaPlayer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast

class SecondActivity : AppCompatActivity() {
    //    MediaPlayer is used to control audio files and streams, public class
//    ? mark is to allow to pass null value
    internal var mediaPlayer: MediaPlayer ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
    }
    companion object {
        const val TOTAL_COUNT = "total_count"
    }
    fun play(view: View){
        val signh = intent.getIntExtra(TOTAL_COUNT,0)
        mediaPlayer = MediaPlayer.create(this, signh)
        mediaPlayer!!.start()

    }
    fun stop(view: View){
        val myToast = Toast.makeText(this, "Stop!!", Toast.LENGTH_SHORT)
//     Pop up "Stop!!" message
        myToast.show()
        mediaPlayer!!.release()
    }
}
