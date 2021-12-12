package src.sample.classes.GUI;

import src.sample.classes.taskmodules.processing.Scheduler;

public class ThreadStart implements Runnable {
    public static Scheduler sc;

    @Override
    public void run() {
        sc = new Scheduler();
        sc.Start();
    }
}
