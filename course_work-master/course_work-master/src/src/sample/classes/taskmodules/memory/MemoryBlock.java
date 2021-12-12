package src.sample.classes.taskmodules.memory;

import src.sample.classes.taskmodules.processing.Process;

import java.util.Comparator;

public class MemoryBlock {
    int start;
    int end;
    Process process;

    public static Comparator<MemoryBlock> byEnd = Comparator.comparingInt(o -> o.end);

    public MemoryBlock(int start, int end, Process process) {
        this.start = start;
        this.end = end;
        this.process = process;
    }
}
