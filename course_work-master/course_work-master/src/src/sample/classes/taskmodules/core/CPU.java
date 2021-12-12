package src.sample.classes.taskmodules.core;

import src.sample.classes.taskmodules.processing.Process;
import src.sample.classes.taskmodules.processing.Scheduler;
import src.sample.classes.utilmodules.ITimerListener;

public class CPU implements ITimerListener
{
    Core[] cores;

    public CPU(final int number)
    {
        this.cores = new Core[number];

        for(int i = 0; i<number; i++)
            cores[i]=new Core();
    }
    public Core[] getCores(){return cores;}

    public void setCoreJob(int coreNumb, Process process)
    {
        if(process!=null) {
            cores[coreNumb].currentProcess = process;
            cores[coreNumb].isFree = false;
        }
    }

public int getFreeCore()
{
    for (int i =0; i<cores.length; i++)
        if(cores[i].isFree)
            return i;
    return -1;
}

    @Override
    public void tickEvent() {
        for(Core core : cores)
        {
            if(!core.isFree) {
                Process tempProcess = core.getCurrentProcess();

                tempProcess.setBurstTime(tempProcess.getBurstTime() + 1);
                if (tempProcess.getBurstTime() == tempProcess.getTickWorks()) {
                    Scheduler.PDone(core.currentProcess);
                    core.currentProcess = null;
                    core.isFree = true;
                }
            }
        }
    }
}
