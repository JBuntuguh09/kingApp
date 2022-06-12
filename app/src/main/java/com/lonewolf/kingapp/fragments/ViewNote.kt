package com.lonewolf.kingapp.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.lonewolf.kingapp.MainActivity
import com.lonewolf.kingapp.R
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ViewNote.newInstance] factory method to
 * create an instance of this fragment.
 */
class ViewNote : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var title :TextView
    private lateinit var date :TextView
    private lateinit var content :TextView
    //private lateinit var title :TextView
    private lateinit var edit : ImageView


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
        val view = inflater.inflate(R.layout.fragment_view_note, container, false)
        title = view.findViewById(R.id.txtTitle)
        content = view.findViewById(R.id.txtContent)
        date = view.findViewById(R.id.txtDate)
        edit = view.findViewById(R.id.edtEdit)


        getButtons()
        return view

    }

    private fun getButtons() {
        edit.setOnClickListener {
            (activity as MainActivity).upDateNote = 1
            (activity as MainActivity).navTo(New_Note(), "Edit Note", "View Note", 1)
        }
        title.text = (activity as MainActivity).uTitle
       // content.text = (activity as MainActivity).uContent
        date.text = (activity as MainActivity).uDate
        showText()


    }

    private  fun showText(){

        var fileInputStream : FileInputStream
        try {
            fileInputStream = requireActivity().openFileInput((activity as MainActivity).uTitle)
            val inputStreamReader = InputStreamReader(fileInputStream)
            val bufferedReader = BufferedReader(inputStreamReader)
            val stringBuilder = StringBuilder()
            var text : String
            try {
                while ((bufferedReader.readLine().also { text = it }) !=null){
                    stringBuilder.append(text).append("\n")

                }
            }catch (r:Exception){
                r.printStackTrace()
            }
            content.text = stringBuilder.toString()


        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ViewNote.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ViewNote().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}