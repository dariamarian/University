package com.example.examenmariandaria.utils.observers;

import com.example.examenmariandaria.utils.events.Event;

public interface Observer<E extends Event> {
    void update(E e);
}
