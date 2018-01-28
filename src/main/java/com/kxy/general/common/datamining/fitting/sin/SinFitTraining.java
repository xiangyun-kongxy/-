package com.kxy.general.common.datamining.fitting.sin;

import com.kxy.general.common.datamining.data.KeyValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * class for Training SinFit.
 * Created by kongxiangyun on 1/28/18.
 */
public class SinFitTraining {
    /**
     * the fitting SinFit.
     */
    private List<FSin> function;

    /**
     * the change parameters.
     */
    private double ca, cb, cc, cd;

    /**
     * how much to change.
     */
    private double ch;

    /**
     * weight of mean square.
     */
    static final double WEIGHT_DM = 4;
    /**
     * weight of diff of distance.
     */
    static final double WEIGHT_DT = 3;
    /**
     * weight of diff of diff.
     */
    static final double WEIGHT_DF = 2;

    /**
     * get current trained SinFit.
     * @return SinFit
     */
    public List<FSin> getFunction() {
        List<FSin> func = new ArrayList<>();
        for (FSin sin : function) {
            FSin s = new FSin(sin.getA(), sin.getB(), sin.getC(), sin.getD());
            func.add(s);
        }
        return func;
    }

    /**
     * training SinFit by a set of data.
     * @param data data for training
     * @param trainingTimes how many iterators will take at most
     */
    public void train(List<KeyValue> data, long trainingTimes) {
        function = new ArrayList<>();
        FSin sin = new FSin(1.0, 1.0, 0.0, 0.0);
        function.add(sin);
        initChangeParameter();

        double dm, df, dt;
        dm = meanSquare(data, function);
        df = diffSum(data, function);
        dt = distSum(data, function);

        long lastAccepted = trainingTimes;
        while (dm > 0.01) {
            if (--trainingTimes <= 0) {
                break;
            }

            function = changeFunction(data, dm, df, dt);

            dm = meanSquare(data, function);
            df = diffSum(data, function);
            dt = distSum(data, function);

            if (jump == 0.0) {
                lastAccepted = trainingTimes;

                System.out.print("" + trainingTimes + "    " + dm + "    ");
                SinFit fit = new SinFit(function);
                fit.print();
                System.out.println();
            } else if (lastAccepted - trainingTimes > ADD_FUNC_BOUNDARY) {
                sin = new FSin(0.0, 1.0, 0, 0);
                function.add(sin);
                initChangeParameter();
                lastAccepted = trainingTimes;
            }
        }
    }

    /**
     * accept if the difference is smaller.
     * @param dm diff of mean square
     * @param df diff of sum of diff
     * @param dt diff of sum of distance
     * @param dm2 new of dm
     * @param df2 new of df
     * @param dt2 new of dt
     * @return accept or not
     */
    private boolean accept(double dm, double df, double dt,
                   double dm2, double df2, double dt2) {
        return dm2 < dm && Math.abs(dm2 - dm) > 1.0e-3;
        /*
        double s1 = Math.pow(dm, WEIGHT_DM)
                            + Math.pow(dt, WEIGHT_DT)
                            + Math.pow(df, WEIGHT_DF);
        double s2 = Math.pow(dm2, WEIGHT_DM)
                            + Math.pow(dt2, WEIGHT_DT)
                            + Math.pow(df2, WEIGHT_DF);
        return s2 < s1;
        */
    }

    /**
     * max try change count.
     */
    static final Long MAX_TRY_CHANGE = 10000L;
    /**
     * when to add a new function.
     */
    static final Long ADD_FUNC_BOUNDARY = 1000L;

    /**
     * p 1.
     */
    static final double ADJUST_P1 = 1.0;
    /**
     * p 2.
     */
    static final double ADJUST_P2 = 4.0;
    /**
     * change parameters.
     */
    Double da = 0.0, db = 0.0, dc = 0.0, dd = 0.0;
    Double jump = 0.0;

    /**
     * change SinFit according to dm, df and dt to make the difference smaller.
     * @param data data for training
     * @param dm diff of mean square
     * @param df diff of sum of diff
     * @param dt diff of sum of distance
     * @return changed SinFit
     */
    private List<FSin> changeFunction(List<KeyValue> data,
                                      double dm, double df, double dt) {
        List<FSin> func = getFunction();
        FSin sin = func.get(func.size() - 1);

        double dm2 = 0.0, df2, dt2;

        long n = 0;
        boolean funcAdded = true;
        while (++n < MAX_TRY_CHANGE) {
            changeParameter(df, dt);
            sin.setA(sin.getA() + da);
            sin.setB(sin.getB() + db);
            sin.setC(sin.getC() + dc);
            sin.setD(sin.getD() + dd);

            dm2 = meanSquare(data, func);
            df2 = diffSum(data, func);
            dt2 = distSum(data, func);
            if (accept(dm, df, dt, dm2, df2, dt2)) {
                adjustChangeParameter(true, da, db, dc, dd);
                break;
            } else {
                sin.setA(sin.getA() - da);
                sin.setB(sin.getB() - db);
                sin.setC(sin.getC() - dc);
                sin.setD(sin.getD() - dd);

                adjustChangeParameter(false, da, db, dc, dd);
            }
        }
        if (n >= MAX_TRY_CHANGE) {
            jump = 10.0;
        } else {
            jump = 0.0;
        }

        return func;
    }

