package com.studiotrek.cineclassico.view.activity;

import android.content.Intent;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.studiotrek.cineclassico.R;

/**
 * Created by Admin on 04/03/2017.
 */

public class BaseActivity extends AppCompatActivity {

    public void SnackbarOffline(View view) {
        Snackbar snackbar = Snackbar
                .make(view, getResources().getString(R.string.offline), Snackbar.LENGTH_INDEFINITE)
                .setAction(getResources().getString(R.string.ativar_wifi), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                });

        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        TextView textView = (TextView) snackBarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(getResources().getColor(R.color.colorAccent));

        snackbar.show();
    }

}
