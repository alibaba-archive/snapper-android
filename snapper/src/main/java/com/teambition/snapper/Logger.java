package com.teambition.snapper;

import android.util.Log;

/**
 * Copyright © 2016 by Teambition.
 */

public class Logger {

    private static final String TAG = "Snapper";
    private static Logger logger;

    public static Logger getInstance() {
        if (logger == null) {
            logger = new Logger();
        }
        return logger;
    }

    public static void log(String msg) {
        Log.d(TAG, msg);
    }
}
