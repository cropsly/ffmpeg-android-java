package com.github.hiteshsondhi88.sampleffmpeg;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Util {

    private static final String TAG = Util.class.getSimpleName();
    private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;
    private static final int EOF = -1;

    static boolean copyBinaryFromAssetsToData(Context context, String fileNameFromAssets, String outputFileName) {

        // create files directory under /data/data/package name
        File filesDirectory = context.getFilesDir();

        InputStream is;
        try {
            is = context.getAssets().open(fileNameFromAssets);
            // copy ffmpeg file from assets to files dir
            final FileOutputStream os = new FileOutputStream(new File(filesDirectory, outputFileName));
            byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];

            int n;
            while(EOF != (n = is.read(buffer))) {
                os.write(buffer, 0, n);
            }

            Util.close(os);
            Util.close(is);

            return true;
        } catch (IOException e) {
            Log.e(TAG, "issue in coping binary from assets to data. ", e);
        }
        return false;
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

}
