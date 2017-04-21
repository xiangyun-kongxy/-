package com.kxy.general.ß.trigger;

import lombok.Getter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by xiangyunkong on 21/04/2017.
 */
public class SimpleTrigger implements Trigger {
    @Getter
    private Runnable runnable;
    private ExecutorService executorService;

    public SimpleTrigger(Runnable runnable) {
        this.runnable = runnable;
        this.executorService = Executors.newFixedThreadPool(1);
    }

    public SimpleTrigger(Runnable runnable, ExecutorService executorService) {
        this.runnable = runnable;
        this.executorService = executorService;
    }

    @Override
    public void happen() {
        this.executorService.submit(runnable);
    }
}
