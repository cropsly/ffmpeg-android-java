package com.github.hiteshsondhi88.libffmpeg.utils;

public abstract class StubProcess extends Process {

    private boolean isDestroyed = false;

    @Override
    public void destroy() {
        isDestroyed = true;
    }

    public boolean isDestroyed() {
        return isDestroyed;
    }
}
