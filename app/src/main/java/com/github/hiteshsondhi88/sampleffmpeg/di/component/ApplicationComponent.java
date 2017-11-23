package com.github.hiteshsondhi88.sampleffmpeg.di.component;

/**
 * Created by navjotsinghbedi on 3/28/16.
 */


import com.github.hiteshsondhi88.sampleffmpeg.di.module.DaggerDependencyModule;
import com.github.hiteshsondhi88.sampleffmpeg.screens.home.HomeActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {DaggerDependencyModule.class})
public interface ApplicationComponent {

    void inject(HomeActivity homeActivity);
}
