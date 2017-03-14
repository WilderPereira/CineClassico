package com.studiotrek.cineclassico.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Admin on 04/03/2017.
 */

public class CinePreference {
    public static void setLogin(Context context) {
        SharedPreferences login = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = login.edit();
        editor.putInt(Constantes.LOGIN, Constantes.LOGADO);
        editor.apply();
    }

    public static void setLogout(Context context) {
        SharedPreferences login = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = login.edit();
        editor.putInt(Constantes.LOGIN, Constantes.DESLOGADO);
        editor.apply();
    }

    public static Integer getStatusLogin(Context context) {
        SharedPreferences login = PreferenceManager.getDefaultSharedPreferences(context);
        return login.getInt(Constantes.LOGIN, Constantes.DESLOGADO);
    }
}
