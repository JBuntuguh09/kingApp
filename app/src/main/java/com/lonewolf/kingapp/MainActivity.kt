package com.lonewolf.kingapp

import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.lonewolf.kingapp.fragments.Start_Page
import com.lonewolf.kingapp.resources.NetworkStateReceiver
import com.lonewolf.kingapp.resources.ShortCut_To


class MainActivity : AppCompatActivity(), NetworkStateReceiver.NetworkStateReceiverListener {
    private lateinit var submit : Button
    private lateinit var topic : EditText
    private lateinit var content : EditText
    private val REQ_ONE_TAP = 2 // Can be any integer unique to the Activity.

    private val showOneTapUI = true

    private lateinit var networkStateReceiver: NetworkStateReceiver
    var uTitle =""
    var uContent =""
    var uPath = ""
    var uDate = ""
    var uId = ""
    var upDateNote = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        val account = GoogleSignIn.getLastSignedInAccount(this)
//        if(account!=null){
//            authGoogle()
//        }


        networkStateReceiver = NetworkStateReceiver()

        networkStateReceiver!!.addListener(this)
        this.registerReceiver(
            networkStateReceiver,
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )



//        submit.setOnClickListener {
//            try {
//                val filename  = topic.text.toString()
//                val fout = openFileOutput(filename, MODE_PRIVATE)
//                fout.write(content.text.toString().toByteArray())
//                fout.close()
//                val file = File(filesDir, filename)
//                Toast.makeText(this, "Here is  "+filesDir, Toast.LENGTH_SHORT).show()
//            }catch (e:Exception){
//                e.printStackTrace()
//            }
//        }

        Log.d("aaaaaa", ShortCut_To.getCurrDateRaw());

        showPage(Start_Page(), "Start Page", "", "Start Page")
    }

     fun showPage(fragment: Fragment, stackName: String, oldStack: String, title: String) {
        val fragmentManager = supportFragmentManager

        if (fragmentManager.findFragmentByTag(stackName) != null) {

            fragmentManager.beginTransaction().show(fragmentManager.findFragmentByTag(stackName)!!)
                .commit()
        } else {
            //if the fragment does not exist, add it to fragment manager.
            fragmentManager.beginTransaction().add(R.id.frame_main, fragment, stackName).commit()
        }
        if (fragmentManager.findFragmentByTag(oldStack) != null) {
            //if the other fragment is visible, hide it.
            fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag(oldStack)!!)
                .commit()
        }

    }

    fun navTo(frag : Fragment, page:String, prev: String,returnable : Int) {

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        if(returnable==1){
            fragmentTransaction.replace(R.id.frame_main, frag, page).addToBackStack(page)
        }else{
            fragmentTransaction.remove(frag).replace(R.id.frame_main, frag, page).addToBackStack(prev)
        }
        fragmentTransaction.commit()


    }

    override fun onDestroy() {
        super.onDestroy()
        networkStateReceiver!!.removeListener(this);
        this.unregisterReceiver(networkStateReceiver);
    }

    override fun networkAvailable() {
       Log.d("internet", "Yes yes yes")
    }

    override fun networkUnavailable() {
        Log.d("internet", "No No No")
    }

    fun authGoogle(){
        Log.d("bbbb", "ok")
        var gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail().build()

        // Build a GoogleSignInClient with the options specified by gso.
      var  mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        val signInIntent: Intent = mGoogleSignInClient.getSignInIntent()
        startActivityForResult(signInIntent, 1)

    }

    override fun onStart() {
        val account = GoogleSignIn.getLastSignedInAccount(this)
        //updateUI(account)
        super.onStart()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode === 1) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task: Task<GoogleSignInAccount> =
                GoogleSignIn.getSignedInAccountFromIntent(data)
            //handleSignInResult(task)
        }
    }


}

