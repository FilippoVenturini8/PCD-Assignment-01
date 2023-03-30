package utils;

import java.util.Optional;

public interface SynchronizedQueue <T>{
    void add(T elem);

    T blockingRemove() throws InterruptedException;

    Optional<T> remove();

    boolean isEmpty();

    int size();
}
