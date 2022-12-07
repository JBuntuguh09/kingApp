package com.lonewolf.kingapp.resources

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Build.VERSION_CODES
import android.text.TextUtils


class Storage(context: Context) {
    val pref: SharedPreferences
    val editor: SharedPreferences.Editor
        get() = pref.edit()
    var lockCode: String?
        get() = pref.getString(LOCKCODE, "")
        set(lockCode) {
            val editor = editor
            editor.putString(LOCKCODE, lockCode)
            editor.commit()
        }
    var lockStatus: String?
        get() = pref.getString(LOCKSTATUS, "")
        set(lockStatus) {
            val editor = editor
            editor.putString(LOCKSTATUS, lockStatus)
            editor.commit()
        }
    var pLATFORM: String?
        get() = pref.getString(PLATFORM, "")
        set(platform) {
            val editor = editor
            editor.putString(PLATFORM, platform)
            editor.commit()
        }
    var uSER_TYPE: String?
        get() = pref.getString(USER_TYPE, "")
        set(user_type) {
            val editor = editor
            editor.putString(USER_TYPE, user_type)
            editor.commit()
        }
    var uSERID: String?
        get() = pref.getString(USERID, "")
        set(userid) {
            val editor = editor
            editor.putString(USERID, userid)
            editor.commit()
        }

    var fEATURE: String?
        get() = pref.getString(FEATURE, "")
        set(feature) {
            val editor = editor
            editor.putString(FEATURE, feature)
            editor.commit()
        }
    var temp_1: String?
        get() = pref.getString(TEMP_1, "")
        set(temp_1) {
            val editor = editor
            editor.putString(TEMP_1, temp_1)
            editor.commit()
        }
    var role: String?
        get() = pref.getString(ROLE, "")
        set(role) {
            val editor = editor
            editor.putString(ROLE, role)
            editor.commit()
        }
    var uSERNAME: String?
        get() = pref.getString(USERNAME, "")
        set(username) {
            val editor = editor
            editor.putString(USERNAME, username)
            editor.commit()
        }
    var pASSWORD: String?
        get() = pref.getString(PASSWORD, "")
        set(password) {
            val editor = editor
            editor.putString(PASSWORD, password)
            editor.commit()
        }
    var firstName: String?
        get() = pref.getString(FNAME, "")
        set(firstName) {
            val editor = editor
            editor.putString(FNAME, firstName)
            editor.commit()
        }

    fun gettokenId(): String? {
        return pref.getString(TOKENID, "")
    }

    var tokenId :String?
        get() =pref.getString(TOKENID, "")
        set(TokenId) {
            val editor = editor
            editor.putString(TOKENID, TokenId)
            editor.commit()
        }

    var adminToken : String?
        get() = pref.getString(ADMINID, "")
        set(Admintoken) {
            val editor = editor
            editor.putString(ADMINID, Admintoken)
            editor.commit()
        }

    var service : String?
    get() = pref.getString(SERVICE, "")
    set(Service) {
        val editor = editor
        editor.putString(SERVICE, Service)
        editor.commit()
    }

    var permissions : String?
    get() = pref.getString(PERMISSIONS, "")
    set(Permissions) {
        val editor = editor
        editor.putString(PERMISSIONS, Permissions)
        editor.commit()
    }

    var  loggedIn : Boolean
    get() = pref.getBoolean(KEEPLOGGEDIN, false)
    set(KeepLoggedIn) {
        val editor = editor
        editor.putBoolean(KEEPLOGGEDIN, KeepLoggedIn)
        editor.commit()
    }

    var featurePic1 : String?
    get() = pref.getString(FEATUREPIC1, "")
    set(FeaturePic) {
        val editor = editor
        editor.putString(FEATUREPIC1, FeaturePic)
        editor.commit()
    }

    var featurePic2 : String?
        get() = pref.getString(FEATUREPIC2, "")
        set(FeaturePic) {
            val editor = editor
            editor.putString(FEATUREPIC2, FeaturePic)
            editor.commit()
        }

    var featurePic3 : String?
        get() = pref.getString(FEATUREPIC3, "")
        set(FeaturePic) {
            val editor = editor
            editor.putString(FEATUREPIC3, FeaturePic)
            editor.commit()
        }

    var featurePic4 : String?
        get() = pref.getString(FEATUREPIC4, "")
        set(FeaturePic) {
            val editor = editor
            editor.putString(FEATUREPIC4, FeaturePic)
            editor.commit()
        }

    var featurePic5 : String?
        get() = pref.getString(FEATUREPIC5, "")
        set(FeaturePic) {
            val editor = editor
            editor.putString(FEATUREPIC5, FeaturePic)
            editor.commit()
        }

