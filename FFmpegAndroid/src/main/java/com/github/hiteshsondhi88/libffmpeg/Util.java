package com.github.hiteshsondhi88.libffmpeg;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

class Util {

    static boolean isDebug(Context context) {
        return (0 != (context.getApplicationContext().getApplicationInfo().flags &= ApplicationInfo.FLAG_DEBUGGABLE));
    }

    static void close(InputStream inputStream) {
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e) {
                // Do nothing
            }
        }
    }

    static void close(OutputStream outputStream) {
        if (outputStream != null) {
            try {
                outputStream.flush();
                outputStream.close();
            } catch (IOException e) {
                // Do nothing
            }
        }
    }

    static String convertInputStreamToString(InputStream inputStream) {
        try {
            BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
            String str;
            StringBuilder sb = new StringBuilder();
            while ((str = r.readLine()) != null) {
                sb.append(str);
            }
            return sb.toString();
        } catch (IOException e) {
            Log.e("error converting input stream to string", e);
        }
        return null;
    }

    static void destroyProcess(Process process) {
        if (process != null) {
            try {
                process.destroy();
            } catch (Exception e) {
                Log.e("process destroy error", e);
            }
        }
    }

    static boolean killAsync(AsyncTask asyncTask) {
        return asyncTask != null && !asyncTask.isCancelled() && asyncTask.cancel(true);
    }

    static boolean isProcessCompleted(Process process) {
        try {
            if (process == null) return true;
            process.exitValue();
            return true;
        } catch (IllegalThreadStateException e) {
            // do nothing
        }
        return false;
    }

    public interface ObservePredicate {
        Boolean isReadyToProceed();
    }

    public static FFmpegObserver observeOnce(final ObservePredicate predicate, final Runnable run, final int timeout) {
        final android.os.Handler observer = new android.os.Handler();

        // Enable this to detect neverending observers
//        final Exception e = new RuntimeException("WTF");

        final FFmpegObserver observeAction = new FFmpegObserver() {
            private boolean canceled = false;
            private int timeElapsed = 0;

            @Override
            public void run() {
                if (timeElapsed + 40 > timeout) cancel();
                timeElapsed += 40;

                if (canceled) return;

                Boolean readyToProceed = null;
                try {
                    readyToProceed = predicate.isReadyToProceed();
                } catch (Exception e) {
//                    Log.v("Observing " + e.getMessage());
                    observer.postDelayed(this, 40);
                    return;
                }

                if (readyToProceed != null && readyToProceed) {
//                    Log.v("Observed");
                    run.run();
                } else {
                    // Enable this to detect neverending observers
//                    Log.v("Util", "Observing", e);
//                    Log.v("Observing");
                    observer.postDelayed(this, 40);
                }
            }

            @Override
            public void cancel() {
                canceled = true;
            }
        };

        observer.post(observeAction);

        return observeAction;
    }
}
