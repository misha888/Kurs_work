package src.sample.classes.taskmodules.processing;

import src.sample.classes.taskmodules.memory.MemoryScheduler;
import src.sample.classes.Configuration;
import src.sample.classes.utilmodules.Timer;

import java.util.ArrayList;

public class Queue {
    private final ArrayList<Process> ReadyQueue;
    private final ArrayList<Process> RejectedQueue;
    private final ArrayList<Process> DoneProcesses;
    private int LastID;

    public Queue() {
        this.ReadyQueue = new ArrayList<>();
        this.RejectedQueue = new ArrayList<>();
        this.DoneProcesses = new ArrayList<>();
        this.LastID =1;
    }

    public void Add(final int PCount) {
        for (int i = 0; i < PCount; i++) {
            Process p = new Process(this.LastID);
            this.LastID++;
            addProcess(p);
        }
    }

    public void addProcess(Process p) {
        if (p.getState() == State.Waiting)
            p.setBurstTime(p.getBurstTime()+(int)(p.getBurstTime()/ Configuration.old_new_process_time));

        if(MemoryScheduler.fillMemoryBlock(p)) {
            this.ReadyQueue.add(p);
        }
        else {
            if (p.getState() == State.Waiting)
                p.setState(State.Canceled);
            else
                p.setState(State.Rejected);

            RejectedQueue.add(p);
        }
    }

    public void Remove(Process process) {
        ReadyQueue.remove(process);
        MemoryScheduler.releaseMemoryBlock(process);
    }

    public void cancelOutdated() {
            for (int i = ReadyQueue.size()-1; i>=0; i--)
                if (Timer.getTick() >= ReadyQueue.get(i).getTimeIn() * Configuration.remove_process_multiplier)
                    cancelProcess(ReadyQueue.get(i));
    }

    private void cancelProcess(Process process) {
        Remove(process);
        process.setState(State.Canceled);
        RejectedQueue.add(process);
    }
    public Process getNextProcess() {
        ReadyQueue.sort(Process.byTime);
        if(ReadyQueue.size()!=0) {
            Process temp = ReadyQueue.get(0);
            this.Remove(temp);
            temp.setState(State.Running);
            return temp;
        }
        return null;
    }

    public void finishProcess(Process process)
    {
        DoneProcesses.add(process);
    }

    public ArrayList<Process> getDoneProcesses() {
        return DoneProcesses;
    }

    public ArrayList<Process> getReadyQueue() {
        return ReadyQueue;
    }

    public ArrayList<Process> getRejectedQueue() {
        return RejectedQueue;
    }
}
