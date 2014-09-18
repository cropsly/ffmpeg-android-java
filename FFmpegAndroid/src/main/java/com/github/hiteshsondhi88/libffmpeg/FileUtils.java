package com.github.hiteshsondhi88.libffmpeg;

import android.content.Context;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.Map;

class FileUtils {

    static final String ffmpegFileName = "ffmpeg";
    private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;
    private static final int EOF = -1;

    static boolean copyBinaryFromAssetsToData(Context context, String fileNameFromAssets, String outputFileName) {
		
		// create files directory under /data/data/package name
		File filesDirectory = getFilesDirectory(context);
		
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
			Log.e("issue in coping binary from assets to data. ", e);
		}
        return false;
	}

	static File getFilesDirectory(Context context) {
		// creates files directory under data/data/package name
        return context.getFilesDir();
	}

    static String getFFmpeg(Context context) {
        return getFilesDirectory(context).getAbsolutePath() + File.separator + FileUtils.ffmpegFileName;
    }

    static String getFFmpeg(Context context, Map<String,String> environmentVars) {
        String ffmpegCommand = "";
        if (environmentVars != null) {
            for (Map.Entry<String, String> var : environmentVars.entrySet()) {
                ffmpegCommand += var.getKey()+"="+var.getValue()+" ";
            }
        }
        ffmpegCommand += getFFmpeg(context);
        return ffmpegCommand;
    }

    static String SHA1(String file) {
        InputStream is = null;
        try {
            is = new BufferedInputStream(new FileInputStream(file));
            return SHA1(is);
        } catch (IOException e) {
            Log.e(e);
        } finally {
            Util.close(is);
        }
        return null;
    }

    static String SHA1(InputStream is) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
            final byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
            for (int read; (read = is.read(buffer)) != -1; ) {
                messageDigest.update(buffer, 0, read);
            }

            Formatter formatter = new Formatter();
            // Convert the byte to hex format
            for (final byte b : messageDigest.digest()) {
                formatter.format("%02x", b);
            }
            return formatter.toString();
        } catch (NoSuchAlgorithmException e) {
            Log.e(e);
        } catch (IOException e) {
            Log.e(e);
        } finally {
            Util.close(is);
        }
        return null;
    }
}