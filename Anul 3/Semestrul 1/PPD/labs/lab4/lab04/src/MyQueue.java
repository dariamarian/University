import java.util.LinkedList;
import java.util.Queue;

public class MyQueue {
    private final Queue<Concurent> queue = new LinkedList<>();
    private volatile int finishedReaders;

    public synchronized void put(Concurent concurent) {
        queue.add(concurent);
        notify();
    }

    public synchronized Concurent take() throws InterruptedException {
        if (queue.isEmpty() && finishedReaders < Constants.READERS) {
            wait();
        }
        return queue.isEmpty() ? null : queue.remove();
    }

    public synchronized void increment() {
        finishedReaders++;
        if (finishedReaders == Constants.READERS) {
            notifyAll();
        }
    }
}