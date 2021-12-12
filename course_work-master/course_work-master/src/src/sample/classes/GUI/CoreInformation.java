package src.sample.classes.GUI;

import src.sample.classes.taskmodules.processing.Process;
import src.sample.classes.taskmodules.processing.State;

public class CoreInformation {
    private String name;
    private int id;
    private State state;
    private int timeIn;
    private int burstTime;

    public CoreInformation(String core, Process process) {
        this.name = core;

        if(process!=null) {
            this.id = process.getId();
            this.state = process.getState();
            this.timeIn = process.getTimeIn();
            this.burstTime = process.getBurstTime();
            this.timeIn = process.getTimeIn();
        }
    }
    public String getName() {
        return name;
    }
    public int getId() {
        return id;
    }
    public State getState() {
        return state;
    }
    public int getTimeIn() {
        return timeIn;
    }
    public int getBurstTime() {
        return burstTime;
    }
}
