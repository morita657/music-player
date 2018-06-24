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
import android.media.MediaPlayer.OnPreparedListener
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_second.*
import android.os.Handler
import java.util.concurrent.TimeUnit
class SecondActivity : AppCompatActivity(),OnSeekBarChangeListener{
    internal var mediaPlayer: MediaPlayer ?= null
    internal var mValue: Int = 0
    internal var seekbarStatusView: TextView? = null
    internal var seekbarView: SeekBar ?= null
    internal var runnable: Runnable ?= null
    private val handler = Handler()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val signh = intent.getIntExtra(SONG_ID,0)
        val name = intent.getStringExtra(SONG_NAME)
        val title = findViewById<View>(R.id.textView) as TextView
        title.setText(name)
        mediaPlayer = MediaPlayer.create(this, signh)
        seekbarView = this.seekbar

        seekbarView!!.setOnSeekBarChangeListener(this)
        mediaPlayer!!.setOnPreparedListener(object: MediaPlayer.OnPreparedListener{
            override fun onPrepared(mp: MediaPlayer?) {
                println(mediaPlayer!!.getDuration())
                seekbarView!!.setMax(mediaPlayer!!.getDuration())
                playCycle()
                mediaPlayer!!.start()
            }
        })
    }
    override fun onProgressChanged(seekBar: SeekBar, progress: Int,fromUser: Boolean) {
        if(mediaPlayer!!.isPlaying()){
            mediaPlayer!!.seekTo(progress)
        }
    }
    fun playCycle(){
            runnable = object : Runnable {
                override fun run() {
                    seekbarView!!.setProgress(mediaPlayer!!.currentPosition)

                    val duration = findViewById<View>(R.id.textView2) as TextView
                    val current:Long = mediaPlayer!!.currentPosition.toLong()
                    val result = String.format("%02d:%02d",
                            TimeUnit.MILLISECONDS.toMinutes(current) -
                                    TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(current)), // The change is in this line
                            TimeUnit.MILLISECONDS.toSeconds(current) -
                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(current)))
                    val fullTime = mediaPlayer!!.getDuration().toLong()
                    val total = String.format("%02d:%02d",
                            TimeUnit.MILLISECONDS.toMinutes(fullTime) -
                                    TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(fullTime)), // The change is in this line
                            TimeUnit.MILLISECONDS.toSeconds(fullTime) -
                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(fullTime)))
                    duration.setText(result + " /" + total)
                    playCycle()
                }
            }
            handler.postDelayed(runnable, 1000)
    }

    override fun onStartTrackingTouch(seekBar: SeekBar) {
        seekbarStatusView!!.text = "Started Tracking Touch"
    }

    override fun onStopTrackingTouch(seekBar: SeekBar) {
        seekbarStatusView!!.text = "Stopped Tracking Touch"

    }

    companion object {
        const val SONG_ID = "song_id"
        const val SONG_NAME="none"
    }
    fun toggle(view: View){
        if(mediaPlayer!!.isPlaying()){
            stop()
        }
        play()
    }
    fun play(){
        mediaPlayer!!.start()
        handler.post(runnable)
    }
    fun stop(){
        val myToast = Toast.makeText(this, "Stop!!", Toast.LENGTH_SHORT)
        myToast.show()
        mediaPlayer!!.pause()
    }
    fun fastfoward(view:View){
        mValue += 30; //change this value to control how much to forward
        mediaPlayer!!.seekTo(mediaPlayer!!.currentPosition+mValue)
    }
    fun backward(view: View){
        mValue -= 30; //change this value to control how much to backward
        mediaPlayer!!.seekTo(mediaPlayer!!.currentPosition+mValue)
    }

}
