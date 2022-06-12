package com.lonewolf.kingapp.adapters

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lonewolf.kingapp.MainActivity
import com.lonewolf.kingapp.R
import com.lonewolf.kingapp.fragments.PlayAudio
import com.lonewolf.kingapp.fragments.ViewNote
import com.lonewolf.kingapp.resources.ShortCut_To

class RecyclerMedia(context : Activity, arrayList : ArrayList<HashMap<String, String>>)
    : RecyclerView.Adapter<RecyclerMedia.MyHolder>() {
    private var arrayList = ArrayList<HashMap<String, String>>()
    private var context : Activity


    init {
        this.arrayList =arrayList
        this.context = context

    }

    inner class MyHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        var linMain : LinearLayout
        var title : TextView
        var type : TextView
        var edit : ImageView
        var delete : ImageView
        var date : TextView

        init {
            linMain = itemview.findViewById(R.id.linMain)
            title = itemview.findViewById(R.id.edtTitle)
            type = itemview.findViewById(R.id.txtType)
            edit = itemview.findViewById(R.id.imgEdit)
            delete = itemview.findViewById(R.id.imgClose)
            date = itemview.findViewById(R.id.txtDate)


        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerMedia.MyHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_media,parent, false)

        return MyHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerMedia.MyHolder, position: Int) {
        val hash = arrayList[position]
        holder.title.setText(hash["Title"])
        holder.type.setText(hash["Type"])
        holder.date.setText(hash["cDatetime"]!!.split("T")[0])

        if(hash["Type"].equals("Note")){

        }else{

        }

        holder.linMain.setOnClickListener {
            (context as MainActivity).uTitle = hash["Title"]!!
            (context as MainActivity).uId = hash["Id"]!!
            (context as MainActivity).upDateNote = 0
            (context as MainActivity).uPath = hash["Path"]!!
            (context as MainActivity).uContent = hash["Content"]!!
            (context as MainActivity).uDate = hash["cDatetime"]!!.split("T")[0]

            if(hash["Type"].equals("Note")){
                (context as MainActivity).navTo(ViewNote(), "View Note", "Start Page", 1)
            }else{
                (context as MainActivity).navTo(PlayAudio(), "Play Audio", "Start Page", 1)
            }
        }

        holder.edit.setOnClickListener {

        }

        holder.delete.setOnClickListener {

        }

    }

    override fun getItemCount(): Int {
        return arrayList.size
    }
}