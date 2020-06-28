package com.aminsujit.foodrecipes;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class AppExecutors {
    private static AppExecutors instance;
    public static AppExecutors getInstance() {
        if (instance == null) {
            instance = new AppExecutors();
        }
        return instance;
    }
    /*You can use java.util.Timer or ScheduledThreadPoolExecutor (preferred)
    to schedule an action to occur at regular intervals on a background thread.*/
    //corePoolSize the number of threads to keep in the pool,
    //even if they are idle
    /*
    * An ExecutorService that can schedule commands to run after a given delay, or to execute periodically.
     * */
    private final ScheduledExecutorService mNetworkIO = Executors.newScheduledThreadPool(3);

    public ScheduledExecutorService getmNetworkIO() {
        return mNetworkIO;
    }
}
