package ppd.server.myserver.util;

import lombok.Getter;
import lombok.Setter;
import ppd.server.myserver.entity.Concurent;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Getter
@Setter
public class Node {
    public final Lock lock = new ReentrantLock();
    private Concurent data;
    public Node next;
    public Node previous;

    public void lock() {
        lock.lock();
    }

    public void unlock() {
        lock.unlock();
    }

    public Node(Concurent data, Node next, Node previous) {
        this.data = data;
        this.next = next;
        this.previous = previous;
    }
    public boolean isNotLastNode() {
        return data != null;
    }
}
