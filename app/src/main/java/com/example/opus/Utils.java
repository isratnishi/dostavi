package com.example.opus;

import android.app.Activity;
import android.view.inputmethod.InputMethodManager;

import java.util.Calendar;

public class Utils {

    public static String getFormattedDate(Calendar calendar) {
        String tempMonth = "";
        String tempDay = "";
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        if (day < 10)
            tempDay = "0" + day;
        else
            tempDay += day;
        month = month + 1; //since month is 0 based but in server it is 1 based
        if (month < 10)
            tempMonth += "0" + month;
        else
            tempMonth += month;

        return year + tempMonth + tempDay;
    }

   /* public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }*/
}
