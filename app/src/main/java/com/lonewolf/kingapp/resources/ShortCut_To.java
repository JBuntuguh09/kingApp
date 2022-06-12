package com.lonewolf.kingapp.resources;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class ShortCut_To {

    public static final String DATEWITHTIME = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    public static final String DATEFORMATDDMMYYYY = "dd/MM/yyyy";
    public static final String DATEFORMATDDMMYYYY2 = "dd-MM-yyyy";
    public static final String DATEFORMATYYYYMMDD = "yyyy-MM-dd";
    public static final String TIME = "hh:mm a";
    public static final String DATEWITHTIMEDDMMYYY = "dd-MM-yyyy'T'HH:mm:ss.SSS'Z'";



    public static String getCurrentDatewithTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATEWITHTIMEDDMMYYY, Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static void hideKeyboard(Activity activity) {
        try {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            //Find the currently focused view, so we can grab the correct window token from it.
            View view = activity.getCurrentFocus();
            //If no view currently has focus, create a new one, just so we can grab a window token from it
            if (view == null) {
                view = new View(activity);
            }
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }catch (Exception e){
            Log.d("No keyboard", "No keyboard to drop");
        }
    }

    public static String getCurrentDates() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATEFORMATYYYYMMDD, Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static String getCurrentDateFormat2() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATEFORMATDDMMYYYY, Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }



    public static String getTimeFromDate(String str){
        if(str != null && !str.equalsIgnoreCase("null") && str.trim().length()!=0){
            SimpleDateFormat sdf1 = new SimpleDateFormat(DATEWITHTIME, Locale.US);
            SimpleDateFormat sdf2 = new SimpleDateFormat(TIME, Locale.US);

            try {
                Date date = sdf1.parse(str);
                return sdf2.format(date);
            }catch (Exception e){
                e.printStackTrace();
                return "";
            }
        }else {
            return " ";
        }
    }

    public static String getCurrDateRaw(){
        Calendar calendar = Calendar.getInstance();
        return String.valueOf(calendar.getTimeInMillis());
    }

    public static String getCurrentDay(){

        String daysArray[] = {"sun","mon","tue", "wed","thu","fri", "sat"};

        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        int mt = calendar.get(Calendar.MONTH);
        int yr = calendar.get(Calendar.YEAR);


        return yr+daysArray[day-1]+mt;

    }

    public static String getCurrentMonthYear() {

        Calendar c = Calendar.getInstance();
        int currMonth = c.get(Calendar.MONTH)+1;
        int currYear = c.get(Calendar.YEAR);
        int curDay = c.get(Calendar.DAY_OF_MONTH);

        return currMonth+"/"+currYear;
    }

        public static String getCurrentDayMonthYear() {

        Calendar c = Calendar.getInstance();
        int currMonth = c.get(Calendar.MONTH);
        int currYear = c.get(Calendar.YEAR);
        int curDay = c.get(Calendar.DAY_OF_MONTH);


        if(currMonth==0){

            return "January "+ curDay +", "+ currYear;
        }else if(currMonth==1){
            return "February "+ curDay +", "+ currYear;
        }else if(currMonth==2){
            return "March "+ curDay +", "+ currYear;
        }else if(currMonth==3){
            return "April "+ curDay +", "+ currYear;
        }else if(currMonth==4){
            return "May "+ curDay +", "+ currYear;
        }else if(currMonth==5){
            return "June "+ curDay +", "+ currYear;
        }else if(currMonth==6){
            return "July "+ curDay +", "+ currYear;
        }else if(currMonth==7){
            return "August "+ curDay +", "+ currYear;
        }else if(currMonth==8){
            return "September "+ curDay +", "+ currYear;
        }else if(currMonth==9){
            return "October "+ curDay +", "+ currYear;
        }else if(currMonth==10){
            return "November "+ curDay +", "+ currYear;
        }else if(currMonth==11){
            return "December "+ curDay +", "+ currYear;
        }
        else {
            return "";
        }

    }

    public static String getDateTimeForAPI(String dateFormatted) {
        Calendar apiDate = Calendar.getInstance();
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(DATEFORMATDDMMYYYY);
            apiDate.setTime(dateFormat.parse(dateFormatted));
            Calendar corrTime = Calendar.getInstance();
            apiDate.set(Calendar.HOUR_OF_DAY, corrTime.get(Calendar.HOUR_OF_DAY));
            apiDate.set(Calendar.MINUTE, corrTime.get(Calendar.MINUTE));
            apiDate.set(Calendar.SECOND, corrTime.get(Calendar.SECOND));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //2014-03-15T21:04:43.162Z
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATEWITHTIME);
        return dateFormat.format(apiDate.getTime());
    }

    public static String getDateForAPP(String strDate) {
        if (strDate != null && !strDate.equalsIgnoreCase("null") && strDate.trim().length() != 0) {

            SimpleDateFormat sdf1 = new SimpleDateFormat(DATEWITHTIME);
            SimpleDateFormat sdf2 = new SimpleDateFormat(DATEFORMATDDMMYYYY2);

            try {

                Date date = sdf1.parse(strDate);
                return sdf2.format(date);

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }


        } else {
            return "";
        }
    }

    public static String getFormatDateAPI(String str) {
        if (str != null && !str.equalsIgnoreCase("null") && str.trim().length() != 0) {

            SimpleDateFormat sdf1 = new SimpleDateFormat(DATEFORMATDDMMYYYY);
            SimpleDateFormat sdf2 = new SimpleDateFormat(DATEFORMATYYYYMMDD);

            try {

                Date date = sdf1.parse(str);
                return sdf2.format(date);

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }


        } else {
            return "";
        }
    }

    public static void sortData(ArrayList<HashMap<String, String>> list, final String field){

            Collections.sort(list, (lhs, rhs) -> lhs.get(field).compareTo(rhs.get(field)));





    }

    public static void sortDataInvert(ArrayList<HashMap<String, String>> list, final String field){

        Collections.sort(list, new Comparator<HashMap<String, String>>() {
            @Override
            public int compare(HashMap<String, String> lhs, HashMap<String, String> rhs) {

                return rhs.get(field).compareTo(lhs.get(field));
            }
        });

    }

    public static String convertDate(String date){
        String[] nDate = date.split("T");
        String mDate = nDate[0];
        String[] oDate = mDate.split("-");
        String fDate = oDate[2]+"/"+oDate[1]+"/"+oDate[0];
        return fDate;
    }

    public static String addDaysToDate(String date, int numDays){

        String[] arrDate = date.split("/");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(arrDate[0]));
        cal.set(Calendar.MONTH, Integer.parseInt(arrDate[1])-1);
        cal.set(Calendar.YEAR, Integer.parseInt(arrDate[2]));
        cal.add(Calendar.DAY_OF_MONTH, numDays);

        return sdf.format(cal.getTime());
    }

    public static String getMonthYear(String date) {

        Calendar c = Calendar.getInstance();
        int currMonth = Integer.parseInt(date.split("/")[1])-1;
        int currYear = Integer.parseInt(date.split("/")[2]);
        int curDay = Integer.parseInt(date.split("/")[0]);


        if(currMonth==0){

            return "January, "+currYear;
        }else if(currMonth==1){
            return "February, "+currYear;
        }else if(currMonth==2){
            return "March, "+currYear;
        }else if(currMonth==3){
            return "April, "+currYear;
        }else if(currMonth==4){
            return "May, "+currYear;
        }else if(currMonth==5){
            return "June, "+currYear;
        }else if(currMonth==6){
            return "July, "+currYear;
        }else if(currMonth==7){
            return "August, "+currYear;
        }else if(currMonth==8){
            return "September, "+currYear;
        }else if(currMonth==9){
            return "October, "+currYear;
        }else if(currMonth==10){
            return "November, "+currYear;
        }else if(currMonth==11){
            return "December, "+currYear;
        }
        else {
            return "";
        }

    }

    public static String getMonthYearShort(String date) {

        Calendar c = Calendar.getInstance();
        int currMonth = Integer.parseInt(date.split("/")[1])-1;
        int currYear = Integer.parseInt(date.split("/")[2]);
        int curDay = Integer.parseInt(date.split("/")[0]);


        if(currMonth==0){

            return "Jan. "+currYear;
        }else if(currMonth==1){
            return "February, "+currYear;
        }else if(currMonth==2){
            return "Mar. "+currYear;
        }else if(currMonth==3){
            return "Apr. "+currYear;
        }else if(currMonth==4){
            return "May. "+currYear;
        }else if(currMonth==5){
            return "Jun. "+currYear;
        }else if(currMonth==6){
            return "Jul. "+currYear;
        }else if(currMonth==7){
            return "Aug. "+currYear;
        }else if(currMonth==8){
            return "Sep. "+currYear;
        }else if(currMonth==9){
            return "Oct. "+currYear;
        }else if(currMonth==10){
            return "Nov. "+currYear;
        }else if(currMonth==11){
            return "Dec. "+currYear;
        }
        else {
            return "";
        }

    }

    public static String getDayOFWeek(String dDate){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dDate.split("/")[0]));
        calendar.set(Calendar.MONTH, (Integer.parseInt(dDate.split("/")[1])-1));
        calendar.set(Calendar.YEAR, Integer.parseInt(dDate.split("/")[2]));

        return calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.US).toUpperCase();
    }

    public static String getCompany(String company, String limit){
        String newCompany = "Alpha";
        if(limit.equals("Charlie")){
            switch (company) {
                case "None":
                    newCompany = "Alpha";
                    break;
                case "Alpha":
                    newCompany = "Bravo";
                    break;
                case "Bravo":
                    newCompany = "Charlie";
                    break;
                case "Charlie":
                    newCompany = "Alpha";
                    break;
            }
        }else if(limit.equals("Delta")){
            switch (company) {
                case "None":
                    newCompany = "Alpha";
                    break;
                case "Alpha":
                    newCompany = "Bravo";
                    break;
                case "Bravo":
                    newCompany = "Charlie";
                    break;
                case "Charlie":
                    newCompany = "Delta";
                    break;
                case "Delta":
                    newCompany = "Alpha";
                    break;

            }
        }else if(limit.equals("Echo")){
            switch (company) {
                case "None":
                    newCompany = "Alpha";
                    break;
                case "Alpha":
                    newCompany = "Bravo";
                    break;
                case "Bravo":
                    newCompany = "Charlie";
                    break;
                case "Charlie":
                    newCompany = "Alpha";
                    break;
                case "Delta":
                    newCompany = "Echo";
                    break;
                case "Echo":
                    newCompany = "Alpha";
                    break;
            }
        }else if(limit.equals("Foxtrot")){
            switch (company) {
                case "None":
                    newCompany = "Alpha";
                    break;
                case "Alpha":
                    newCompany = "Bravo";
                    break;
                case "Bravo":
                    newCompany = "Charlie";
                    break;
                case "Charlie":
                    newCompany = "Delta";
                    break;
                case "Delta":
                    newCompany = "Echo";
                    break;
                case "Echo":
                    newCompany = "Foxtrot";
                    break;

                case "Foxtrot":
                    newCompany = "Alpha";
                    break;
            }
        }else if(limit.equals("Gulf")){
            switch (company) {
                case "None":
                    newCompany = "Alpha";
                    break;
                case "Alpha":
                    newCompany = "Bravo";
                    break;
                case "Bravo":
                    newCompany = "Charlie";
                    break;
                case "Charlie":
                    newCompany = "Delta";
                    break;
                case "Delta":
                    newCompany = "Echo";
                    break;
                case "Echo":
                    newCompany = "Foxtrot";
                    break;

                case "Foxtrot":
                    newCompany = "Gulf";
                    break;
                case "Gulf":
                    newCompany = "Alpha";
                    break;

            }
        }



        return newCompany;

    }

    public static int getAgeAlt(String dob){

        String[] arrYear = dob.split("/");
        String[] currYear = getCurrentDateFormat2().split("/");

        int newYear = Integer.parseInt(currYear[2]) - Integer.parseInt(arrYear[2]);

        if(Integer.parseInt(arrYear[1])>Integer.parseInt(currYear[1])){
            newYear=newYear-1;
        }else{
            if(Integer.parseInt(arrYear[1]) ==Integer.parseInt(currYear[1])){
                if(Integer.parseInt(arrYear[0])>Integer.parseInt(currYear[0])){
                    newYear = newYear - 1;
                }
            }
        }

        return newYear;
    }

    public static int getAge(String dobString){

        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            date = sdf.parse(dobString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(date == null) return 0;

        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.setTime(date);

        int year = dob.get(Calendar.YEAR);
        int month = dob.get(Calendar.MONTH);
        int day = dob.get(Calendar.DAY_OF_MONTH);

        dob.set(year, month+1, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
            age--;
        }



        return age+1;
    }

    public static String getDateAfterNumberOfDays(String strDate, int num){
        if (strDate == null || strDate.equalsIgnoreCase("null") || strDate.trim().length() == 0) {
            return "";
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(DATEFORMATYYYYMMDD);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(sdf.parse(strDate));

            calendar.add(Calendar.DATE, -num);

            Date resultdate = new Date(calendar.getTimeInMillis());
            return sdf.format(resultdate);

        }catch (Exception e){
            e.printStackTrace();
        }


        return "";

    }

    public static Date addMonth(Date date, int i) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, i);
        return cal.getTime();
    }

    public static int getCountOfDays(String createdDateString, String expireDateString) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");

        Date createdConvertedDate = null;
        Date expireCovertedDate = null;
        try {
            createdConvertedDate = dateFormat.parse(createdDateString);
            expireCovertedDate = dateFormat.parse(expireDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        Calendar start = new GregorianCalendar();
        start.setTime(createdConvertedDate);

        Calendar end = new GregorianCalendar();
        end.setTime(expireCovertedDate);

        long diff = end.getTimeInMillis() - start.getTimeInMillis();

        float dayCount = (float) diff / (24 * 60 * 60 * 1000);


        return (int) (dayCount);
    }

    public static String getCountOfDay(String createdDateString, String expireDateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        Date createdConvertedDate = null, expireCovertedDate = null, todayWithZeroTime = null;
        try {
            createdConvertedDate = dateFormat.parse(createdDateString);
            expireCovertedDate = dateFormat.parse(expireDateString);

            Date today = new Date();

            todayWithZeroTime = dateFormat.parse(dateFormat.format(today));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int cYear = 0, cMonth = 0, cDay = 0;

        if (createdConvertedDate.after(todayWithZeroTime)) {
            Calendar cCal = Calendar.getInstance();
            cCal.setTime(createdConvertedDate);
            cYear = cCal.get(Calendar.YEAR);
            cMonth = cCal.get(Calendar.MONTH);
            cDay = cCal.get(Calendar.DAY_OF_MONTH);

        } else {
            Calendar cCal = Calendar.getInstance();
            cCal.setTime(todayWithZeroTime);
            cYear = cCal.get(Calendar.YEAR);
            cMonth = cCal.get(Calendar.MONTH);
            cDay = cCal.get(Calendar.DAY_OF_MONTH);
        }


    /*Calendar todayCal = Calendar.getInstance();
    int todayYear = todayCal.get(Calendar.YEAR);
    int today = todayCal.get(Calendar.MONTH);
    int todayDay = todayCal.get(Calendar.DAY_OF_MONTH);
    */

        Calendar eCal = Calendar.getInstance();
        eCal.setTime(expireCovertedDate);

        int eYear = eCal.get(Calendar.YEAR);
        int eMonth = eCal.get(Calendar.MONTH);
        int eDay = eCal.get(Calendar.DAY_OF_MONTH);

        Calendar date1 = Calendar.getInstance();
        Calendar date2 = Calendar.getInstance();

        date1.clear();
        date1.set(cYear, cMonth, cDay);
        date2.clear();
        date2.set(eYear, eMonth, eDay);

        long diff = date2.getTimeInMillis() - date1.getTimeInMillis();

        float dayCount = (float) diff / (24 * 60 * 60 * 1000);

        return ("" + (int) dayCount );
    }




    public static void showAlert(Activity activity, String title, String message){
        AlertDialog.Builder alert = new AlertDialog.Builder(activity);
        alert.setTitle(title);
        alert.setMessage(message);
        alert.setCancelable(false);
        alert.show();
    }



    public static List<String> getRegions(){
        List<String> listRegions = new ArrayList<>();
        listRegions.add("Select your Current region");
        listRegions.add("Ahafo");
        listRegions.add("Ashanti");
        listRegions.add("Bono");
        listRegions.add("Bono East");
        listRegions.add("Central");
        listRegions.add("Eastern");
        listRegions.add("Greater Accra");
        listRegions.add("North East");
        listRegions.add("Northern");
        listRegions.add("Oti");
        listRegions.add("Savannah");
        listRegions.add("Upper East");
        listRegions.add("Upper West");
        listRegions.add("Volta");
        listRegions.add("Western");
        listRegions.add("Western North");

        return  listRegions;
    }

    public static List<String> getRegionsHome(){
        List<String> listRegions = new ArrayList<>();
        listRegions.add("Select your Home region");
        listRegions.add("Ahafo");
        listRegions.add("Ashanti");
        listRegions.add("Bono");
        listRegions.add("Bono East");
        listRegions.add("Central");
        listRegions.add("Eastern");
        listRegions.add("Greater Accra");
        listRegions.add("North East");
        listRegions.add("Northern");
        listRegions.add("Oti");
        listRegions.add("Savannah");
        listRegions.add("Upper East");
        listRegions.add("Upper West");
        listRegions.add("Volta");
        listRegions.add("Western");
        listRegions.add("Western North");

        return  listRegions;
    }


    public static List<String> getCategoryName(){
        List<String> list = new ArrayList<>();
        list.add("Harmonies");
        list.add("Beats");
        list.add("Instrumentals");
        list.add("Vocals");
        list.add("Educational");
        list.add("New Release");


        return list;
    }


    public static String[] getCountryies(){
        return new String[]{"Select Country", "Ghana", "Afghanistan", "Albania", "Algeria", "Andorra", "Angola", "Antarctica", "Antigua and Barbuda", "Argentina", "Armenia", "Australia", "Austria", "Azerbaijan", "Bahamas",
                "Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium", "Belize", "Benin", "Bermuda", "Bhutan", "Bolivia", "Bosnia and Herzegovina", "Botswana", "Brazil", "Brunei", "Bulgaria",
                "Burkina Faso", "Burma", "Burundi", "Cambodia", "Cameroon", "Canada", "Cape Verde", "Central African Republic", "Chad", "Chile", "China", "Colombia", "Comoros", "Congo, Democratic Republic",
                "Congo, Republic of the", "Costa Rica", "Cote d'Ivoire", "Croatia", "Cuba", "Cyprus", "Czech Republic", "Denmark", "Djibouti", "Dominica", "Dominican Republic", "East Timor",
                "Ecuador", "Egypt", "El Salvador", "Equatorial Guinea", "Eritrea", "Estonia", "Ethiopia", "Fiji", "Finland", "France", "Gabon", "Gambia", "Georgia", "Germany",  "Greece",
                "Greenland", "Grenada", "Guatemala", "Guinea", "Guinea-Bissau", "Guyana", "Haiti", "Honduras", "Hong Kong", "Hungary", "Iceland", "India", "Indonesia", "Iran", "Iraq", "Ireland",
                "Israel", "Italy", "Jamaica", "Japan", "Jordan", "Kazakhstan", "Kenya", "Kiribati", "Korea, North", "Korea, South", "Kuwait", "Kyrgyzstan", "Laos", "Latvia", "Lebanon", "Lesotho",
                "Liberia", "Libya", "Liechtenstein", "Lithuania", "Luxembourg", "Macedonia", "Madagascar", "Malawi", "Malaysia", "Maldives", "Mali", "Malta", "Marshall Islands", "Mauritania", "Mauritius",
                "Mexico", "Micronesia", "Moldova", "Mongolia", "Morocco", "Monaco", "Mozambique", "Namibia", "Nauru", "Nepal", "Netherlands", "New Zealand",
                "Nicaragua", "Niger", "Nigeria", "Norway", "Oman", "Pakistan", "Panama", "Papua New Guinea", "Paraguay", "Peru", "Philippines", "Poland", "Portugal", "Qatar", "Romania", "Russia",
                "Rwanda", "Samoa", "San Marino", " Sao Tome", "Saudi Arabia", "Senegal", "Serbia and Montenegro", "Seychelles", "Sierra Leone", "Singapore", "Slovakia", "Slovenia", "Solomon Islands",
                "Somalia", "South Africa", "Spain", "Sri Lanka", "Sudan", "Suriname", "Swaziland", "Sweden", "Switzerland", "Syria", "Taiwan", "Tajikistan", "Tanzania", "Thailand", "Togo", "Tonga",
                "Trinidad and Tobago", "Tunisia", "Turkey", "Turkmenistan", "Uganda", "Ukraine", "United Arab Emirates", "United Kingdom", "United States", "Uruguay", "Uzbekistan", "Vanuatu", "Venezuela",
                "Vietnam", "Yemen", "Zambia", "Zimbabwe"};
    }

    public static String[] getNetWorks(){
        return new String[]{"Select Network", "MTN", "Vodafone", "AirtelTigo"};
    }
    public static String[] getNetWorksVal(){
        return new String[]{"", "mtn", "vod", "tgo"};
    }

//    public static List<String> getMonthYear(){
//        Calendar c = Calendar.getInstance();
//        int currMonth = c.get(Calendar.MONTH);
//        int currYear = c.get(Calendar.YEAR);
//        int curDay = c.get(Calendar.DAY_OF_MONTH);
//
//        int past = currYear - 30;
//        int future = currYear + 10;
//
//        for(int a = past; a <future; a++){
//            for(int b=currMonth; b<currMonth)
//        }
//    }



}
