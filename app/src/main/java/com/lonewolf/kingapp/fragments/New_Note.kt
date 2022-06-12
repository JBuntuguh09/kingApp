package com.lonewolf.kingapp.fragments

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.lonewolf.kingapp.MainActivity
import com.lonewolf.kingapp.R
import com.lonewolf.kingapp.resources.MyDb
import com.lonewolf.kingapp.resources.ShortCut_To
import java.io.File

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [New_Note.newInstance] factory method to
 * create an instance of this fragment.
 */
class New_Note : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var title : EditText
    private lateinit var content : EditText
    private lateinit var createNewText : Button
    private lateinit var myDb : MyDb
    private var path = ""
    private var uId = ""


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
        val view = inflater.inflate(R.layout.fragment_new__note, container, false)

        myDb = MyDb(requireContext(), "kingdbs", null, 1)
        title = view.findViewById(R.id.edtTitle)
        content = view.findViewById(R.id.edtContent)
        createNewText = view.findViewById(R.id.btnSubmit)

        getButton()

        if((activity as MainActivity).upDateNote==1){
            title.isEnabled = false
            title.setText((activity as MainActivity).uTitle.replace(".txt", ""))
            content.setText((activity as MainActivity).uContent)
            path = (activity as MainActivity).uPath
            uId = (activity as MainActivity).uId

            createNewText.text = "Edit Note"

        }
        return view
    }

    private fun getButtons() {

    }

    private fun getButton(){
        createNewText.setOnClickListener {
            if(title.text.toString().isEmpty()){
                Toast.makeText(requireContext(), "Enter a title", Toast.LENGTH_SHORT).show()
            }else if(content.text.toString().isEmpty()){
                Toast.makeText(requireContext(), "Enter your note", Toast.LENGTH_SHORT).show()
            }else{
                if(createNewText.text.toString().equals("Edit Note")){
                    updateText()
                }else{
                    newNote()
                }

            }
        }
    }

    private fun updateText() {
        try {
            val filename  = title.text.toString()+".txt"
            val fout = requireActivity().openFileOutput(filename, MODE_PRIVATE)
            fout.write(content.text.toString().toByteArray())
            fout.close()
            val file = File(requireActivity().filesDir, filename)
            path = "${requireActivity().filesDir}/${filename}.txt"

            Toast.makeText(requireContext(), "Successfully Updated Note "+path, Toast.LENGTH_SHORT).show()

        }catch (e:Exception){
            e.printStackTrace()
        }finally {
            myDb.updateNote(title.text.toString()+".txt", content.text.toString(), path, uId.toInt(), "No")
            title.setText("")
            content.setText("")
            parentFragmentManager.popBackStack()
            //offlineSave()
        }
    }

    private fun newNote() {
 //       val filenamen  = Environment.getExternalStorageDirectory().absolutePath +"/Android/data/com.lonewolf.kingapp/files/text/"+title.text.toString()+".txt"
//        val myfile = File(filename)
//        try {
//            myfile.printWriter().use {
//                    out->out.println(content.text.toString())
//                out.println("End of file")
//            }
//        }catch (e:Exception){
//            e.printStackTrace()
//        }finally {
//            offlineSave()
//        }
        try {
                val filename  = title.text.toString()+".txt"
                val fout = requireActivity().openFileOutput(filename, MODE_PRIVATE)
                fout.write(content.text.toString().toByteArray())
                fout.close()
                val file = File(requireActivity().filesDir, filename)
                path = "${requireActivity().filesDir}/${filename}.txt"

                Toast.makeText(requireContext(), "Successfully created Note "+path, Toast.LENGTH_SHORT).show()
            }catch (e:Exception){
                e.printStackTrace()
            }finally {
                offlineSave()
            }
    }

    private fun offlineSave() {
        myDb.insertNote(title.text.toString()+".txt", content.text.toString(), path, "Note")
        title.setText("")
        content.setText("")

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment New_Note.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            New_Note().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}