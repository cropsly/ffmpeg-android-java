package com.github.hiteshsondhi88.sampleffmpeg;

import android.app.Application;
import android.content.Context;

/**
 * Created by bedi on 01/03/17.
 */

public class BaseApplication extends Application {

    private static BaseApplication sInstance;

    private ApplicationComponent applicationComponent;

    public static BaseApplication getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    public ApplicationComponent getApplicationComponent() {
        if (applicationComponent == null) {
            applicationComponent = DaggerApplicationComponent.builder()
                    .daggerDependencyModule(new DaggerDependencyModule(this))
                    .build();
        }
        return applicationComponent;
    }
}