    var searchVal : String?
        get() = pref.getString(SEARCHVAL, "")
        set(searchVal) {
            val editor = editor
            editor.putString(SEARCHVAL, searchVal)
            editor.commit()
        }

    var fragVal : String?
        get() = pref.getString(FRAGVAL, "")
        set(fragval) {
            val editor = editor
            editor.putString(FRAGVAL, fragval)
            editor.commit()
        }

    var fragValPrev : String?
        get() = pref.getString(FRAGVALPREV, "")
        set(fragvalPrev) {
            val editor = editor
            editor.putString(FRAGVALPREV, fragvalPrev)
            editor.commit()
        }

    var musicianId : String?
        get() = pref.getString(MUSICIANID, "")
        set(value) {
            val editor = editor
            editor.putString(MUSICIANID, value)
            editor.commit()
        }

    var genre : String?
        get() = pref.getString(GENRE, "")
        set(value) {
            val editor = editor
            editor.putString(GENRE, value)
            editor.commit()
        }

    var picPath : String?
        get() = pref.getString(PICPATH, "")
        set(value) {
            val editor = editor
            editor.putString(PICPATH, value)
            editor.commit()
        }

    var listCategory : MutableSet<String>?
        get() = pref.getStringSet(LISTCAT, null)
        set(value) {
            val editor = editor
            editor.putStringSet(LISTCAT, value)
            editor.commit()
        }

    var selectedCategory : String?
        get() = pref.getString(SELECTCAT, "")
        set(value) {
            val editor = editor
            editor.putString(SELECTCAT, value)
            editor.commit()
        }

    var project : String?
        get() = pref.getString(PROJECT, "")
        set(value) {
            val editor = editor
            editor.putString(PROJECT, value)
            editor.commit()
        }

    var projectType : String?
        get() = pref.getString(PROJECTTYPE, "")
        set(value) {
            val editor = editor
            editor.putString(PROJECTTYPE, value)
            editor.commit()
        }

    var selectedUserId : String?
        get() = pref.getString(SELECTEDUSERID, "")
        set(value) {
            val editor = editor
            editor.putString(SELECTEDUSERID, value)
            editor.commit()
        }

    var myCurrency : String?
        get() = pref.getString(MYCURRENCY, "")
        set(value) {
            val editor = editor
            editor.putString(MYCURRENCY, value)
            editor.commit()
        }

    var ansTitle : String?
        get() = pref.getString(answerTitle, "")
        set(value) {
            val editor = editor
            editor.putString(answerTitle, value)
            editor.commit()
        }

    var randVal : String?
        get() = pref.getString(RANDVAL, "")
        set(value) {
            val editor = editor
            editor.putString(RANDVAL, value)
            editor.commit()
        }

    var email : String?
        get() = pref.getString(EMAIL, "")
        set(value) {
            val editor = editor
            editor.putString(EMAIL, value)
            editor.commit()
        }

    var projPrice : String?
        get() = pref.getString(PROJECTPRICE, "")
        set(value) {
            val editor = editor
            editor.putString(PROJECTPRICE, value)
            editor.commit()
        }

    //HI-SCORES
    var highscore1 : String?
        get() = pref.getString(HIGH1, "0")
        set(value) {
            val editor = editor
            editor.putString(HIGH1, value)
            editor.commit()
        }

    var highscore2 : String?
        get() = pref.getString(HIGH2, "0")
        set(value) {
            val editor = editor
            editor.putString(HIGH2, value)
            editor.commit()
        }

    var highscore3 : String?
        get() = pref.getString(HIGH3, "0")
        set(value) {
            val editor = editor
            editor.putString(HIGH3, value)
            editor.commit()
        }

    var highscore4 : String?
        get() = pref.getString(HIGH4, "0")
        set(value) {
            val editor = editor
            editor.putString(HIGH4, value)
            editor.commit()
        }

    var highscore5 : String?
        get() = pref.getString(HIGH5, "0")
        set(value) {
            val editor = editor
            editor.putString(HIGH5, value)
            editor.commit()
        }

    //First Time
    var first1 : Int?
        get() = pref.getInt(FIRST1, 0)
        set(value) {
            val editor = editor
            editor.putInt(FIRST1, value!!)
            editor.commit()
        }


    var first2 : Int?
        get() = pref.getInt(FIRST2, 0)
        set(value) {
            val editor = editor
            editor.putInt(FIRST2, value!!)
            editor.commit()
        }

    var first3 : Int?
        get() = pref.getInt(FIRST3, 0)
        set(value) {
            val editor = editor
            editor.putInt(FIRST3, value!!)
            editor.commit()
        }

    var first4 : Int?
        get() = pref.getInt(FIRST4, 0)
        set(value) {
            val editor = editor
            editor.putInt(FIRST4, value!!)
            editor.commit()
        }

