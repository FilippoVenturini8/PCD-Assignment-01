package utils;

public class Flag {

    private boolean flag = false;

    public synchronized boolean get(){
        return this.flag;
    }

    public synchronized void set(boolean value){
        this.flag = value;
    }
}
