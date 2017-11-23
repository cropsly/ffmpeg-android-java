package com.github.hiteshsondhi88.sampleffmpeg.screens.home;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.github.hiteshsondhi88.libffmpeg.ExecuteBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.FFmpeg;
import com.github.hiteshsondhi88.libffmpeg.LoadBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegCommandAlreadyRunningException;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegNotSupportedException;
import com.github.hiteshsondhi88.sampleffmpeg.R;
import com.github.hiteshsondhi88.sampleffmpeg.core.BaseActivity;
import com.github.hiteshsondhi88.sampleffmpeg.core.BaseApplication;

public class HomeActivity extends BaseActivity {

    private static final String TAG = HomeActivity.class.getSimpleName();

    @Inject
    FFmpeg ffmpeg;

    @BindView(R.id.command)
    EditText commandEditText;

    @BindView(R.id.command_output)
    LinearLayout outputLayout;

    @BindView(R.id.run_command)
    Button runButton;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        BaseApplication.getInstance().getApplicationComponent().inject(this);

        setToolbar(getResources().getString(R.string.app_name), false);

        loadFFMpegBinary();
        initUI();
    }

    private void initUI() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(null);
    }

    private void loadFFMpegBinary() {
        try {
            ffmpeg.loadBinary(new LoadBinaryResponseHandler() {
                @Override
                public void onFailure() {
                    showUnsupportedExceptionDialog();
                }
            });
        } catch (FFmpegNotSupportedException e) {
            showUnsupportedExceptionDialog();
        }
    }

    private void execFFmpegBinary(final String[] command) {
        try {
            ffmpeg.execute(command, new ExecuteBinaryResponseHandler() {
                @Override
                public void onFailure(String s) {
                    addTextViewToLayout("FAILED with output : " + s);
                }

                @Override
                public void onSuccess(String s) {
                    addTextViewToLayout("SUCCESS with output : " + s);
                }

                @Override
                public void onProgress(String s) {
                    Log.d(TAG, "Started command : ffmpeg " + command);
                    addTextViewToLayout("progress : " + s);
                    progressDialog.setMessage("Processing\n" + s);
                }

                @Override
                public void onStart() {
                    outputLayout.removeAllViews();

                    Log.d(TAG, "Started command : ffmpeg " + command);
                    progressDialog.setMessage("Processing...");
                    progressDialog.show();
                }

                @Override
                public void onFinish() {
                    Log.d(TAG, "Finished command : ffmpeg " + command);
                    progressDialog.dismiss();
                }
            });
        } catch (FFmpegCommandAlreadyRunningException e) {
            // do nothing for now
        }
    }

    private void addTextViewToLayout(String text) {
        TextView textView = new TextView(HomeActivity.this);
        textView.setText(text);
        outputLayout.addView(textView);
    }

    private void showUnsupportedExceptionDialog() {
        new AlertDialog.Builder(HomeActivity.this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(getString(R.string.device_not_supported))
                .setMessage(getString(R.string.device_not_supported_message))
                .setCancelable(false)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        HomeActivity.this.finish();
                    }
                })
                .create()
                .show();

    }

    @OnClick(R.id.run_command)
    void runCommand() {
        String cmd = commandEditText.getText().toString();
        String[] command = cmd.split(" ");
        if (command.length != 0) {
            execFFmpegBinary(command);
        } else {
            Toast.makeText(HomeActivity.this, getString(R.string.empty_command_toast), Toast.LENGTH_LONG).show();
        }
    }
}
