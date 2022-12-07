package com.lonewolf.kingapp.fragments

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.lonewolf.kingapp.MainActivity
import com.lonewolf.kingapp.R
import com.lonewolf.kingapp.adapters.RecyclerMedia
import com.lonewolf.kingapp.database.Note
import com.lonewolf.kingapp.database.NoteViewModel
import com.lonewolf.kingapp.databinding.FragmentStartPageBinding
import com.lonewolf.kingapp.resources.MyDb


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Start_Page.newInstance] factory method to
 * create an instance of this fragment.
 */
class Start_Page : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var myDb: MyDb
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var recyclerView: RecyclerView
    private  var liveData: MutableLiveData<String> = MutableLiveData()
    private var searchTerm = ""

    private lateinit var noteViewModel: NoteViewModel
    private lateinit var liveDatas: LiveData<List<Note>>
    private lateinit var binding: FragmentStartPageBinding

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
        val view = inflater.inflate(R.layout.fragment_start__page, container, false)
        binding = FragmentStartPageBinding.bind(view)
        myDb = MyDb(requireContext(), "kingdbs", null, 1)

        recyclerView = view.findViewById(R.id.recyclerView)
        linearLayoutManager = LinearLayoutManager(requireContext())
        liveData.observe(viewLifecycleOwner, Observer<String> { searchText: String? ->
            // perform search
            searchTerm = searchText!!
            //getOffLine()
        })
        noteViewModel = ViewModelProvider(this, defaultViewModelProviderFactory).get(NoteViewModel::class.java)
        noteViewModel.liveData.observe(viewLifecycleOwner) { data ->
            if (data.isNotEmpty()) {
                println(data)
                getOffLine(data)
            }
        }




        getButtons()
        //getOffLine()
        return  view
    }

    private fun getButtons() {

        binding.edtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) =
                Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit
            override fun afterTextChanged(s: Editable?) {
                // use the text

                liveData.postValue(s.toString())
            }
            })

        binding.edtSearch.setOnTouchListener { v: View?, event: MotionEvent ->
            val DRAWABLE_LEFT = 0
            val DRAWABLE_TOP = 1
            val DRAWABLE_RIGHT = 2
            val DRAWABLE_BOTTOM = 3
            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= binding.edtSearch.right - binding.edtSearch.compoundDrawables[DRAWABLE_RIGHT].bounds.width()
                ) {
//                        Date_Picker datepicker = new Date_Picker();
//                        datepicker.setEditTextDisplay(topic);
//                        datepicker.show(getFragmentManager(), null);
                       // getOffLine()
                    return@setOnTouchListener true
                }
            }
            false
        }

        binding.floatingActionButton.setOnClickListener {
            showBottom()
        }
    }

    private fun getOffLine(data : List<Note>){

        val arrayList = ArrayList<HashMap<String, String>>()

        try {
                for(aVal in data){
                    val hashMap = HashMap<String, String>()
                    hashMap["Title"] = aVal.Title
                    hashMap["Id"] = aVal.Note_Id.toString()
                    hashMap["Content"] = aVal.Content
                    hashMap["Path"] = aVal.Path
                    hashMap["cDatetime"] = aVal.CreatedDateTime
                    hashMap["uDatetime"] = aVal.UpdatedDateTime
                    hashMap["Type"] = aVal.Type

                    arrayList.add(hashMap)
                }


                if(arrayList.size>0){
                    println(arrayList.toString())
                    val recyclerViewList = RecyclerMedia(requireActivity(), arrayList)
                    binding.recyclerView.layoutManager = linearLayoutManager
                    binding.recyclerView.itemAnimator = DefaultItemAnimator()
                    binding.recyclerView.adapter = recyclerViewList
                }else{
                    binding.recyclerView.removeAllViews()
                }


        }catch (e : Exception){
            e.printStackTrace()
        }
    }

    private fun showBottom() {
        val dialogue = Dialog(requireContext())
        dialogue.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogue.setContentView(R.layout.layout_select)

        val text = dialogue.findViewById<Button>(R.id.btnText)
        val audio = dialogue.findViewById<Button>(R.id.btnRecord)

        text.setOnClickListener {
            (activity as MainActivity).navTo(New_Note(), "New Note", "Start Page", 1)
            dialogue.dismiss()
        }

        audio.setOnClickListener {
            (activity as MainActivity).navTo(New_Audio(), "New Audio", "Start Page", 1)
            dialogue.dismiss()
        }


        dialogue.show()
        dialogue.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialogue.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogue.window!!.attributes.windowAnimations = R.style.bottomAnim
        dialogue.window!!.setGravity(Gravity.BOTTOM)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Start_Page.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Start_Page().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}