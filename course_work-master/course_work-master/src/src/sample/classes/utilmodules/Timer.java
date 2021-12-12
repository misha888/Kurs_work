package src.sample.classes.utilmodules;

import src.sample.classes.Configuration;

import java.util.ArrayList;
import java.util.TimerTask;

public class Timer extends TimerTask {
    ArrayList<ITimerListener> listenersList = new ArrayList<>();

    private static int tick;
    public static int getTick() {
        return tick;
    }
    public static void TickUP(){
        tick++;
    }

    public static void clearTime(){tick =0;}

    @Override
    public void run() {
        while (true)
        {
            try {
                Thread.sleep(1000, Configuration.tick_per_second);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            TickUP();

            for(ITimerListener listener : listenersList)
                listener.tickEvent();
        }
    }
    public void addListener(ITimerListener listener)
    {
        listenersList.add(listener);
    }
}
