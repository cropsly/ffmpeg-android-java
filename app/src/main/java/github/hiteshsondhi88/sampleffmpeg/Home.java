package github.hiteshsondhi88.sampleffmpeg;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import javax.inject.Inject;

import dagger.ObjectGraph;
import github.hiteshsondhi88.libffmpeg.FFmpeg;
import github.hiteshsondhi88.libffmpeg.FFmpegLoadBinaryResponseHandler;
import github.hiteshsondhi88.libffmpeg.exceptions.FFmpegNotSupportedException;

public class Home extends Activity {

    private static final String TAG = Home.class.getSimpleName();

    @Inject
    FFmpeg ffmpeg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ObjectGraph.create(new DaggerDependencyModule(this)).inject(this);

        try {
            ffmpeg.loadBinary(new FFmpegLoadBinaryResponseHandler() {

                @Override
                public void onStart() {
                    // Do Nothing
                }

                @Override
                public void onFailure() {
                    Log.e(TAG, "FFmpeg loading failed");
                }

                @Override
                public void onSuccess() {
                    Log.e(TAG, "FFmpeg loading success");
                }

                @Override
                public void onFinish() {
                    Log.e(TAG, "FFmpeg loading finished");
                }
            });
        } catch (FFmpegNotSupportedException e) {
            Log.e(TAG, "FFmpeg not supported", e);
        }
    }
}