    /**
     * init parameters.
     */
    private void initChangeParameter() {
        ch = 1.0;
        ca = 1.0;
        cb = 0.0;
        cc = 0.0;
        cd = 0.0;
    }

    /**
     * adjust parameters.
     * @param accepted new condition is accepted or not
     * @param da accepted da
     * @param db accepted db
     * @param dc accepted dc
     * @param dd accepted dd
     */
    private void adjustChangeParameter(boolean accepted, double da, double db,
                                       double dc, double dd) {
        if (accepted) {
            double dh = da + db + dc + dd;
            ch = (ch + dh / 2.0);
            ca = (ca + da / dh) / 2.0;
            cb = (cb + db / dh) / 2.0;
            cc = (cc + dc / dh) / 2.0;
            cd = (cd + dd / dh) / 2.0;
        } else {
            ch *= ADJUST_P1;
            ca = (ca + ADJUST_P1) / ADJUST_P2;
            cb = (cb + ADJUST_P1) / ADJUST_P2;
            cc = (cc + ADJUST_P1) / ADJUST_P2;
            cd = (cd + ADJUST_P1) / ADJUST_P2;
        }
    }

    /**
     * random max.
     */
    static final Integer RAND_MAX = 100;
    /**
     * random half max.
     */
    static final Integer RAND_HALF = 50;

    /**
     * try to change function parameter.
     */
    private void changeParameter(double df, double dt) {
        Random random = new Random(System.nanoTime());
        double r;

        da = 0.0;
        db = 0.0;
        dc = 0.0;
        dd = 0.0;

        double ea = 0.0;
        double ed = 0.0;
        double eb = 0.0;
        double ec = 0.0;
        if (Math.abs(dt) > Math.abs(df) * 10) {
            ea = Math.abs(dt);
        } else if (Math.abs(Math.abs(dt) / Math.abs(df) - 1.0) < 1.0e-2) {
            ed = Math.abs(dt) / Math.abs(df);
        } else {
            eb = 10.0;
            ec = 10.0;
        }

        if (random.nextDouble() < ca + ea) {
            da = (ch + jump + ea) * random.nextDouble();
            if (random.nextInt(RAND_MAX) < RAND_HALF) {
                da *= -1;
            }
        }
        if (random.nextDouble() < cb) {
            db = (ch + jump + eb) * random.nextDouble();
            if (random.nextInt(RAND_MAX) < RAND_HALF) {
                db *= -1;
            }
        }
        if (random.nextDouble() < cc) {
            dc = (ch + jump + ec) * random.nextDouble();
            if (random.nextInt(RAND_MAX) < RAND_HALF) {
                dc *= -1;
            }
        }
        if (random.nextDouble() < cd + ed) {
            dd = (ch + jump + ed) * random.nextDouble();
            if (random.nextInt(RAND_MAX) < RAND_HALF) {
                dd *= -1;
            }
        }

        if (da == 0.0 && db == 0.0 && dc == 0.0 && dd == 0.0) {
            da = 1.0;
        }
    }

    /**
     * mean square between real value and the fitting value.
     * @param data the real values.
     * @param f the training SinFit
     * @return the mean square
     */
    private double meanSquare(List<KeyValue> data, List<FSin> f) {
        SinFit fit = new SinFit(f);
        double s = 0.0;
        for (KeyValue d : data) {
            double vr = (Double) d.getValue();
            double vf = fit.apply((Double) d.getKey());
            s += Math.pow(vr - vf, 2);
        }
        return Math.sqrt(s / data.size());
    }

    /**
     * diff of sum of diff between real value and the fitting value.
     * @param data the real values.
     * @param f the training SinFit
     * @return the mean square
     */
    private double diffSum(List<KeyValue> data, List<FSin> f) {
        SinFit fit = new SinFit(f);
        double s = 0.0;
        for (KeyValue d : data) {
            double vr = (Double) d.getValue();
            double vf = fit.apply((Double) d.getKey());
            s += vf - vr;
        }
        return s / data.size();
    }

    /**
     * diff of sum of distance between real value and the fitting value.
     * @param data the real values.
     * @param f the training SinFit
     * @return the mean square
     */
    private double distSum(List<KeyValue> data, List<FSin> f) {
        SinFit fit = new SinFit(f);
        double s = 0.0;
        for (KeyValue d : data) {
            double vr = (Double) d.getValue();
            double vf = fit.apply((Double) d.getKey());
            s += Math.abs(vf - vr);
        }
        return s / data.size();
    }
}
