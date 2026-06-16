package dev.risas.dencore.utilities.thread;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ThreadFactory;

public class NameThreadFactory implements ThreadFactory {

    private final String name;

    public NameThreadFactory(String name) {
        this.name = name;
    }

    @Override
    public Thread newThread(@NotNull Runnable runnable) {
        return new Thread(runnable, name);
    }
}