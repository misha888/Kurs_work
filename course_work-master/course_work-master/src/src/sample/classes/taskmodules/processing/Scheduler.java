package src.sample.classes.taskmodules.processing;

import src.sample.Main;
import src.sample.classes.Configuration;
import src.sample.classes.utilmodules.Timer;
import src.sample.classes.utilmodules.ITimerListener;
import src.sample.classes.utilmodules.Utils;
import src.sample.classes.GUI.CoreInformation;
import src.sample.classes.taskmodules.core.CPU;
import src.sample.classes.taskmodules.memory.MemoryBlock;
import src.sample.classes.taskmodules.memory.MemoryScheduler;

import java.util.ArrayList;

public class Scheduler implements ITimerListener {
    static Queue queue;

    CPU cpu;
    MemoryScheduler memoryScheduler;
    Timer timer;

    public Scheduler() {
        queue = new Queue();

        this.cpu = new CPU(Configuration.coresQuantity);
        this.memoryScheduler = new MemoryScheduler();
        this.timer = new Timer();
        this.timer.addListener(cpu);
        this.timer.addListener(this);
    }

    public void Start() {
        preLaunchInit();
        this.timer.run();
    }

    private void preLaunchInit() {
        MemoryScheduler.add(new MemoryBlock(0,230,null));
        queue.Add(Configuration.processes_On_Start);
    }

    private void addJob() {
        if(Utils.getRandBool()) {
            queue.Add(Utils.getRandomInteger(12));
        }
        updateTable();
    }

    public static void PDone(Process process) {
        if(Utils.getRandBool()) {
            process.setState(State.Finished);
            queue.finishProcess(process);
        }
        else
        {
            process.setState(State.Waiting);
            queue.addProcess(process);
        }
    }

    private void clearOutdated() {
        if(Timer.getTick()% Configuration.remove_old_process_every_n_ticks ==0) {
            queue.cancelOutdated();
        }
    }

    private void setJobToCPU() {
        for (int i = 0; i< Configuration.coresQuantity; i++) {
            int tempInt = cpu.getFreeCore();
            if (tempInt >= 0) {
                cpu.setCoreJob(tempInt, queue.getNextProcess());
            }
        }
    }

    public void updateTable() {
        Main.controller.TableRowSetter(queue, generateCoreInfo());
    }

    private ArrayList generateCoreInfo() {
        ArrayList temp = new ArrayList<CoreInformation>();
        for (int i =0; i<cpu.getCores().length;i++ )
            temp.add(new CoreInformation(String.valueOf(i),cpu.getCores()[i].getCurrentProcess()));
        return temp;
    }

    @Override
    public void tickEvent() {
        clearOutdated();
        setJobToCPU();
        addJob();
    }
}
