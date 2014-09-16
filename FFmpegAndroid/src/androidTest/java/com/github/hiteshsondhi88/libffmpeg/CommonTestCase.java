package com.github.hiteshsondhi88.libffmpeg;

import junit.framework.TestCase;

public abstract class CommonTestCase extends TestCase {

    public void setUp() throws Exception {
        super.setUp();
        Log.setDEBUG(true);
    }

}
