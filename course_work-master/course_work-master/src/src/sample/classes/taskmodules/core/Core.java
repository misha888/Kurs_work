package src.sample.classes.taskmodules.core;

import src.sample.classes.taskmodules.processing.Process;

public class Core {
   public boolean isFree = true;
   public Process currentProcess = null;

   public String getState() {
      String temp = " ";
      if(this.isFree){
         temp += "is free";
      }
      else{
         temp += "Working on Process: " + currentProcess;
      }
      return temp ;
   }
   public Process getCurrentProcess(){
      return currentProcess;
   }
}
