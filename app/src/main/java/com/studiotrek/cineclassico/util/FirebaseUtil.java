package com.studiotrek.cineclassico.util;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Admin on 04/03/2017.
 */

public class FirebaseUtil {

    //public static final String UID = FirebaseAuth.getInstance().getCurrentUser().getUid();

    private static FirebaseDatabase mDatabase;

    public static FirebaseDatabase getDatabase() {
        if (mDatabase == null) {
            mDatabase = FirebaseDatabase.getInstance();
            mDatabase.setPersistenceEnabled(true);
        }
        return mDatabase;
    }

}
