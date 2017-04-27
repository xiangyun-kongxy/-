package com.kxy.general.beta.trigger.impl;

import com.kxy.general.beta.trigger.SimpleTrigger;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;

/**
 * Created by xiangyunkong on 21/04/2017.
 */
public class TimerTrigger extends SimpleTrigger {
    /**
     * the timer to drive the trigger.
     */
    private Timer timer = new Timer(true);

    /**
     * @see SimpleTrigger#SimpleTrigger(Runnable)
     * @param runnable task to do when happening
     */
    public TimerTrigger(Runnable runnable) {
        super(runnable);

        this.configTimer();
    }

    /**
     * @see SimpleTrigger#SimpleTrigger(Runnable, ExecutorService)
     * @param runnable task to do when happening
     * @param executorService thread pool used to do task
     */
    public TimerTrigger(Runnable runnable, ExecutorService executorService) {
        super(runnable, executorService);

        this.configTimer();
    }

    /**
     * nothing to do for TimeTrigger (with this open API, it will happen
     * internal).
     */
    @Override
    public void happen() {
    }

    /**
     * init the timer.
     */
    private void configTimer() {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                TimerTrigger.super.happen();
            }
        };
        this.timer.schedule(task, 0L);
    }
}
