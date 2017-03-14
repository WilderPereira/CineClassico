package com.studiotrek.cineclassico.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.facebook.CallbackManager;

/**
 * Created by Admin on 04/03/2017.
 */

public class Util {

    public static String usernameFromEmail(String email) {
        if (email.contains("@")) {
            return email.split("@")[0];
        } else {
            return email;
        }
    }

    public static void navigate(FragmentTransaction fragmentTransaction, int container, Fragment fragment) {
        fragmentTransaction.replace(container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
    }

    public static boolean isOnline(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            boolean isAvailable = false;

            if (networkInfo != null && networkInfo.isConnected()) {
                isAvailable = true;
            }

            return isAvailable;
        } catch (Exception e) {
            return false;
        }
    }

}
