package com.scottkrulcik.agnostic;

import dagger.Module;
import dagger.Provides;
import dagger.producers.Production;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Module
public final class ProductionExecutorModule {
    private static final ExecutorService exec = Executors.newCachedThreadPool();

    @Provides
    @Production
    static Executor provideExecutor() {
        return exec;
    }
}