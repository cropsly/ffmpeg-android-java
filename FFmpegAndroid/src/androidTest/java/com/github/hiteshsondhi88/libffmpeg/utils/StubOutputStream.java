package com.github.hiteshsondhi88.libffmpeg.utils;

import java.io.IOException;
import java.io.OutputStream;

public class StubOutputStream extends OutputStream {

    private boolean closed = false;

    @Override
    public void write(int oneByte) throws IOException {
        // Do nothing for now, just need to test if output stream is closed
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