    var first5 : Int?
        get() = pref.getInt(FIRST5, 0)
        set(value) {
            val editor = editor
            editor.putInt(FIRST5, value!!)
            editor.commit()
        }

    var first6 : Int?
        get() = pref.getInt(FIRST6, 0)
        set(value) {
            val editor = editor
            editor.putInt(FIRST6, value!!)
            editor.commit()
        }

    var currPage : String?
        get() = pref.getString(CURRENTPAGE, "")
        set(value) {
            val editor = editor
            editor.putString(CURRENTPAGE, value)
            editor.commit()
        }

    var selectedToken : String?
        get() = pref.getString(SELECTEDTOKEN, "")
        set(value) {
            val editor = editor
            editor.putString(SELECTEDTOKEN, value)
            editor.commit()
        }

    var isComplete : Int
        get() = pref.getInt(iscompleted, 0)
        set(value) {
            val editor = editor
            editor.putInt(iscompleted, value)
            editor.commit()
        }

    //My Location
    fun getMyLocation(): String? {
        return pref.getString(PREF_MYLOC, "")
    }

    fun setMyLocation(myLocation: String?) {
        val editor = editor
        editor.putString(PREF_MYLOC, myLocation)
        editor.commit()
    }



    fun getDeviceName(): String? {
        val manufacturer = Build.MANUFACTURER
        val model = Build.MODEL
        if (model.startsWith(manufacturer)) {
            return capitalize(model)
        }
        return if (manufacturer.equals("HTC", ignoreCase = true)) {
            // make sure "HTC" is fully capitalized.
            "HTC $model"
        } else capitalize(manufacturer) + " " + model
    }

    private fun capitalize(str: String): String {
        if (TextUtils.isEmpty(str)) {
            return str
        }
        val arr = str.toCharArray()
        var capitalizeNext = true
        var phrase = ""
        for (c in arr) {
            if (capitalizeNext && Character.isLetter(c)) {
                phrase += Character.toUpperCase(c)
                capitalizeNext = false
                continue
            } else if (Character.isWhitespace(c)) {
                capitalizeNext = true
            }
            phrase += c
        }
        return phrase
    }

    companion object {
        private const val PREF_NAME = "saved_preference"
        private const val EMAIL = "user email"
        private const val LOCKCODE = "Lock code"
        private const val LOCKSTATUS = "lock status"
        private const val USERID = "User Login"
        private const val PLATFORM = "Platform Type"
        private const val USER_TYPE = "User type"
        private const val FEATURE = "app feature"
        private const val TEMP_1 = "temporary variable 1"
        private const val ROLE = "Role"
        private const val USERNAME = "username"
        private const val PASSWORD = "password"
        private const val FNAME = "first name"
        private const val TOKENID = "Push token id"
        private const val ADMINID = "Admin Push token id"
        private const val SERVICE = "Service name"
        private const val PERMISSIONS = "my access"
        private const val KEEPLOGGEDIN = "keep me logged in"
        private const val FEATUREPIC1 = "feature pic 1"
        private const val FEATUREPIC2 = "feature pic 2"
        private const val FEATUREPIC3 = "feature pic 3"
        private const val FEATUREPIC4 = "feature pic 4"
        private const val FEATUREPIC5 = "feature pic 5"
        private const val SEARCHVAL = "Search value"
        private const val FRAGVAL = "Frag value"
        private const val MUSICIANID = "Musician id"
        private const val GENRE = "Musician Genre"
        private const val PICPATH = "pic path"
        private const val FRAGVALPREV = "Previous frag file"
        private const val LISTCAT = "list of categories"
        private const val SELECTCAT = "selected category"
        private const val PROJECT = "entered project"
        private const val PROJECTTYPE = "entered project type"
        private const val SELECTEDUSERID = "selected user id"
        private const val MYCURRENCY = "user's currency"
        private const val PROJECTPRICE = "Select project price"
        private const val CURRENTPAGE = "current page in app"
        private const val SELECTEDTOKEN = "selected user tokem"
        private const val PREF_MYLOC = "locationsz"
        private const val iscompleted = "is completed test"
        private const val answerTitle = "answers title"
        private const val RANDVAL = "random value"
        private const val HIGH1 = "HIGH SCORE 1"
        private const val HIGH2 = "HIGH SCORE 2"
        private const val HIGH3 = "HIGH SCORE 3"
        private const val HIGH4 = "HIGH SCORE 4"
        private const val HIGH5 = "High Score 5"
        private const val FIRST1 = "First Time 1"
        private const val FIRST2 = "First Time 2"
        private const val FIRST3 = "First Time 3"
        private const val FIRST4 = "First Time 4"
        private const val FIRST5 = "First Time 5"
        private const val FIRST6 = "First Time 6"







    }

    init {
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }
}