package org.example;

import org.example.domain.Concurent;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MyQueue {
    private final int MAX = 100;
    private final Lock lock = new ReentrantLock();
    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();
    private final AtomicInteger readersLeft;

    public MyQueue(AtomicInteger readersLeft) {
        this.readersLeft = readersLeft;
    }

    public int first = 0;
    public int last = 0;
    public int count = 0;
    private final Concurent[] queue = new Concurent[101];

    public void finish() {
        lock.lock();
        notEmpty.signal();
        notFull.signal();
        lock.unlock();
    }

    public void push(Concurent node) throws InterruptedException {
        lock.lock();
        try {
            // Wait until the queue is not full
            while (count == MAX) {
                notFull.await();
            }

            // Add the concurent to the queue
            queue[last++] = node;

            // Wrap around if the end of the array is reached
            if (last == MAX) {
                last = 0;
            }

            // Increment the count of elements in the queue
            ++count;

            // Signal that the queue is not empty
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    public Concurent pop() throws InterruptedException {
        Concurent result;
        lock.lock();
        try {
            // Wait until the queue is not empty
            while (count == 0) {
                // Check if there are no more readers
                if (readersLeft.get() == 0) {
                    // Return null if no more readers
                    return null;
                }

                // Wait for the queue to become not empty
                notEmpty.await();
            }

            // Retrieve the concurent from the queue
            result = queue[first++];

            // Wrap around if the end of the array is reached
            if (first == MAX) {
                first = 0;
            }

            // Decrement the count of elements in the queue
            --count;

            // Signal that the queue is not full
            notFull.signal();

            return result;
        } finally {
            lock.unlock();
        }
    }


    public boolean isEmpty() {
        return count == 0;
    }
}
