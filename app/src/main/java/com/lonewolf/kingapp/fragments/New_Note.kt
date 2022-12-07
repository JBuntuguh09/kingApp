package com.lonewolf.kingapp.fragments

import android.Manifest
import android.content.Context.MODE_PRIVATE
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageView
import com.canhub.cropper.options
import com.google.android.gms.vision.Frame
import com.google.android.gms.vision.text.TextBlock
import com.google.android.gms.vision.text.TextRecognizer

import com.lonewolf.kingapp.MainActivity
import com.lonewolf.kingapp.R
import com.lonewolf.kingapp.database.Note
import com.lonewolf.kingapp.database.NoteViewModel
import com.lonewolf.kingapp.databinding.FragmentNewNoteBinding
import com.lonewolf.kingapp.databinding.FragmentStartPageBinding
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

    private lateinit var myDb : MyDb
    private var path = ""
    private var uId = ""

    private lateinit var bitmap: Bitmap
    private lateinit var binding: FragmentNewNoteBinding
    private lateinit var noteViewModel: NoteViewModel


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

        binding = FragmentNewNoteBinding.bind(view)
        noteViewModel = ViewModelProvider(this, defaultViewModelProviderFactory).get(NoteViewModel::class.java)


        myDb = MyDb(requireContext(), "kingdbs", null, 1)


        getButton()
        getButtons()
        if((activity as MainActivity).upDateNote==1){
            binding.edtTitle.isEnabled = false
            binding.edtTitle.setText((activity as MainActivity).uTitle.replace(".txt", ""))
            binding.edtContent.setText((activity as MainActivity).uContent)
            path = (activity as MainActivity).uPath
            uId = (activity as MainActivity).uId

            binding.btnSubmit.text = "Edit Note"


        }
        return view
    }

    private fun getButtons() {
        binding.btnCapture.setOnClickListener {
            Toast.makeText(requireContext(), "wassop", Toast.LENGTH_SHORT).show()
            firstCheck()
        }
    }

    private fun getButton(){
        binding.btnSubmit.setOnClickListener {
            if(binding.edtTitle.text.toString().isEmpty()){
                Toast.makeText(requireContext(), "Enter a title", Toast.LENGTH_SHORT).show()
            }else if(binding.edtContent.text.toString().isEmpty()){
                Toast.makeText(requireContext(), "Enter your note", Toast.LENGTH_SHORT).show()
            }else{
                if(binding.btnSubmit.text.toString().equals("Edit Note")){
                    updateText()
                }else{
                    newNote()
                }

            }
        }
    }

    private fun updateText() {
        try {
            val filename  = binding.edtTitle.text.toString()+".txt"
            val fout = requireActivity().openFileOutput(filename, MODE_PRIVATE)
            fout.write(binding.edtContent.text.toString().toByteArray())
            fout.close()
            val file = File(requireActivity().filesDir, filename)
            path = "${requireActivity().filesDir}/${filename}.txt"

            Toast.makeText(requireContext(), "Successfully Updated Note "+path, Toast.LENGTH_SHORT).show()

        }catch (e:Exception){
            e.printStackTrace()
        }finally {
            //myDb.updateNote(binding.edtTitle.text.toString()+".txt", binding.edtContent.text.toString(), path, uId.toInt(), "No")
           val note = Note(uId.toInt(), binding.edtTitle.text.toString()+".txt", binding.edtContent.text.toString(), "Note", path, ShortCut_To.getCurrentDatewithTime(), ShortCut_To.getCurrentDatewithTime())
            noteViewModel.updateNote(note)
            binding.edtTitle.setText("")
            binding.edtContent.setText("")
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
                val filename  = binding.edtTitle.text.toString()+".txt"
                val fout = requireActivity().openFileOutput(filename, MODE_PRIVATE)
                fout.write(binding.edtContent.text.toString().toByteArray())
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
        val note = Note(0, binding.edtTitle.text.toString()+".txt", binding.edtContent.text.toString(), "Note", path, ShortCut_To.getCurrentDatewithTime(), ShortCut_To.getCurrentDatewithTime())
        noteViewModel.insertNote(note)
        binding.edtTitle.setText("")
        binding.edtContent.setText("")

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

    private fun firstCheck() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED
        ) {
            // Permission is not granted
            // Ask for permision
            Log.d("Permission Granted", "No")
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA),
                9
            )
        } else {
            startCrop()
            Log.d("Permission Granted", "Yes")

// Permission has already been granted
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == 9 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startCrop()
        } else {
            Toast.makeText(
                requireContext(),
                "Please allow permissions to access this",
                Toast.LENGTH_SHORT
            ).show()
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private val cropImage = registerForActivityResult(CropImageContract()) { result ->
        if (result.isSuccessful) {
            // use the returned uri

            val uriContent = result.uriContent
            val uriFilePath = result.getUriFilePath(requireContext()) // optional usage

            bitmap = MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, uriContent)

            getTFI(bitmap)
        } else {
            // an error occurred
            val exception = result.error
        }
    }

    private fun startCrop() {
        // start picker to get image for cropping and then use the image in cropping activity
        cropImage.launch(
            options {
                setGuidelines(CropImageView.Guidelines.ON)
            }
        )

        //start picker to get image for cropping from only gallery and then use the image in
        //cropping activity
//        cropImage.launch(
//            options {
//                setImagePickerContractOptions(
//                    PickImageContractOptions(includeGallery = true, includeCamera = false)
//                )
//            }
//        )
//
//        // start cropping activity for pre-acquired image saved on the device and customize settings
//        cropImage.launch(
//            options(uri = imageUri) {
//                setGuidelines(CropImageView.Guidelines.ON)
//                setOutputCompressFormat(Bitmap.CompressFormat.PNG)
//            }
//        )
    }

    private fun getTFI(bitmap: Bitmap){
        val textRecognizer = TextRecognizer.Builder(requireContext()).build()
        if (!textRecognizer.isOperational){
            Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
        }else{
            val frame = Frame.Builder().setBitmap(bitmap).build()
            val sparseArray : SparseArray<TextBlock> = textRecognizer.detect(frame)
            val stringBuilder = StringBuilder()
            for (i in 0 until sparseArray.size()){
                val textBlock = sparseArray.valueAt(i)
                stringBuilder.append(textBlock.value)
                stringBuilder.append("\n")
            }
            binding.edtContent.setText("${binding.edtContent.text} \n${stringBuilder}")
        }





    }
}