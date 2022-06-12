package com.lonewolf.kingapp.fragments

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.lonewolf.kingapp.MainActivity
import com.lonewolf.kingapp.R
import com.lonewolf.kingapp.adapters.RecyclerMedia
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
    private lateinit var search : EditText
    private lateinit var addNew : FloatingActionButton
    private lateinit var myDb: MyDb
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var recyclerView: RecyclerView

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

        myDb = MyDb(requireContext(), "kingdbs", null, 1)
        search = view.findViewById(R.id.edtSearch)
        addNew = view.findViewById(R.id.floatingActionButton)
        recyclerView = view.findViewById(R.id.recyclerView)
        linearLayoutManager = LinearLayoutManager(requireContext())

        getButtons()
        getOffLine()
        return  view
    }

    private fun getButtons() {
        search.setOnTouchListener { v: View?, event: MotionEvent ->
            val DRAWABLE_LEFT = 0
            val DRAWABLE_TOP = 1
            val DRAWABLE_RIGHT = 2
            val DRAWABLE_BOTTOM = 3
            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= search.right - search.compoundDrawables[DRAWABLE_RIGHT].bounds.width()
                ) {
//                        Date_Picker datepicker = new Date_Picker();
//                        datepicker.setEditTextDisplay(topic);
//                        datepicker.show(getFragmentManager(), null);

                    return@setOnTouchListener true
                }
            }
            false
        }

        addNew.setOnClickListener {
            showBottom()
        }
    }

    private fun getOffLine(){
        val curs = myDb.getData("select * from ${myDb.MainTb};")
        val arrayList = ArrayList<HashMap<String, String>>()
        Log.d("hhhhhhh", curs.toString())
        try {
            if (curs != null) {
                for(a in 0 until curs.count){
                    curs.moveToPosition(a)
                    val hashMap = HashMap<String, String>()
                    hashMap["Title"] = curs.getString(curs.getColumnIndex("Title"))
                    hashMap["Id"] = curs.getString(curs.getColumnIndex("Main_Id"))
                    hashMap["Content"] = curs.getString(curs.getColumnIndex("Content"))
                    hashMap["Path"] = curs.getString(curs.getColumnIndex("Path"))
                    hashMap["cDatetime"] = curs.getString(curs.getColumnIndex("Created_Datetime"))
                    hashMap["Type"] = curs.getString(curs.getColumnIndex("Type"))


                    arrayList.add(hashMap)
                }


                if(arrayList.size>0){
                    val recyclerViewList = RecyclerMedia(requireActivity(), arrayList)
                    recyclerView.layoutManager = linearLayoutManager
                    recyclerView.itemAnimator = DefaultItemAnimator()
                    recyclerView.adapter = recyclerViewList
                }

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