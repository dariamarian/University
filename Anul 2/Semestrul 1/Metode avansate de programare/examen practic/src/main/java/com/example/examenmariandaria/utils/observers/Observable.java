package com.example.examenmariandaria.utils.observers;
import com.example.examenmariandaria.utils.events.Event;

public interface Observable<E extends Event>{
    void addObserver(Observer<E> e);

    void removeObserver(Observer<E> e);

    void notifyObservers(E t);
}
