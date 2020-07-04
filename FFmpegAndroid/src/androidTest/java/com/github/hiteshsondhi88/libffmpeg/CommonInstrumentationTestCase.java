package com.github.hiteshsondhi88.libffmpeg;

import android.test.InstrumentationTestCase;

public class CommonInstrumentationTestCase extends InstrumentationTestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        Log.setDEBUG(true);
    }

}
