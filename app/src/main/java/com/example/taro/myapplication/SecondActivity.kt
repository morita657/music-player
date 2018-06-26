package com.example.taro.myapplication

import android.media.MediaPlayer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import android.support.annotation.IntDef
import android.widget.ListView
import java.lang.annotation.RetentionPolicy
import java.util.concurrent.Executors
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.net.Uri
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_second.*
import android.os.Handler
import java.util.concurrent.TimeUnit
class SecondActivity : AppCompatActivity(),OnSeekBarChangeListener{
    internal var mediaPlayer: MediaPlayer ?= null
    internal var mValue: Int = 0
    internal var seekbarView: SeekBar ?= null
    internal var runnable: Runnable ?= null
    private val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val signh = intent.getStringExtra(SONG_ID)
        val name = intent.getStringExtra(SONG_NAME)
        val title = findViewById<View>(R.id.textView) as TextView
        title.setText(name)
        println(signh)
        mediaPlayer = MediaPlayer.create(getApplicationContext(), Uri.parse(signh))
        seekbarView = this.seekbar
        seekbarView!!.setMax(mediaPlayer!!.getDuration())
        playCycle()
        mediaPlayer!!.start()
    }
    override fun onProgressChanged(seekBar: SeekBar, progress: Int,fromUser: Boolean) {
        mediaPlayer!!.seekTo(progress)
    }
    fun playCycle(){
        seekbarView!!.setProgress(mediaPlayer!!.currentPosition)
            runnable = object : Runnable {
                override fun run() {
                    seekbarView!!.setProgress(mediaPlayer!!.currentPosition)
                    val duration = findViewById<View>(R.id.textView2) as TextView
                    val current: Long = mediaPlayer!!.currentPosition.toLong()
                    val result = computeDuration(current)
                    val fullTime = mediaPlayer!!.getDuration().toLong()
                    val total = computeDuration(fullTime)
                    duration.setText(result + " / " + total)
                    playCycle()
                }
            }
            handler.postDelayed(runnable, 1000)
    }

    override fun onStartTrackingTouch(seekBar: SeekBar) {}

    override fun onStopTrackingTouch(seekBar: SeekBar) {}

    companion object {
        const val SONG_ID = "song_id"
        const val SONG_NAME="none"
    }
    fun toggle(view: View){
        if(mediaPlayer!!.isPlaying){
            stop()
        }
        mediaPlayer!!.start()
        play()
    }
    fun play(){
        mediaPlayer!!.start()
        handler.post(runnable)
    }
    fun stop(){
        if(mediaPlayer!!.isPlaying()){
            println(mediaPlayer!!.isPlaying())
            super.onPause()
            println(mediaPlayer!!.pause())
            mediaPlayer!!.pause()
        }
    }
    fun fastfoward(view:View){
        mValue += 30; //change this value to control how much to forward
        mediaPlayer!!.seekTo(mediaPlayer!!.currentPosition+mValue)
    }
    fun backward(view: View){
        mValue -= 30; //change this value to control how much to backward
        mediaPlayer!!.seekTo(mediaPlayer!!.currentPosition+mValue)
    }

    fun computeDuration(duration:Long):String{
        return String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(duration) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(duration)), // The change is in this line
                TimeUnit.MILLISECONDS.toSeconds(duration) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration)))
    }

}
