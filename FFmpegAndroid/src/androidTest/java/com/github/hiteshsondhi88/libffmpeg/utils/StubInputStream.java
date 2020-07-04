package com.github.hiteshsondhi88.libffmpeg.utils;

import java.io.IOException;
import java.io.InputStream;

public class StubInputStream extends InputStream {

    private boolean closed = false;

    @Override
    public int read() throws IOException {
        // Do nothing for now, just need to test if input stream is closed
        return 0;
    }

    @Override
    public void close() throws IOException {
        super.close();
        closed = true;
    }

    public boolean isClosed() {
        return closed;
    }
}
