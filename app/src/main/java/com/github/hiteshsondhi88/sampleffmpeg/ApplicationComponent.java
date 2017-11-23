package com.github.hiteshsondhi88.sampleffmpeg;

/**
 * Created by navjotsinghbedi on 3/28/16.
 */


import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {DaggerDependencyModule.class})
public interface ApplicationComponent {

    void inject(Home home);
}
