package com.lonewolf.kingapp.fragments

import android.Manifest
import android.content.Context
import android.content.ContextWrapper
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.net.Credentials
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.lonewolf.kingapp.MainActivity
import com.lonewolf.kingapp.R
import com.lonewolf.kingapp.resources.MyDb
import com.lonewolf.kingapp.resources.ShortCut_To
import com.lonewolf.kingapp.resources.UploadFiles
import com.squareup.picasso.Picasso
import java.io.File
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [New_Audio.newInstance] factory method to
 * create an instance of this fragment.
 */
class New_Audio : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var record : ImageView
    private lateinit var play : ImageView
    private lateinit var timer : TextView
    private lateinit var seekBar: SeekBar
    private lateinit var submit : Button
    private  var mediaPlayer: MediaPlayer? = null
    private  var mediaRecorder: MediaRecorder? = null
    private lateinit var executorService: ExecutorService
    private var isRecording:Boolean =false
    private var isPlaying : Boolean =false
    private var seconds = 0
    private var dummySecs = 0
    private var playerbleSecs = 0
    private lateinit var handler: Handler
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
        val view = inflater.inflate(R.layout.fragment_new__audio, container, false)

        myDb = MyDb(requireContext(), "kingdbs", null, 1)
        record = view.findViewById(R.id.imgRecord)
        play = view.findViewById(R.id.imgPlay)
        timer = view.findViewById(R.id.txtTime)
        seekBar = view.findViewById(R.id.seekBar)
        submit = view.findViewById(R.id.btnSubmit)

        mediaPlayer = MediaPlayer()
        executorService = Executors.newSingleThreadExecutor()
        audioName = "Audio-${ShortCut_To.getCurrDateRaw()}"


        getButtons()
        return  view
    }

    private fun getButtons() {
        record.setOnClickListener {
            if (hasPermissions(requireContext(),
                    Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )){
                if(!isRecording){
                    Log.d("fffff", "hello222")
                    isRecording=true

                    executorService.execute {
                        Log.d("rrrrr", "gggggg")
                        mediaRecorder = MediaRecorder()
                        mediaRecorder!!.setAudioSource(MediaRecorder.AudioSource.MIC)
                        mediaRecorder!!.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
                        mediaRecorder!!.setOutputFile(getRecordinPath())
                        path = getRecordinPath()


                        try {
                            mediaRecorder!!.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
                            mediaRecorder!!.prepare()
                            mediaRecorder!!.start()
                        }catch (e: Exception){
                            e.printStackTrace()
                        }



                        requireActivity().runOnUiThread {
                            play.visibility =View.GONE
                            Picasso.with(requireContext()).load(R.drawable.ic_stop_black_48dp).into(record)
                            playerbleSecs = 0
                            seconds = 0
                            dummySecs = 0

                            runTimer();
                        }
                    }
                }else{
                    executorService.execute{

                        mediaRecorder!!.stop()
                        mediaRecorder!!.release()
                        mediaRecorder = null
                        playerbleSecs = seconds
                        dummySecs =seconds
                        seconds =0
                        isRecording = false

                        requireActivity().runOnUiThread {
                           // play.visibility =View.GONE
                            play.visibility = View.VISIBLE
                            Picasso.with(requireContext()).load(R.drawable.ic_microphone_settings_black_48dp).into(record)
                            handler.removeCallbacksAndMessages(null)
                        }
                    }
                }

            }else{
                Log.d("fffff", "hello")
                requestStoragePermmissions()
            }
        }

        play.setOnClickListener {
            if(!isPlaying){
                if(path !=""){
                    try {
                        Log.d("gets", "Here")
                        mediaPlayer!!.setDataSource(getRecordinPath())
                    }catch (e:Exception){
                        e.printStackTrace()
                    }
                }else{
                    Toast.makeText(requireContext(), "No recording present", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                try {
                    mediaPlayer!!.prepare()
                    mediaPlayer!!.start()
                }catch (e:Exception){
                    e.printStackTrace()
                }

                isPlaying = true
                Picasso.with(requireContext()).load(R.drawable.ic_stop_black_48dp).into(play)
                runTimer()
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

        submit.setOnClickListener {
            if(isPlaying){
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
            }
            if(isRecording){
                Toast.makeText(requireContext(), "Finish recording before you save", Toast.LENGTH_SHORT).show()
            }else if(path==""){
                Toast.makeText(requireContext(), "Record an audio file", Toast.LENGTH_SHORT).show()
            }else{
                offlineInsert()
            }
        }
    }

    private fun offlineInsert() {
        myDb.insertNote(audioName, audioName, path, "Audio")
        (activity as MainActivity).showPage(Start_Page(), "Start Page", "New Audio", "1")
        parentFragmentManager.popBackStack()


    }

    private fun getRecordinPath(): String {
        val contextWrapper=ContextWrapper(requireContext().applicationContext)
        val music = contextWrapper.getExternalFilesDir(Environment.DIRECTORY_MUSIC)
        val file = File(music, audioName+".mp3")


        return file.path

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

                    if(playerbleSecs==-1 && isPlaying) {
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


    private fun  requestStoragePermmissions(){
        //Permission is not granted
        // Ask for permision


        // Permission is not granted
        // Ask for permision
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.RECORD_AUDIO, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE),
            1
        )

    }

    fun hasPermissions(context: Context?, vararg permissions: String?): Boolean {
        if (context != null && permissions != null) {
            for (permission in permissions) {
                if (ActivityCompat.checkSelfPermission(
                        context,
                        permission!!
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    requestStoragePermmissions()
                    return false

                }
            }
        }
        return true
    }



    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if(requestCode==permissionsVal){
            if(grantResults.isNotEmpty()){
                val permissonToRecord = grantResults[0] == PackageManager.PERMISSION_GRANTED
                if (permissonToRecord){
                    Toast.makeText(requireContext(), "Permission granted", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(requireContext(), "Permission not granted", Toast.LENGTH_SHORT).show()
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onDetach() {
        if(isPlaying){
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
        }
        super.onDetach()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment New_Audio.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            New_Audio().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}