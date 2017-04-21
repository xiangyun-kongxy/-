package com.kxy.general.ß.trigger.impl;

import com.kxy.general.ß.trigger.SimpleTrigger;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;

/**
 * Created by xiangyunkong on 21/04/2017.
 */
public class TimerTrigger extends SimpleTrigger {
    private Timer timer = new Timer(true);

    public TimerTrigger(Runnable runnable) {
        super(runnable);

        this.configTimer();
    }

    public TimerTrigger(Runnable runnable, ExecutorService executorService) {
        super(runnable, executorService);

        this.configTimer();
    }

    @Override
    public void happen() {
    }

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
