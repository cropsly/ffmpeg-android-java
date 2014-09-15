package com.github.hiteshsondhi88.libffmpeg;

@SuppressWarnings("unused")
class Log {

    private static String TAG = FFmpeg.class.getSimpleName();
    private static boolean DEBUG = false;

    static void setDEBUG(boolean DEBUG) {
        Log.DEBUG = DEBUG;
    }

    static void setTAG(String tag) {
        Log.TAG = tag;
    }

    static void d(Object obj) {
        if (DEBUG) {
            android.util.Log.d(TAG, obj != null ? obj.toString() : null+"");
        }
    }

    static void e(Object obj) {
        if (DEBUG) {
            android.util.Log.e(TAG, obj != null ? obj.toString() : null+"");
        }
    }

    static void w(Object obj) {
        if (DEBUG) {
            android.util.Log.w(TAG, obj != null ? obj.toString() : null+"");
        }
    }

    static void i(Object obj) {
        if (DEBUG) {
            android.util.Log.i(TAG, obj != null ? obj.toString() : null+"");
        }
    }

    static void v(Object obj) {
        if (DEBUG) {
            android.util.Log.v(TAG, obj != null ? obj.toString() : null+"");
        }
    }

    static void e(Object obj, Throwable throwable) {
        if (DEBUG) {
            android.util.Log.e(TAG, obj != null ? obj.toString() : null+"", throwable);
        }
    }

    static void e(Throwable throwable) {
        if (DEBUG) {
            android.util.Log.e(TAG, "", throwable);
        }
    }

}
