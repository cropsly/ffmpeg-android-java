package com.github.hiteshsondhi88.libffmpeg;

import android.os.AsyncTask;

import com.github.hiteshsondhi88.libffmpeg.utils.AssertionHelper;
import com.github.hiteshsondhi88.libffmpeg.utils.StubInputStream;
import com.github.hiteshsondhi88.libffmpeg.utils.StubOutputStream;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class UtilTest extends CommonTestCase {

    public void testIsDebug() throws Exception {
        // do nothing for now
    }

    public void testCloseInputStream() throws Exception {
        StubInputStream stubInputStream = new StubInputStream();
        // initially input stream shouldn't be closed
        assertEquals(false, stubInputStream.isClosed());

        // closing input stream
        Util.close(stubInputStream);

        // Input stream should be closed now
        assertEquals(true, stubInputStream.isClosed());
    }

    public void testCloseOutputStream() throws Exception {
        StubOutputStream stubOutputStream = new StubOutputStream();
        // initially output stream shouldn't be closed
        assertEquals(false, stubOutputStream.isClosed());

        // closing output stream
        Util.close(stubOutputStream);

        // Output stream should be closed now
        assertEquals(true, stubOutputStream.isClosed());
    }

    public void testConvertInputStreamToString() throws Exception {
        String exampleString1 = "Example to provide source to InputStream";
        String exampleString2 = "";
        String exampleString3 = 1+"";
        InputStream stream1 = new ByteArrayInputStream(exampleString1.getBytes());
        InputStream stream2 = new ByteArrayInputStream(exampleString2.getBytes());
        InputStream stream3 = new ByteArrayInputStream(exampleString3.getBytes());
        String output1 = Util.convertInputStreamToString(stream1);
        String output2 = Util.convertInputStreamToString(stream2);
        String output3 = Util.convertInputStreamToString(stream3);

        assertEquals(output1, exampleString1);
        assertEquals(output2, exampleString2);
        assertEquals(output3, exampleString3);
    }

    public void testDestroyProcessAndIsProcessCompleted() throws Exception {
        final Process process = Runtime.getRuntime().exec("logcat");
        assertEquals(false, Util.isProcessCompleted(process));

        Util.destroyProcess(process);

        ExecutorService executor = Executors.newSingleThreadExecutor();

        Future<?> future = executor.submit(new Runnable() {
            @Override
            public void run() {
                String errorStream = Util.convertInputStreamToString(process.getErrorStream());
                String inputStream = Util.convertInputStreamToString(process.getInputStream());

                assertEquals(null, errorStream);
                assertEquals(null, inputStream);
            }
        });

        try {
            future.get(1, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            AssertionHelper.assertError("could not destroy process");
        }

        executor.shutdownNow();
    }

    public void testKillAsync() throws Exception {
        AsyncTask<Void, Void, Void> asyncTask = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    Log.d("AsyncTask started and putting to sleep");
                    Thread.sleep(5000);
                    Log.d("could not kill AsyncTask");
                    AssertionHelper.assertError("could not kill AsyncTask");
                } catch (InterruptedException e) {
                    Log.d("AsyncTask has been terminated");
                    AssertionHelper.assertError("AsyncTask has been terminated");
                }
                return null;
            }
        };
        asyncTask.execute();
        assertEquals(true, Util.killAsync(asyncTask));
    }
}