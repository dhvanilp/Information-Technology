package com.example.dhp.codefundo;

import android.util.Log;

/**
 * Created by bordia98 on 21/3/18.
 */

public class ReturnColumnName {
    String ans = "";
    String date;

    ReturnColumnName(String date) {
        this.date = date;
    }

    public String returncolumn() {

        String monthname = date.substring(5, 7);
        Log.v("date", date.substring(5, 7));
        if (monthname.equals("01")) {
            ans += "jan";
        } else if (monthname.equals("02")) {
            ans += "feb";
        } else if (monthname.equals("03")) {
            ans += "mar";
        } else if (monthname.equals("04")) {
            ans += "apr";
        } else if (monthname.equals("05")) {
            ans += "may";
        } else if (monthname.equals("06")) {
            ans += "jun";
        } else if (monthname.equals("07")) {
            ans += "jul";
        } else if (monthname.equals("08")) {
            ans += "aug";
        } else if (monthname.equals("09")) {
            ans += "sep";
        } else if (monthname.equals("010")) {
            ans += "oct";
        } else if (monthname.equals("02")) {
            ans += "nov";
        } else {
            ans += "dec";
        }

        ans += date.substring(8, 10);
        ans += date.substring(0, 4);
        Log.v("date changed is ", ans);
        return ans;

    }
}
