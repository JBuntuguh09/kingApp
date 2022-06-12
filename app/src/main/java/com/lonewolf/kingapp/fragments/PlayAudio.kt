package com.lonewolf.kingapp.fragments

import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.fragment.app.Fragment
import com.lonewolf.kingapp.MainActivity
import com.lonewolf.kingapp.R
import com.lonewolf.kingapp.resources.MyDb
import com.squareup.picasso.Picasso
import java.util.*
import java.util.concurrent.ExecutorService


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PlayAudio.newInstance] factory method to
 * create an instance of this fragment.
 */
class PlayAudio : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private  lateinit var imgMain: pl.droidsonroids.gif.GifImageView
    private  lateinit var imgStill:ImageView
    private lateinit var mPlayer: MediaPlayer
    private lateinit var handler: Handler
    private lateinit var record : ImageView
    private lateinit var play : ImageView
    private lateinit var timer : TextView
    private lateinit var seekBar: SeekBar
    private lateinit var submit : Button
      var mediaPlayer: MediaPlayer? = null
    private  var mediaRecorder: MediaRecorder? = null
    private lateinit var executorService: ExecutorService
    private var isRecording:Boolean =false
    private var isPlaying : Boolean =false
    private var seconds = 0
    private var dummySecs = 0
    private var playerbleSecs = 0
    private var path =""
    private var permissionsVal =1
    private var audioName = ""
    private lateinit var myDb : MyDb

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_play_audio, container, false)

        play = view.findViewById(R.id.imgPlay)

        imgMain = view.findViewById(R.id.imgGif)
        imgStill = view.findViewById(R.id.imgPic)
        timer = view.findViewById(R.id.txtTime)
        seekBar = view.findViewById(R.id.seekBar)
        path = (activity as MainActivity).uPath

        handler = Handler()
        mediaPlayer = MediaPlayer()


        getButtons()

        return view
    }

    override fun onDetach() {
        mediaPlayer!!.stop()
        mediaPlayer!!.reset()
        mediaPlayer!!.release()
        isPlaying = false
        isRecording = false
        mediaPlayer = null
        mediaPlayer = MediaPlayer()
        playerbleSecs = dummySecs
        seconds = 0
        handler.removeCallbacksAndMessages(null)
        super.onDetach()
    }

    private fun getButtons() {
        play.setOnClickListener {
            if(!isPlaying){
                if(path !=""){
                    try {

                        mediaPlayer!!.setDataSource(path)

                    }catch (e:Exception){
                        e.printStackTrace()
                    }
                }else{
                    Toast.makeText(requireContext(), "No recording present", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                try {
                    mediaPlayer!!.prepare()
                    Log.d("mmmm", mediaPlayer!!.duration.toString())
                    playerbleSecs = (mediaPlayer!!.duration)/1000
                   // seconds = mediaPlayer!!.duration
                    mediaPlayer!!.start()
                }catch (e:Exception){
                    e.printStackTrace()
                }

                isPlaying = true
                Picasso.with(requireContext()).load(R.drawable.ic_pause_black_48dp).into(play)
                runTimer()
                trackAudio(seekBar, mediaPlayer!!)
            }else{
                mediaPlayer!!.stop()
                mediaPlayer!!.release()
                mediaPlayer = null
                mediaPlayer = MediaPlayer()
                isPlaying = false
                seconds =0
                Picasso.with(requireContext()).load(R.drawable.ic_play_black_48dp).into(play)
                handler.removeCallbacksAndMessages(null)

            }
        }
    }

    private fun runTimer() {
        handler = Handler()
        handler.post(object : Runnable {
            override fun run() {

                var minutes = (seconds%360)/60
                var sec = seconds%60
                var time = String.format(Locale.getDefault(), "%02d:%02d", minutes, sec)
                timer.text = time

                if(isRecording || (isPlaying && playerbleSecs!=-1)){
                    seconds++
                    playerbleSecs--

                    if(playerbleSecs<=-1 && isPlaying) {
                        Picasso.with(requireContext()).load(R.drawable.ic_play_black_48dp).into(play)
                        mediaPlayer!!.stop()
                        mediaPlayer!!.reset()
                        mediaPlayer!!.release()
                        isPlaying = false
                        isRecording = false
                        mediaPlayer = null
                        mediaPlayer = MediaPlayer()
                        playerbleSecs = dummySecs
                        seconds = 0
                        handler.removeCallbacksAndMessages(null)

                        return
                    }
                }
                handler.postDelayed({ this.run() }, 1000)
            }
        })
    }

    private fun trackAudio(seeker: SeekBar, media: MediaPlayer) {
        seeker.progress = 0
        val gogu = media.duration

        Log.d("lllll", gogu.toString())
        seeker.max = gogu
        val timerz = Timer()
            timerz.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {

                try {
                    val ggoo = media.duration

                    Log.d("mmmmm2", ggoo.toString())
                    seeker.progress =
                        ((gogu.toDouble() / ggoo.toDouble()) * media.currentPosition.toDouble()).toInt()

                }catch (e: Exception){
                    timerz.cancel()
                    timerz.purge()
                    e.printStackTrace()
                }
            }
        }, 0, 1000)

        seeker.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    val ggoo = media.duration
                    Log.d("mmmmm", ggoo.toString())
                    val bm = (ggoo.toDouble() / gogu.toDouble()) * progress
                    //  Log.d("open", String.valueOf(progress)+"//"+mediaPlayer1.getDuration()+"//"+gogu+"//"+bm+"//"+actSeek);
                    media.seekTo(bm.toInt())
                    if (media.isPlaying) {
                        media.start()
                    }

                    //    seekMeter(mediaPlayer1, gogu1);
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })
    }

    fun packPress(){

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PlayAudio.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PlayAudio().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

}