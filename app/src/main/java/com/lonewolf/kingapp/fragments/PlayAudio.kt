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
import androidx.lifecycle.ViewModelProvider
import com.lonewolf.kingapp.MainActivity
import com.lonewolf.kingapp.R
import com.lonewolf.kingapp.database.NoteViewModel
import com.lonewolf.kingapp.databinding.FragmentPlayAudioBinding
import com.lonewolf.kingapp.resources.MyDb
import com.squareup.picasso.Picasso
import pl.droidsonroids.gif.GifDrawable
import java.util.*
import java.util.concurrent.ExecutorService
import kotlin.math.roundToInt


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
    private lateinit var handler: Handler

      var mediaPlayer: MediaPlayer? = null
    private var isRecording:Boolean =false
    private var isPlaying : Boolean =false
    private var seconds = 0
    private var dummySecs = 0
    private var playerbleSecs = 0
    private var path =""

    private lateinit var binding: FragmentPlayAudioBinding
    private lateinit var noteViewModel: NoteViewModel
    var nCheck = 0

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

        binding = FragmentPlayAudioBinding.bind(view)
        noteViewModel = ViewModelProvider(this, defaultViewModelProviderFactory).get(NoteViewModel::class.java)

        path = (activity as MainActivity).uPath
        handler = Handler()
        mediaPlayer = MediaPlayer()

        binding.seekBar.isEnabled = false
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
        if(path !=""){
            try {

                mediaPlayer!!.setDataSource(path)
                mediaPlayer!!.prepare()

                playerbleSecs = (mediaPlayer!!.duration)/1000

            }catch (e:Exception){
                e.printStackTrace()
            }
        }else{
            Toast.makeText(requireContext(), "No recording present", Toast.LENGTH_SHORT).show()

        }
        binding.imgPlay.setOnClickListener {
            if(!isPlaying){
                if(nCheck==1){
                    try {
                        mediaPlayer!!.setDataSource(path)
                        mediaPlayer!!.prepare()
                        playerbleSecs = (mediaPlayer!!.duration)/1000
                        nCheck = 0


                    }catch (e:Exception){
                        e.printStackTrace()
                    }
                }
                try {
                    seconds=1

                    mediaPlayer!!.start()
                    binding.imgPic.visibility= View.GONE
                    binding.imgGif.visibility = View.VISIBLE
                    binding.seekBar.isEnabled = true
                }catch (e:Exception){
                    e.printStackTrace()
                }

                isPlaying = true
                Picasso.with(requireContext()).load(R.drawable.ic_pause_black_48dp).into(binding.imgPlay)
                runTimer()
                trackAudio(binding.seekBar, mediaPlayer!!)
            }else{
                mediaPlayer!!.pause()

                isPlaying = false

                Picasso.with(requireContext()).load(R.drawable.ic_play_black_48dp).into(binding.imgPlay)
                binding.imgPic.visibility= View.VISIBLE
                binding.imgGif.visibility = View.GONE
                handler.removeCallbacksAndMessages(null)


            }
        }
    }

    private fun runTimer() {
        handler = Handler()
        handler.post(object : Runnable {
            override fun run() {

                val minutes = (seconds%360)/60
                val sec = seconds%60
                val time = String.format(Locale.getDefault(), "%02d:%02d", minutes, sec)
                binding.txtTime.text = time

                if(isRecording || (isPlaying && playerbleSecs!=-1)){
                    seconds++
                    playerbleSecs--

                    if(playerbleSecs<=-1 && isPlaying) {
                        Picasso.with(requireContext()).load(R.drawable.ic_play_black_48dp).into(binding.imgPlay)
                        binding.imgPic.visibility= View.VISIBLE
                        binding.imgGif.visibility = View.GONE
                        mediaPlayer!!.stop()
                        mediaPlayer!!.reset()
                        mediaPlayer!!.release()
                        isPlaying = false
                        isRecording = false
                        mediaPlayer = null
                        mediaPlayer = MediaPlayer()
                        playerbleSecs = dummySecs
                        seconds = 0
                        nCheck = 1
                        handler.removeCallbacksAndMessages(null)
                        binding.seekBar.isEnabled = false
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

        seeker.max = gogu
        val timerz = Timer()
        timerz.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {

                try {
                    val ggoo = media.duration

                    seeker.progress =
                        ((gogu.toDouble() / ggoo.toDouble()) * media.currentPosition.toDouble()).toInt()

                }catch (e: Exception){
                    timerz.cancel()
                    timerz.purge()
                    e.printStackTrace()
                }
            }
        }, 0, 1000)
        val ggoo = media.duration
        seeker.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                if (fromUser) {


                    val bm = (ggoo.toDouble() / gogu.toDouble()) * progress


                    if (media.isPlaying) {
                        media.seekTo(bm.toInt())
                        seconds = (media.currentPosition.toDouble()/1000).roundToInt()
                        playerbleSecs = ((ggoo.toDouble()/1000) - (media.currentPosition.toDouble()/1000)).roundToInt()
                        media.start()
                    }

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