package utils;

public interface SynchronizedQueue <T>{
    void add(T elem);

    T remove() throws InterruptedException;

    boolean isEmpty();
}
