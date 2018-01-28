package com.kxy.general.common.datamining.fitting.sin;

import com.kxy.general.common.datamining.data.KeyValue;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

import java.lang.reflect.Executable;
import java.util.ArrayList;
import java.util.List;

/**
 * SinFitTraining tester.
 * Created by kongxiangyun on 1/28/18.
 */
public class SinFitTrainingTest {
    private FSin f1 = new FSin(1.0, 1.0, 0.0, 0.0);
    private FSin f2 = new FSin(5.6, 3.2, 0.4, 2.1);
    private FSin f3 = new FSin(1.2, 1.0, 0.0, 0.0);
    private FSin f4 = new FSin(1.0, 2.1, 0.0, 0.0);
    private FSin f5 = new FSin(1.0, 1.0, 3.2, 0.0);
    private FSin f6 = new FSin(1.0, 1.0, 0.0, 4.3);

    @Test
    public void testTrain() throws Exception {
        List<FSin> ff;
        SinFit tested;
        List<KeyValue> data;

        ff = new ArrayList<>();
        ff.add(f1);
        tested = new SinFit(ff);
        data = new ArrayList<>();
        for (long i = 0; i < 100; ++i) {
            double key = i / 3.0;
            double value = tested.apply(key);
            data.add(new KeyValue(key, value));
        }
        System.out.println("test case 1:");
        train(data, tested);

        ff = new ArrayList<>();
        ff.add(f2);
        tested = new SinFit(ff);
        data = new ArrayList<>();
        for (long i = 0; i < 100; ++i) {
            double key = i / 3.0;
            double value = tested.apply(key);
            data.add(new KeyValue(key, value));
        }
        System.out.println("test case 2:");
        train(data, tested);

        ff = new ArrayList<>();
        ff.add(f3);
        ff.add(f4);
        ff.add(f5);
        ff.add(f6);
        tested = new SinFit(ff);
        data = new ArrayList<>();
        for (long i = 0; i < 100; ++i) {
            double key = i / 3.0;
            double value = tested.apply(key);
            data.add(new KeyValue(key, value));
        }
        System.out.println("test case 3:");
        train(data, tested);
    }

    private void train(List<KeyValue> data, SinFit tested) throws
            Exception {
        SinFitTraining training = new SinFitTraining();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                training.train(data, 10000);
            }
        });
        thread.start();

        while (thread.isAlive()) {
            Thread.sleep(1000L);
        }

        SinFit trained = new SinFit(training.getFunction());

        for (FSin f : tested.getFunction()) {
            System.out.println("" + f.getA() + "sin(" + f.getB() + "x + " +
                                       f.getC() + ") + " + f.getD());
        }
        System.out.println("--------------");
        for (FSin f : trained.getFunction()) {
            System.out.println("" + f.getA() + "sin(" + f.getB() + "x + " +
                                       f.getC() + ") + " + f.getD());
        }
        for (long i = 0; i < 100; ++i) {
            double key = i / 3.0;
            double value1 = tested.apply(key);
            double value2 = trained.apply(key);
            System.out.println("" + value1 + " " + value2 + " " +
                                       (value2 - value1));
        }
    }
}