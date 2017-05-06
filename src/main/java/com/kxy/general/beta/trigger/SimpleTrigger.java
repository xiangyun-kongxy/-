package com.kxy.general.beta.trigger;

import lombok.Getter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by xiangyunkong on 21/04/2017.
 */
public class SimpleTrigger implements Trigger {
    /**
     * what to do when the trigger happen.
     */
    @Getter
    private Runnable runnable;

    /**
     * the threads to execute the Runnable.
     */
    private ExecutorService executorService;

    /**
     * create trigger with a task. the thread will be created for the task.
     * @param runnable task to do when the trigger happen
     */
    public SimpleTrigger(Runnable runnable) {
        this.runnable = runnable;
        this.executorService = Executors.newFixedThreadPool(1);
    }

    /**
     * create trigger with a task and a thread pool. the thread pool will be
     * used to run the task when the trigger happens
     * @param runnable task to do when the trigger happens
     * @param executorService thread pool to run task
     */
    public SimpleTrigger(Runnable runnable, ExecutorService executorService) {
        this.runnable = runnable;
        this.executorService = executorService;
    }

    /**
     * just put the task into the thread pool.
     */
    @Override
    public void happen() {
        this.executorService.submit(runnable);
    }
}
