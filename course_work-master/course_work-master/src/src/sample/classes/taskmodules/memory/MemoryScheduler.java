package src.sample.classes.taskmodules.memory;

import src.sample.classes.taskmodules.processing.Process;
import src.sample.classes.Configuration;

import java.util.ArrayList;

public class MemoryScheduler {
private static ArrayList<MemoryBlock> memoryBlocks = new ArrayList<>();

public static int searchMemoryBlock(int size)
{
    memoryBlocks.sort(MemoryBlock.byEnd);

    for(int i=memoryBlocks.size()-1;i>0;i--) {
        if (memoryBlocks.get(i -1).start - memoryBlocks.get(i).end >= size+1)
            return memoryBlocks.get(i).end + 1;
    }
    if(Configuration.max_memory_size - memoryBlocks.get(memoryBlocks.size()-1).end>=size+1)
        return memoryBlocks.get(memoryBlocks.size()-1).end+1;
    else
        return Configuration.max_memory_size;
}

public static boolean fillMemoryBlock(Process process)
{
    int start = searchMemoryBlock(process.getMemory());

    if (start!= Configuration.max_memory_size)
    {
        memoryBlocks.add(new MemoryBlock(start, start+process.getMemory(),process));
        return true;
    }
    else
    return false;
}

public static void releaseMemoryBlock(Process process)
{
    memoryBlocks.removeIf(mb -> mb.process == process);
}

public static void clearMemory(){
memoryBlocks.clear();
}

public static void add(MemoryBlock memoryBlock) {
    memoryBlocks.add(memoryBlock);
}

    @Override
    public String toString() {
        return "MemScheduler{"+memoryBlocks+"}";
    }
}
