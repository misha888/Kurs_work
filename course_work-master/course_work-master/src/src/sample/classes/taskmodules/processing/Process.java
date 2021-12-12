package src.sample.classes.taskmodules.processing;

import src.sample.classes.Configuration;
import src.sample.classes.utilmodules.Timer;
import src.sample.classes.utilmodules.Utils;

import java.util.Comparator;

public class Process {
    private final int id;
    private final String Name;
    private State state;
    private  int TickWorks;
    private final int Memory;
    private final int TimeIn;
    private int BurstTime;

    public Process(int id) {
        this.id = id;
        this.Name = "Процесс №" + this.id + " " + Utils.getRandString(Utils.getRandomInteger(Configuration.min_process_name_l,Configuration.max_process_name_l));
        this.Memory = Utils.getRandomInteger(Configuration.min_process_memory_required, Configuration.max_process_memory_required);
        this.TickWorks = Utils.getRandomInteger(Configuration.min_process_time_required, Configuration.max_process_time_required);
        this.TimeIn = Timer.getTick();
        this.BurstTime =0;
        this.state = State.Ready;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void setBurstTime(int burstTime) {
        this.BurstTime = burstTime;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return Name;
    }

    public int getTickWorks() {
        return TickWorks;
    }

    public int getMemory() {
        return Memory;
    }

    public int getTimeIn() {
        return TimeIn;
    }

    public int getBurstTime() {
        return BurstTime;
    }

    public State getState(){return state;}

    public static Comparator<Process> byTime = Comparator.comparingInt(o -> o.TickWorks);
}
