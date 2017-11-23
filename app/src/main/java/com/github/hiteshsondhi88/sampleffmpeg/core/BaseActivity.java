package com.github.hiteshsondhi88.sampleffmpeg.core;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.github.hiteshsondhi88.sampleffmpeg.R;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by sd2_rails on 4/18/17.
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Nullable
    @BindView(R.id.toolbar)
    public Toolbar toolbar;

    public void setToolbar(String title) {
        setToolbar(title, true);
    }

    public void setToolbar(String title, boolean showBackButton) {
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(showBackButton);
                getSupportActionBar().setDisplayShowHomeEnabled(showBackButton);
                getSupportActionBar().setTitle(title);
            }
        }
    }

    public void setToolbarText(String title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    public Context getContext() {
        return this;
    }
}
