package com.opus_bd.dostana;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.util.Calendar;

import static android.content.Context.INPUT_METHOD_SERVICE;

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

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

   /* public void hideSoftKeyboard(Activity activity) {

        View view = activity.getCurrentFocus();
        if(view!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }*/
}
